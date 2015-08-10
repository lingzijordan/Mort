d3 = require 'd3'

module.exports = React.createClass
	render: require './chart-with-labels.jade'

	displayName: __filename

	componentWillMount: getLabels = ->
		#colors = d3.scale.ordinal().range ["#f1c40f", "#e67e22", "#e74c3c", "#95a5a6", "#f39c12", "#d35400", "#c0392b", "#bdc3c7", "#7f8c8d","#1abc9c", "#2ecc71", "#3498db", "#9b59b6", "#34495e", "#16a085", "#27ae60", "#2980b9", "#8e44ad", "#2c3e50"]
		colors = d3.scale.category20c()
		state = colors: colors
		if this.props.categories.vertical?.domain
			labels = for value in this.props.categories.vertical.domain
				color: colors value
				label: value
			state.labels = labels.reverse()
				
		this.setState state

	componentWillRecieveProps: getLabels

	componentDidUpdate: (@prevProps)->
		#return if JSON.stringify(@prevProps) is JSON.stringify(this.props)
		this.draw()

	componentDidMount: ->
		this.draw()

	_id: 0

	setTimeout: (id, timeout, cb)->
		await setTimeout defer(), timeout
		cb() if id is @_id

	draw:->
		dom = this.refs.content.getDOMNode()

		loop
			width = dom.offsetWidth
			break if width
			return unless @isMounted()
			await requestAnimationFrame defer()

		{categories, metrics, root, field} = this.props
		{colors} = this.state

		painter = require("./#{this.props.viewType.toLowerCase()}-painter.coffee")
		noNeed = painter.call this, dom, root, field, colors, categories, metrics, ++@_id
		if Array.isArray noNeed
			for type in noNeed
				d3.select(dom).selectAll('svg').selectAll(type).remove()