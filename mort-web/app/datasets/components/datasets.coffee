
stores = require '../stores.coffee'
actions = require '../actions.coffee'

module.exports = React.createClass
	displayName: __filename
	
	render: require './datasets.jade'
	
	mixins: [Reflux.connect(stores.dataSets)]
	
	getInitialState: ->
		actions.dataSets this.props.dataSourceId
		{}