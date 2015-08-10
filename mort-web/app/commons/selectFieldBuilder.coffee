i18n = require('../i18n.coffee')
SelectFieldComponent = require('./components/select-field-component.coffee')

module.exports = (key) ->
  new SelectFieldBuilder(key)

class SelectFieldBuilder
  constructor: (@key)->
    @displayName = i18n(@key)

  withI18n: (i18nKey)->
    @displayName = i18n(i18nKey)
    this

  withOptions: (options)->
    optionsObj = []
    map = (option) ->
      optionsObj.push(
        {
          value: option.value
          displayName: i18n(option.i18nKey ? option.value)
        }
      )
    map(option) for option in options
    @options = optionsObj
    this

  withChangeCallback: (@changeCallback)->
    this

  withDefaultValue: (@defaultValue)->
    this

  build: ->
    SelectFieldComponent(@key, @displayName, @options, @defaultValue, @changeCallback)