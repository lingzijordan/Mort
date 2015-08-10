Array::distinct = (func)->
	values = {}
	i = 0
	for item in this
		values[func(item)] ?= i++
	ret = []
	for value, i of values
		ret[i] = value
	this.values = values
	ret

module.exports = React.createClass
	displayName: __filename

	render: require './perspective.jade'

	componentWillReceiveProps: updateState = (props = this.props)->
		{result} = props 

		return unless result
		
		categories = result.fields.filter (field,i) ->
			field.index = i
			field.fieldType is 'Category'
		
		metrics = result.fields.filter (field,i) ->
			field.fieldType is 'Metric'
		
		categories.horizontal = categories[0]
		categories.vertical = categories[1]

		for one in metrics when one.id is this.props.field.id and one.operation is this.props.field.operation
			metrics.metric = one
			break

		metric = metrics.metric
		return unless metric

		horizontal_domain = if categories.horizontal
			domain = result.rows.distinct (d)->
				d[categories.horizontal.index]
			.sort()
			categories.horizontal.values = result.rows.values
			domain
		else
			[undefined]

		categories.horizontal?.domain = horizontal_domain

		vertical_domain = if categories.vertical
			domain = result.rows.distinct (d)->
				d[categories.vertical.index]
			.sort()
			categories.vertical.values = result.rows.values
			domain
		else
			[undefined]

		categories.vertical?.domain = vertical_domain

		metric.grid = for vertical_value in vertical_domain
			[]

		for item in result.rows
			item[metric.index] = +item[metric.index]
			row_num = categories.vertical?.values[item[categories.vertical?.index]] or 0
			col_num = categories.horizontal?.values[item[categories.horizontal.index]] or 0
			metric.grid[row_num][col_num] = item

		for row, i in metric.grid
			for col, j in row
				unless col?
					metric[i][j] = []
					metric[i][j][metric.index] = 0

		this.setState
			categories: categories
			metrics: [metric]

	componentWillMount: updateState