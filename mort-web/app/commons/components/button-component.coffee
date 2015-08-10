Button = React.createFactory(require('./impls/button.coffee'))

module.exports = (handleClick, i18nKey, buttonType) ->
  ->
    Button({'holder': {'i18nKey': i18nKey ? 'submit', 'buttonType': buttonType ? 'submit', 'handleClick': handleClick}})