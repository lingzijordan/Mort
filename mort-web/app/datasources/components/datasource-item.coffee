require './datasource-item.styl'
stores= require '../stores.coffee'
actions = require '../actions.coffee'
#app_actions = require '../../app-actions.coffee'
#app_stores = require '../../app-stores.coffee'

Snap = require 'snapsvg'

module.exports = React.createClass
	displayName: __filename

	render: require './datasource-item.jade'

	mixins: [Reflux.connect(stores.datasource), Reflux.ListenerMixin, React.addons.LinkedStateMixin]

	toggleSelection: (checked)->
		@state.selected = not @state.selected
		actions.select
			datasource: @props.datasource
			selected: @state.selected
	
	getInitialState:->
		dummyProcess: Math.round Math.random() * 100

	svgPaths:
		open:'M273,273c0,0-55.8-23-123-23c-78.2,0-123,23-123,23s23-37.7,23-123C50,79.936,27,27,27,27s46,23,124,23c85,0,122-23,122-23s-23,38.43-23,123C250,229.646,273,273,273,273z'
		close:'M273,273c0,0-55.8,24-123,24c-78.2,0-123-24-123-24S3,235.3,3,150C3,79.936,27,27,27,27S72,3,150,3 c85,0,123,24,123,24s24,38.43,24,123C297,229.646,273,273,273,273z'
		reset:"M273,273C273,273,217.2,273,150,273C71.8,273,27,273,27,273C27,273,27,235.3,27,150C27,79.936,27,27,27,27C27,27,72,27,150,27C235,27,273,27,273,27C273,27,273,65.43,273,150C273,229.646,273,273,273,273C273,273,273,273,273,273"
		#reset:"M273,273C273,273,217.2,273,150,273C71.8,273,27,273,27,273C27,273,27,235.3,27,150C27,79.936,27,27,27,27C27,27,72,27,150,27C235,27,273,27,273,27C273,27,273,65.43,273,150C273,229.646,273,273,273,273C273,273,273,273,273,273".replace(/273/g,'300').replace(/27/g,'0')

	isOpen: false

	# componentWillMount:->
	# 	this.listenTo app_stores.newSenceEnter, (lastSenceInfo)=>
	# 		return unless lastSenceInfo
	# 		return unless lastSenceInfo.page is 'datasource'
	# 		return unless +lastSenceInfo.id is +(this.props.datasource.id)
	# 		this.enter()


	componentDidMount: ->
		svg = Snap this.refs.svg.getDOMNode()
		this.path = svg.select 'path'

		while this.isMounted() and this.state.dummyProcess < 100
			this.setState dummyProcess: 1 + this.state.dummyProcess
			await setTimeout defer(), Math.random() * 3000

	hover: ->
		#this.getDOMNode().classList.add 'active'
		# svg = this.refs.svg.getDOMNode()
		# rect = svg.getBoundingClientRect()

		# svg.style.position = 'fixed'
		# svg.style.width = rect.width
		# svg.style.height = rect.height
		# svg.style.top = rect.top# - 64
		# svg.style.left = rect.left
		# svg.style['z-index'] = 1000
		
		await this.path.stop().animate
			path: this.svgPaths.open
		, 150, mina.easeout, defer()
		await this.path.stop().animate
			path: this.svgPaths.reset
		, 350, mina.elastic, defer()	
	exit: (autocb)->
		return
		this.getDOMNode().classList.add 'active'
		svg = this.refs.svg.getDOMNode()
		rect = svg.getBoundingClientRect()

		svg.style.position = 'fixed'
		svg.style.width = rect.width
		svg.style.height = rect.height
		svg.style.top = rect.top# - 64
		svg.style.left = rect.left
		svg.style['z-index'] = 1000
		
		await this.path.stop().animate
			path: this.svgPaths.open
		, 150, mina.easeout, defer()
		
		svg.classList.add 'transition'
		svg.classList.add 'expand'

		await this.path.stop().animate
			path: this.svgPaths.reset
		, 350, mina.elastic, defer()
		
		#svg.classList.remove 'transition'
		#svg.classList.remove 'expand'
		
		#app_actions.oldSenceExit()
	#componentWillUnMount:->
			
	enter: ->
		svg = this.refs.svg.getDOMNode()
		rect = svg.getBoundingClientRect()

		svg.removeAttribute 'style'
		svg.classList.add 'expand-normal'
		
		await this.path.stop().animate
			path: this.svgPaths.close
		, 250, mina.easeout, defer()

		svg.classList.add 'transition'
		svg.style.width = rect.width
		svg.style.height = rect.height
		svg.style.top = rect.top
		svg.style.left = rect.left
		svg.style['z-index'] = 'auto'

		await this.path.stop().animate
			path: this.svgPaths.reset
		, 800, mina.elastic, defer()

		svg.classList.remove 'transition'
		svg.classList.remove 'expand-normal'
		svg.removeAttribute 'style'

		# await this.path.stop().animate
		# 	path: this.svgPaths.close
		# , 250, mina.easeout, defer()

		# await this.path.stop().animate
		# 	path: this.svgPaths.reset
		# , 250, mina.elastic, defer()

		# await this.path.stop().animate
		# 	path: this.svgPaths.close
		# , 250, mina.easeout, defer()
		# await this.path.stop().animate
		# 	path: this.svgPaths.reset
		# , 800, mina.elastic, defer()
