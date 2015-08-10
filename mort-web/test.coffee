console.log '123'
request = require 'superagent'
describe '/datasources', ->
	it 'response some datasources', (done)->
		await request.get('http://localhost/datasources').end defer err, res
		console.log res
		expect(res.body.length).toBeGreaterThan 0
		done()