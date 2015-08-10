stores = require '../stores.coffee'
actions = require '../actions.coffee'
app_actions = require '../../app-actions.coffee'

sqlparser = require 'simple-sql-parser'

{NavigatableMixin} = require 'react-router-component'

module.exports = React.createClass
	displayName: __filename
	
	render: require './dataset-editor.jade'

	mixins: [Reflux.ListenerMixin, React.addons.LinkedStateMixin, NavigatableMixin]

	getInitialState: ->
		sql: 'select tab2.name as carName,tab1.start_date as startEnd,tab1.end_date as endDate,tab1.status as status from rental_record tab1 inner join car_brand tab2 where tab1.car_id = tab2.id'
		sql2ast:
			status: true

	sqlOnKeyUp: ->
		sql = this.state.sql.replace /;*$/g, ''
		ast = sqlparser.sql2ast sql
		
		this.setState
			sql2ast: ast

	onSubmit: (data)->
		data.dataSourceId = +data.dataSourceId
		fields = []
		for index, field of data.fields
			fields[index] = field
			field.length = +field.length
			field.scale = +field.scale

		data.fields = fields
		true

	onResponse: actions.created
