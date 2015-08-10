actions = require '../actions.coffee'
stores = require '../stores.coffee'
{NavigatableMixin} = require 'react-router-component'
merge = require 'merge'

module.exports = React.createClass
	displayName: __filename

	render: require './create-view.page.jade'

	mixins: [Reflux.ListenerMixin, Reflux.connect(stores.aggregate), NavigatableMixin, React.addons.LinkedStateMixin]
	
	getInitialState: ->
		this.listenTo stores.fields, (event)=>
			if event.fields?.data?.metrics
				return if event.fields.data.metrics[0].id is 0
				event.fields.data.metrics.unshift
					id: 0
					codeName: 0
					aliasName:'count(*)'
					fieldType:"Metric"
					dataClassName:"java.sql.BigDicmal"
			this.setState event

		this.listenTo stores.createdView, (res)=>
			if res.createdView.error
				return alert res.createdView.error

			this.navigate res.createdView.response.headers.location

		actions.fields @props

		fields: {}
		dropFields: []
		dropMetrics: []
		dropCategories: []
		dropFields_by_codeName:{}
		aggregate:{}
		viewType: 'BarChart'
		
	onDragStart:(field, operation)->
		(e)->
			e.dataTransfer.effectAllowed = 'move'
			
			if field.id is 0
				operation = 'count'

			if operation
				e.dataTransfer.setData 'operation',operation
				e.dataTransfer.setData 'text', "#{operation}(#{field.aliasName})"
			else
				e.dataTransfer.setData 'text', field.aliasName

			e.dataTransfer.setData 'field', JSON.stringify field

	enableDrop:(ev)->
		ev.stopPropagation()
		ev.preventDefault()

	onDrop: (ev)->
		ev.stopPropagation()
		ev.preventDefault()

		field = ev.dataTransfer.getData 'field'
		return unless field
		field = JSON.parse field
		field.operation = ev.dataTransfer.getData 'operation'
		
		# remove before push, so that no duplication
		#dropFields = this._remove field
		dropFields = this.state.dropFields
		dropFields.push field

		this._index_by_codeName dropFields
		
		this.aggregate()
	
	_remove: (field)->
		dropFields = this.state.dropFields	
		for dropField, i in dropFields when dropField.codeName is field.codeName and dropField.operation is field.operation
			dropFields.splice i, 1
			break
		dropFields

	_index_by_codeName: (dropFields)->
		dropFields_by_codeName = {}
		for dropField, i in dropFields
			 dropFields_by_codeName[dropField.codeName] ?= []
			 dropFields_by_codeName[dropField.codeName].push dropField.operation

		dropMetrics = dropFields.filter (field)-> field.fieldType is 'Metric'	 
		dropCategories = dropFields.filter (field)-> field.fieldType is 'Category'	 
		this.setState
			dropFields: dropFields
			dropMetrics: dropMetrics
			dropCategories: dropCategories
			dropFields_by_codeName: dropFields_by_codeName

	remove:(field)->
		(ev)=>
			ev.stopPropagation()
			dropFields = this._remove field
			this._index_by_codeName dropFields
			this.aggregate()

	_aggregate: ->
		dropFields = this.state.dropFields
		data =
			reportId: +this.props.id
			categories: for field in dropFields when 'Category' is field.fieldType
				fieldId: field.id
			metrics: for field in dropFields when 'Metric' is field.fieldType
				fieldId: field.id
				aliasName: field.aliasName
				operation: field.operation
		data.canAggregate = data.categories.length or data.metrics.length
		data

	aggregate: ->
		data = this._aggregate()
		actions.aggregate data

	submit: (data)->
		merge data, this._aggregate()
		return false unless data.canAggregate

		more = {}
		for metric in data.metrics
			refName = "chart#{metric.operation}#{metric.aliasName}"
			more[refName] = this.refs[refName].state.viewType
		data.description = JSON.stringify more
		data.reportId = +data.reportId
		true

	onResponse: actions.createdView