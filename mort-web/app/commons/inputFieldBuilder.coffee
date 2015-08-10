i18n = require('../i18n.coffee')
InputFieldComponent = require('./components/input-field-component.coffee')

module.exports = (key)->
  new InputFieldBuilder(key)

class InputFieldBuilder
  constructor: (@key)->
    @displayName = i18n(@key)
    @validators = []

  withI18n: (i18nKey)->
    @displayName = i18n(i18nKey)
    this

  withFieldType: (@fieldType)->
    this

  withDefaultValue: (@defaultValue)->
    this

  withValidator: (validator)->
    @validators.push validator
    this

  build: ->
    InputFieldComponent(@key, @displayName, @fieldType, @defaultValue, @validators)
