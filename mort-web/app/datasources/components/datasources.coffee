require './datasources.styl'
stores = require '../stores.coffee'
actions = require '../actions.coffee'

module.exports = React.createClass
	displayName: __filename

	mixins: [Reflux.connect(stores.datasources), Reflux.ListenerMixin]

	render: require './datasources.jade'

	getInitialState: ->
		actions.datasources()
		{}

	componentWillMount: ->
		this.listenTo stores.created, (result)->
			actions.datasources()