global.React = require 'react/addons'
global.Reflux = require 'reflux'
require './app.styl'

stores = require './app-stores.coffee'

app = React.createClass
	displayName: __filename
	
	render: require './app.jade'

	# mixins: [Reflux.ListenerMixin]

	# componentDidMount: ->
	# 	mask = this.refs.mask.getDOMNode()

	# 	this.listenTo stores.oldSenceExit, =>
	# 		mask.classList.remove 'transition'
	# 		mask.classList.add 'show'
	# 		mask.classList.add 'transition'
	# 		mask.classList.add 'to91'
				
	# 	this.listenTo stores.newSenceEnter, =>
	# 		mask.classList.remove 'to91'
	# 		mask.classList.add 'transition'
	# 		mask.classList.remove 'show'

React.render React.createElement(app), document.body