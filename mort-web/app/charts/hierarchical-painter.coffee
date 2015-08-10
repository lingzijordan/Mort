d3 = require 'd3'
Snap = require 'snapsvg'

module.exports = (dom, root, metric, colors, categories, metrics, _id)->
	self = this
	
	width = dom.offsetWidth
	height = Math.round width * .62
	barMaxHeight = height - 30

	dom.style.paddingBottom = '62%'

	valuePath = "#{metric.operation}(#{metric.aliasName})"
	partition = d3.layout.partition()
		.value (d)->
			d.values[valuePath]
		.size [width, height]
		.sort (a, b)->
			(a.name - b.name) or (a.name.localeCompare b.name)

	x = d3.scale.linear()
		.range [0, width]
	y = d3.scale.linear()
		.range [0, height]
	
	svg = d3.select(dom).selectAll('svg').data [1]
	svg.enter()
		.append 'svg'
			
	svg.exit().remove()

	svg.attr 'viewBox',"0 0 #{width} #{height}"
		.attr 'preserveAspectRatio', "none"

	data = partition.nodes root
	{depth} = data[data.length - 1]

	nodes_with_type = [
		{
			type: 'chunk' 
			filter:(d)-> not d.values
			duration: 100
			transition: mina.ease
		}
		{
			type: 'node'
			filter:(d)-> d.values
			duration: 100
			transition: mina.ease
		}
	]

	for options in nodes_with_type

		nodes = svg.selectAll ".#{options.type}"
			.data data.filter options.filter
		
		nodesEnter = nodes.enter().append 'g'
			.attr 'class', options.type
		
		nodesEnter.append 'path'
		nodesEnter.append 'text'
			.attr 'class', 'metric'
			.attr 'fill', '#fff'
			
		nodes.exit().remove()

		path = nodes.select 'path'
		switch depth
			when 1
				path.each (d,i)->
					await self.setTimeout _id, 20*i, defer()
					
					Snap(@).stop().animate
						path: "M#{d.x},#{d.y} h#{d.dx} v#{d.dy} h#{-d.dx} Z"
						fill: colors d.name
						transform:  "translate(0,0)"
					, options.duration, options.transition
			else
				path
					.attr 'd', (d)->
						"M#{d.x},#{d.y} h#{d.dx} v#{d.dy} h#{-d.dx} Z"
					.attr 'fill', (d)->
						colors d.name
					.attr 'transform', "translate(0,0)"

		nodes.select 'text'
			.attr 'x', (d)->
				width / 2
			.attr 'y', (d)->
				20
			.attr 'text-anchor', 'middle'
			.text (d)->
				d.values?[valuePath] ? d.value

	[".x-axis", ".line"]