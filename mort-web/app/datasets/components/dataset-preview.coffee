datasource_stores = require '../../datasources/stores.coffee'
datasource_actions = require '../../datasources/actions.coffee'

module.exports = React.createClass
	displayName: __filename
	
	mixins: [Reflux.ListenerMixin, Reflux.connect(datasource_stores.preview)]

	render: require './dataset-preview.jade'

	componentWillMount:->
		this.listenTo datasource_stores.preview, =>
			await setTimeout defer()
			this.refs.fields.fetchFields()

	preview: ->
		datasource_actions.preview
			dataSourceId: @props.dataSourceId
			dataSource: @props.dataSource
			sql: @props.sql
