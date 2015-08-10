SelectField = React.createFactory(require('./impls/select-field.coffee'))

module.exports = (fieldKey, displayName, options, defaultValue, changeCallback) ->
  holder =
    key: fieldKey
    displayName: displayName
    options: options
    defaultValue: defaultValue
    changeCallback: changeCallback ? (e) ->

  render: ->
      SelectField({holder: holder, ref: holder.key})
