d3 = require 'd3'
Snap = require 'snapsvg'

module.exports = (dom, root, metric, colors, categories, metrics, _id)->
	self = this
	
	width = dom.offsetWidth
	height = Math.round width * .62
	radius = Math.min(width, height) / 2

	dom.style.paddingBottom = '62%'

	valuePath = "#{metric.operation}(#{metric.aliasName})"
	partition = d3.layout.partition()
		.value (d)->
			d.values[valuePath]
		.size [2 * Math.PI, radius * radius]
		.sort (a, b)->
			(a.name - b.name) or (a.name.localeCompare b.name)

	arc = d3.svg.arc()
		.outerRadius (d)-> Math.sqrt d.y
		.innerRadius (d)-> Math.sqrt d.y + d.dy
		.startAngle (d)-> d.x
		.endAngle (d)-> d.x + d.dx

	pie = d3.layout.pie().sort(null).value (d)-> d.values[valuePath] ? d.value

	transform = "translate(#{width / 2},#{height / 2})"

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
				path.each (d, i)->
					if d.value
						_d = arc d
						_d = _d.replace /[\d|\.|e|-]+/g, (n)-> if Math.abs(+n) < 1e-10 then 0 else n
						_d = _d.replace /NaN/g, 0
					else
						_d = 'M0,0Z'

					await self.setTimeout _id, 20*i, defer()

					Snap(@).stop().animate
						path: _d
						fill: colors d.name
						transform: transform
					, options.duration, options.transition
			else
				path
					.attr 'd', arc
					.attr 'fill', (d)->
						Snap(@).stop()
						colors d.name
					.attr 'transform', transform
		
		nodes.select 'text'
			.attr 'x', width / 2
			.attr 'y', height / 2
			.attr 'text-anchor', 'middle'
			.text (d)->
				d.values?[valuePath] ? d.value

	[".x-axis", ".line"]