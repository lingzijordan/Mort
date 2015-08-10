require './create-dashboard.page.styl'

actions = require '../actions.coffee'
stores = require '../stores.coffee'
app_actions = require '../../app-actions.coffee'
{NavigatableMixin} = require 'react-router-component'

module.exports = React.createClass
	displayName: __filename

	render: require './create-dashboard.page.jade'

	mixins: [Reflux.connect(stores.createdDashboard),Reflux.connect(stores.views), NavigatableMixin]

	getInitialState: ->
		app_actions.newSenceEnter()
		actions.views()
		views:
			data: []

	handleViewsSelected: (options)->
		this.viewIds = (option.id for option in options)

	submit: (data) ->
		data.views = this.viewIds
		true

	onResponse: actions.createdDashboard

	componentWillUpdate: (nextProps, nextState)->
		if(nextState.createdDashboard && nextState.createdDashboard.data)
			this.navigate '/dashboards'
		if(nextState.createdDashboard && nextState.createdDashboard.error)
			@setState error: nextState.createdDashboard.error