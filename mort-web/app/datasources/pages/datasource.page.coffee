stores = require '../stores.coffee'
actions = require '../actions.coffee'

datasetStores = require '../../datasets/stores.coffee'
datasetActions = require '../../datasets/actions.coffee'

app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	displayName: __filename
	
	render: require './datasource.page.jade'

	mixins: [Reflux.connect(stores.datasource),React.addons.LinkedStateMixin]

	getInitialState: ->
		actions.datasource this.props
		{}

	componentDidMount: ->
		@listenTo stores.datasource, (data)->
			app_actions.newSenceEnter()
			app_actions.navigate
				pathes: [
					{
						path: '/datasources'
						name: 'datasources'
					}
					{
						path: '/datasources/' + @props.id
						name: @state.datasource.data.name or @props.id
					}
				]

		@listenTo datasetStores.created, (data)=>
			if data.created.error
				alert data.created.error
			else
				this.refs.show_add_frame.getDOMNode().checked = false
				datasetActions.dataSets this.props.id

	componentWillUnmount: ->
		app_actions.oldSenceExit
			page: 'datasource'
			id: this.props.id