actions = require './common-actions.coffee'
module.exports =
  validate: Reflux.createStore
    listenables: [actions]
    onValidate: ->
      @trigger()
