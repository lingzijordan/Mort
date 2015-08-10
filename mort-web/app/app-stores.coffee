actions = require './app-actions.coffee'

_recentSenceInfo = undefined

module.exports =
	navigate: Reflux.createStore
		listenables: [actions]
		onNavigate: (data)->
			this.trigger data
	
	oldSenceExit: Reflux.createStore
		listenables: [actions]		
		onOldSenceExit: (recentSenceInfo)->
			_recentSenceInfo = recentSenceInfo
			this.trigger()

	newSenceEnter: Reflux.createStore
		listenables: [actions]
		onNewSenceEnter: ->
			await process.nextTick defer()
			this.trigger _recentSenceInfo
			
