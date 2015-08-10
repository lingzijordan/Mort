viewTypes = require './viewTypes.json'

module.exports = React.createClass
	displayName: __filename

	render: require './index.jade'

	getInitialState: -> 
		viewType: this.props.viewType or viewTypes[0]

	onDragStart: (e)->
		e.dataTransfer.effectAllowed = 'move'

	stopDrag: (e)->
		e.stopPropagation()
		e.preventDefault()

	componentWillReceiveProps: updateState = (props=this.props)->
		{result} = props
		return unless result
		
		categories = result.fields.filter (field,i) ->
			field.index = i
			field.fieldType is 'Category'
		
		metrics = result.fields.filter (field,i) ->
			field.fieldType is 'Metric'

		for one in metrics when one.id is this.props.field.id and one.operation is this.props.field.operation
			metric = one
			break

		return unless metric

		_root = {}
		for row in result.rows
			node = _root
			for category, i in categories
				if i < categories.length - 1
					node = node[row[category.index]] ?= {}
			node[row[category.index]] = row	
		
		tranform = (name, node, depth)->
			children = []
			if Array.isArray node
				values = {}
				for metric in metrics
					values["#{metric.operation}(#{metric.aliasName})"] = Number node[metric.index]
				name: name
				category: categories[depth]
				values: values
			else
				for key, value of node
					children.push tranform key, value, depth + 1
				name: name
				category: categories[depth]
				children: children

		this.setState
			root: root = tranform 'root', _root, -1
			categories: categories
			metrics: metrics
			result: result
		console.log root

	componentWillMount: updateState