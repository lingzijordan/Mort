require './datasource-datasets.page.styl'

stores = require '../stores.coffee'
actions = require '../actions.coffee'

app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
	displayName: __filename
	render: require './datasource-datasets.page.jade'
	
	componentDidMount: ->
		app_actions.newSenceEnter()
		app_actions.navigate
			pathes: [
					{
						path: '/datasources'
						name: 'datasources'
					}
					{
						path: '/datasources/' + this.props.dataSourceId
						name: this.props.dataSourceId
					}
					{
						path: '/datasources/' + this.props.dataSourceId + '/datasets'
						name: 'datasets'
					}
				]