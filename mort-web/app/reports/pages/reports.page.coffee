require './reports.page.styl'

stores = require '../stores.coffee'
actions = require '../actions.coffee'

app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	render: require './reports.page.jade'
	mixins: [Reflux.connect(stores.reports)]
	getInitialState: ->
		app_actions.newSenceEnter()
		actions.reports()
		{}
	componentDidMount:->
		app_actions.navigate
			pathes:[
				{
					name:'reports'
					path:'/reports'
				}
			]