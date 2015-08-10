d3 = require 'd3'
Snap = require 'snapsvg'
topojson = require 'topojson'
china = require 'topojson.data/zh-cn/provinces.json'

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

	features = topojson.feature(china, china.objects.provinces)

	projection = d3.geo.mercator()
		.scale 1
		.translate [0, 0]

	geo_path = d3.geo.path()
		.projection projection

	b = geo_path.bounds features
	s = .95 / Math.max((b[1][0] - b[0][0]) / width, (b[1][1] - b[0][1]) / height)
	t = [(width - s * (b[1][0] + b[0][0])) / 2, (height - s * (b[1][1] + b[0][1])) / 2]
	
	projection
		.scale s
		.translate t
	
	svg = d3.select(dom).selectAll('svg').data [1]
	svg.enter()
		.append 'svg'
			
	svg.exit().remove()

	svg.attr 'viewBox',"0 0 #{width} #{height}"
		.attr 'preserveAspectRatio', "none"

	data = partition.nodes(root).filter (d)-> d.values?
	{depth} = data[data.length - 1]
	
	for i in [data.length .. 32] by 1
		data[i] =
			value: 0

	nodes = svg.selectAll ".node"
		.data data

	nodesEnter = nodes.enter().append 'g'
		.attr 'class', 'node'
	
	nodesEnter.append 'path'
	nodesEnter.append 'text'
		.attr 'class', 'metric'
	nodes.exit().remove()

	path = nodes.select 'path'

	switch depth
		when 1
			path
				.each (d, i)->
					await self.setTimeout _id, 100*i, defer()
					Snap(@).stop().animate
						path: geo_path(features.features[i])
						fill: if d.value then colors(d.name) else '#ccc'
						transform:  "translate(0,0)"
					, 100, mina.ease
		when 2
			path
				.attr 'd', (d, i)->
					geo_path(features.features[i])
				.attr 'fill', (d)->
					if d.value then colors(d.name) else '#ccc'
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

	[".line", '.chunk', '.x-axis']