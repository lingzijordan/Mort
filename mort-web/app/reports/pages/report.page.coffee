require './report.page.styl'

stores = require '../stores.coffee'
actions = require '../actions.coffee'

app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	render: require './report.page.jade'
	mixins: [Reflux.connect(stores.report)]
	getInitialState: ->
		actions.report this.props
		{}

	componentDidMount: ->
		@listenTo stores.report, (data)=>
			app_actions.navigate
				pathes:[
					{
						name: 'reports'
						path: '/reports'
					}
					{
						path: '/reports/' + this.props.id
						name: this.state.report?.data.report.name or this.props.id
					}
				]