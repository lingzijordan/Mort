request = require 'superagent'
{assert} = require 'chai'

describe '/datasources', ->
	it 'response some datasources', (done)->
		await request.get('http://localhost/data-sources').end defer err, res
		console.log res.body
		assert Array.isArray res.body
		done()