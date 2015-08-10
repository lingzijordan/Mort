d3 = require 'd3'
Snap = require 'snapsvg'

module.exports = (dom, root, metric, colors, categories, metrics, _id)->
	self = this
	
	width = dom.offsetWidth
	height = Math.round width * .62
	barMaxHeight = height - 50

	dom.style.paddingBottom = '62%'

	valuePath = "#{metric.operation}(#{metric.aliasName})"
	partition = d3.layout.partition()
		.value (d)->
			d.values[valuePath]
		.size [width, height]
		.sort (a, b)->
			(a.name - b.name) or (a.name.localeCompare b.name)
	partition.nodes root

	x = d3.scale.ordinal()
		.rangeRoundBands [0, width], .25
		.domain (child.name for child in root.children)
			
	y = d3.scale.linear()
		.range [0, barMaxHeight-10]
		.domain [0, d3.max root.children, (d)-> d.dx]

	barWidth = x.rangeBand()
	
	svg = d3.select(dom).selectAll('svg').data [1]
	svg.enter()
		.append 'svg'
			
	svg.exit().remove()

	svg.attr 'viewBox',"0 0 #{width} #{height}"
		.attr 'preserveAspectRatio', "none"

	data = partition.nodes(root).filter (d)-> d.values?
	{depth} = data[data.length - 1]
	
	nodes = svg.selectAll ".node"
		.data data

	nodesEnter = nodes.enter().append 'g'
		.attr 'class', 'node'
	
	nodesEnter.append 'path'
	nodesEnter.append 'text'
		.attr 'class', 'metric'
		.attr 'fill', '#444'

	nodes.exit().remove()

	path = nodes.select 'path'
	
	switch depth
		when 1
			path
				.each (d, i)->
					await self.setTimeout _id, 10*i, defer()
					Snap(@).stop().animate
						path: "M#{x(d.name)},#{barMaxHeight - y(d.dx)} h#{barWidth} v#{y(d.dx)} h#{-barWidth} Z"
						fill: colors 0
						transform:  "translate(0,0)"
					, 100, mina.ease
		when 2
			path
				.attr 'd', (d)->
					Snap(@).stop()
					"M#{x(d.parent.name)},#{barMaxHeight - y(d.x - d.parent.x) - y(d.dx)} h#{barWidth} v#{y(d.dx)} h#{-barWidth} Z"
				.attr 'fill', (d)-> colors d.name
				.attr 'stroke', 'transparent'
				.attr 'transform': "translate(0,0)"

	nodes.select 'text'
		.attr 'fill', '#444'
		.attr 'x', (d)->
			width / 2
		.attr 'y', (d)->
			20
		.attr 'text-anchor', 'middle'
		.text (d)->
			switch depth
				when 2
					"#{d.name}: #{d.value}"
				when 1
					d.value

	xAxis = svg.selectAll('.x-axis').data [1]
	xAxis.exit().remove()
	xAxis.enter().append 'g'
			.attr 'class', 'x-axis'
	
	xAxisText = xAxis.selectAll 'text'
			.data (d.name for d in root.children)
	xAxisText.enter().append 'text'
		.attr 'text-anchor', 'middle'

	xAxisText.exit().remove()

	xAxis.selectAll('text')
		.text (d)-> d
		.attr 'y', barMaxHeight + 20
		.attr 'x', (d)->
			barWidth/2 + x d

	[".line", '.chunk']