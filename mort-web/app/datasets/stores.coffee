actions = require './actions.coffee'
http = require '../commons/request.coffee'
createStore = require '../commons/createStore.coffee'

module.exports =

	created: createStore actions, 'created'
	
	dataSets: createStore actions, 'dataSets',
		request: (dataSourceId, autocb) ->
			if 'function' is typeof dataSourceId
				autocb = dataSourceId
				url = '/data-sets'
			else
				url = "/data-sources/#{dataSourceId}/data-sets"
			await http.get url, defer response
			response