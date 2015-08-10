xhr = require 'xhr'

request = (method)->
	(url,data,cb)->
		if 'function' is typeof data
			cb = data
			data = {}

		options =
			method: method
			url:url	
		if method is 'POST'
			options.json = data

		xhr options, (err, resp, body)->
			if -1 < resp.headers["content-type"].indexOf "application/json"
				if 'string' is typeof resp.body
					try
						resp.body = JSON.parse resp.body
			cb? resp

module.exports = 
	post: request 'POST'
	get: request 'GET'

