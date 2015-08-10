require './index.styl'
actions = require './app-actions.coffee'

module.exports = React.createClass
	render: require './index.jade'
	displayName: __filename

	componentDidMount: ->
		actions.navigate
			pathes: []