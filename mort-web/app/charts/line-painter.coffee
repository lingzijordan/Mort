d3 = require 'd3'
Snap = require 'snapsvg'

module.exports = (dom, root, metric, colors, categories, metrics, _id)->
	self = this
	
	width = dom.offsetWidth
	height = Math.round width * .62
	barMaxHeight = height - 50
	r = 4

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
	
	nodes = svg.selectAll ".node"
		.data data
	
	nodesEnter = nodes.enter().append 'g'
		.attr 'class', 'node'
	
	nodesEnter.append 'path'
	nodesEnter.append 'text'
		.attr 'class', 'metric'
		.attr 'fill', '#fff'

	nodes.exit().remove()

	path = nodes.select 'path'
	{depth} = data[data.length - 1]
	
	switch depth
		when 1
			path
				.each (d, i)->
					await self.setTimeout _id, 20*i, defer()
					Snap(@).stop().animate
						path: "M#{barWidth/2 + x d.name},#{barMaxHeight - y d.dx} m -#{r},0 a #{r},#{r} 0 1,1 #{r*2},0 a #{r},#{r} 0 1,1 -#{r*2},0 Z"
						fill: colors 0
						transform:  "translate(0,0)"
					, 400, mina.elastic
			
			line = svg.selectAll '.line'
				.data [0]
				.enter()
					.append 'g'
					.attr 'class', 'line'
						.append 'path'
						.attr 'stroke', colors 0
						.attr 'fill', 'transparent'
					.each ->
						segs = []
						d0 = data[0]
						for d in data
							segs.push "L#{x(d.name)+barWidth/2}, #{barMaxHeight - y(d.dx)}"
							await Snap(@).stop().animate
								path: "M#{x(d0.name)+barWidth/2}, #{barMaxHeight - y(d0.dx)}" + segs.join ''
							, 10, mina.linear, defer()
		else
			path
				.attr 'd', (d)->
					"M#{barWidth/2 + x(d.parent.name)},#{barMaxHeight - y(d.x - d.parent.x) - y(d.dx)} m -#{r},0 a #{r},#{r} 0 1,1 #{r*2},0 a #{r},#{r} 0 1,1 -#{r*2},0 Z"
				.attr 'fill', (d)->
					colors d.name
				.attr 'transform', "translate(0,0)"

			line = svg.selectAll '.line'
				.data (d.name for d in root.children[0].children)
				.enter()
					.append 'g'
					.attr 'class', 'line'
						.append 'path'
						.attr 'fill', 'transparent'
			svg.selectAll('.line').select 'path'
				.attr 'd', (d, i)->
					d0 = root.children[0].children[i]
					segs = for child in root.children
						c = child.children[i]
						"L#{x(c.parent.name)+barWidth/2}, #{barMaxHeight - y(c.x - c.parent.x) - y(c.dx)}"
					"M#{x(d0.parent.name)+barWidth/2}, #{barMaxHeight - y(d0.x - d0.parent.x) - y(d0.dx)}" + segs.join ''
				.attr 'name', (d)-> d
				.attr 'stroke', colors
			
	nodes.select 'text'
		.attr 'x', (d)->
			width / 2
		.attr 'y', (d)->
			20
		.attr 'text-anchor', 'middle'
		.text (d)->
			d.values?[valuePath] ? d.value

	['.chunk']