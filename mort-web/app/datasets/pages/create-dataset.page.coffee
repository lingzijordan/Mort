# require './create-dataset.page.styl'

# stores = require '../stores.coffee'
# actions = require '../actions.coffee'
# app_actions = require '../../app-actions.coffee'
# datasource_actions = require '../../datasources/actions.coffee'

# sqlparser = require 'simple-sql-parser'

# {NavigatableMixin} = require 'react-router-component'

# module.exports = React.createClass
# 	displayName: __filename
	
# 	render: require './create-dataset.page.jade'

# 	mixins: [Reflux.ListenerMixin, React.addons.LinkedStateMixin, NavigatableMixin]

# 	getInitialState: ->
# 		sql: 'select * from bigeye_data_sources'
# 		sql2ast:
# 			status: true

# 	componentDidMount: ->
# 		app_actions.newSenceEnter()
# 		app_actions.navigate
# 			pathes: [
# 				{
# 					path: '/datasources'
# 					name: 'datasources'
# 				}
# 				{
# 					path: '/datasources/' + @props.datasourceId
# 					name: @props.datasourceId
# 				}
# 				{
# 					path: '/datasources/' + @props.datasourceId + '/datasets/new'
# 					name: "create dataSet"
# 				}
# 			]

# 		this.listenTo stores.created, (event)->
# 			console.log event

# 	preview: ->
# 		sql = this.state.sql.replace /;*$/g, ''
# 		ast = sqlparser.sql2ast sql
# 		if ast.status
# 			datasource_actions.preview
# 				datasourceId: @props.datasourceId
# 				sql: sql

# 		this.setState
# 			sql2ast: ast

# 	onSubmit: (data)->
# 		data.dataSourceId = +data.dataSourceId
# 		fields = []
# 		for index, field of data.fields
# 			fields[index] = field
# 			field.length = +field.length
# 			field.scale = +field.scale

# 		data.fields = fields
# 		true

# 	onResponse: actions.created
