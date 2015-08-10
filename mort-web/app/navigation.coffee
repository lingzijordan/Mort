require './navigation.styl'
stores = require './app-stores.coffee'

module.exports = React.createClass
	displayName: __filename

	render: require './navigation.jade'
	
	mixins: [Reflux.connect(stores.navigate)]

	getInitialState:->
		pathes:[]

	toggle: ->
		el = this.getDOMNode()
		if this.isOpen
			el.classList.remove 'anim'
			setTimeout ->
				el.classList.remove 'open'
			,250
		else
			el.classList.add 'anim'
			setTimeout ->
				el.classList.add 'open'
			,250
		
		this.isOpen = not this.isOpen

		null