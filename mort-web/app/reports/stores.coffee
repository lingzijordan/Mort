actions = require './actions.coffee'
http = require '../commons/request.coffee'
createStore = require '../commons/createStore.coffee'

module.exports =
	fields: createStore actions, 'fields', 
		request: (report, autocb)->
			await http.get "/reports/#{report.id}/fields", defer response
			response

	aggregate: createStore actions, 'aggregate',
		request: (data, cb)->
			unless data.canAggregate
				return this.trigger aggregate:
					data:null
			await http.post '/analysis-result', data, defer response
			cb response

	createdReport: createStore actions, 'createdReport'

	reports: createStore actions, 'reports',
		request: (autocb) ->
			await http.get '/reports', defer response
			response

	report: createStore actions, 'report',
		request: (options, autocb) ->
			await http.get "/reports/#{options.id}", defer response
			response
	view: createStore actions, 'view',
		request: (options, autocb) ->
			await http.get "/views/#{options.id}", defer response
			response

	createdView: createStore actions, 'createdView'