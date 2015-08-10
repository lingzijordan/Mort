stores = require '../stores.coffee'
app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	displayName: __filename

	mixins: [Reflux.ListenerMixin, React.addons.LinkedStateMixin]
	
	render: require './datasources.page.jade'
	
	getInitialState:->
		show_add_frame: false
	
	componentWillMount: ->
		this.listenTo stores.created, (result)=>
			if result.created.error
				alert result.created.error
			else
				this.refs.show_add_frame.getDOMNode().checked = false

		this.listenTo stores.datasources, (data)->
			app_actions.newSenceEnter()
				
	componentDidMount: ->
		app_actions.navigate
			pathes: [
				{
					path: '/datasources'
					name: 'datasources'
				}
			]