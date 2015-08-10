require './create-report.page.styl'

actions = require '../actions.coffee'
stores = require '../stores.coffee'
app_actions = require '../../app-actions.coffee'
{NavigatableMixin} = require 'react-router-component'

module.exports = React.createClass
	displayName: __filename

	render: require './create-report.page.jade'

	mixins: [Reflux.connect(stores.createdReport), NavigatableMixin]

	getInitialState: ->
		app_actions.newSenceEnter()
		{}

	submit: (data) ->
		data.dataSetId = Number(this.props.dataSetId)
		true

	onResponse: actions.createdReport

	componentWillUpdate: (nextProps, nextState)->
		if(nextState.createdReport)
			this.navigate '/reports'

	componentDidMount:->
		app_actions.newSenceEnter()
		app_actions.navigate
			pathes: [
					{
						path: '/datasets'
						name: 'datasets'
					}
					{
						path: '/datasets/' + this.props.dataSetId
						name: this.props.dataSetId
					}
					{
						path: '/datasets/' + this.props.dataSetId + '/reports/new'
						name: 'create report'
					}
				]