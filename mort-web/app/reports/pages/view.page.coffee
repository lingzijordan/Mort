stores = require '../stores.coffee'
actions = require '../actions.coffee'

app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	render: require './view.page.jade'
	mixins: [Reflux.connect(stores.view)]
	getInitialState: ->
		actions.view this.props
		{}