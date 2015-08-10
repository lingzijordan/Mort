stores = require '../stores.coffee'
routerActions = require '../../app-actions.coffee'

module.exports = React.createClass
	
	mixins: [Reflux.ListenerMixin, React.addons.LinkedStateMixin]
	
	render: require './datasets.page.jade'
	
	componentDidMount: ->
		routerActions.navigate
			pathes: [
				{
					path: '/datasets'
					name: 'datasets'
				}
			]