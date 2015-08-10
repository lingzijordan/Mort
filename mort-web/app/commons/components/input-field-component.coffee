InputField = React.createFactory(require('./impls/input-field.coffee'))

module.exports = (fieldKey, displayName, fieldType, defaultValue, validators) ->
  holder = 
    key: fieldKey
    displayName: displayName
    fieldType: fieldType ? 'text'
    defaultValue: defaultValue ? ''
    validators: validators

  render: ->
      InputField({holder: holder, ref: holder.key})