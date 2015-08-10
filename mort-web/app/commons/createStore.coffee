firstLetterToUppderCase = (word)->
	word.replace /^\w/, (w)-> w.toUpperCase()

isErrorStatusCode = (code)->
	code >= 400

module.exports = (actions, name, options={})->
	store = listenables: [actions]
	_name = firstLetterToUppderCase name

	store["on#{_name}"] = (args...)=>
		if 'function' is typeof options.request
			await options.request.bind(ret) args..., defer response
		else
			response = args[0]

		action = actions[name][response.statusCode]
		unless action
			action_name = if isErrorStatusCode response.statusCode
				'failed'
			else
				'completed'
			action = actions[name][action_name]

		action response

	for childAction in actions[name].children or []
		do (childAction)=>
			isErrorAction = false
			if typeof childAction is 'string'
				isErrorAction = childAction is 'failed'
				_childAction = firstLetterToUppderCase childAction
			else
				isErrorAction = isErrorStatusCode childAction
				_childAction = childAction

			function_name = "on#{_name}#{_childAction}"

			store[function_name] = options[function_name] ? options[childAction] ? (response)->
				event = {}
				event[name] = {}
				event[name].response = response
				if isErrorAction
					event[name].error = response.body
					event[name].data = null
				else
					event[name].error = null
					event[name].data = response.body
				this.trigger event
	
	ret = Reflux.createStore store