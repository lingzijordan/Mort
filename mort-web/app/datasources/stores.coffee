actions = require './actions.coffee'
http = require '../commons/request.coffee'
createStore = require '../commons/createStore.coffee'

getDataSource = (options)->
	data = {}
	{dataSource} = options
	dataSource = dataSource.options
	dataSource.database = dataSource.databaseName
	dataSource.username = dataSource.userName

	dataSource.username = 'bigeye'
	dataSource.password = 'bigeye123'
	
	dataSource.databaseType = 'mysql'

	data.dataSource = dataSource
	data.rawSql =
			statement: options.sql
	data.rowCount= 10
	data

module.exports =
	datasources: createStore actions, 'datasources',
		request: (autocb)->
			await http.get '/data-sources', defer response
			response

	datasource: createStore actions, 'datasource',
		request: (options, autocb)->
			await http.get "/data-sources/#{options.id}", defer response
			response

	preview: createStore actions, 'preview',
		request: (options, autocb)->
			data = getDataSource options
			await http.post '/cdata/preview', data, defer response
			response.body.datasourceId = options.datasourceId

			response
	
	fields: createStore actions, 'fields',
		request: (options, autocb)->
			data = getDataSource options
			await http.post '/cdata/fields', data, defer response
			response
		
		completed: (response)->
			fields_map = {}
			for field in response.body.fields
				fields_map[field.name] = field.aliasName

			this.trigger fields:
				error: null
				data: response.body
				nameToAliasName: fields_map

	check: createStore actions, 'check',
		request: (options, autocb)->
			await http.post '/cdata/connection', options, defer response
			response

	created: createStore actions, 'created'