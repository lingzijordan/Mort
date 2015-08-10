require './datasource-editor.styl'
formToJson = require '../../commons/formToJson.coffee'
inputFor = require '../../commons/inputFieldBuilder.coffee'
selectFor = require '../../commons/selectFieldBuilder.coffee'
ValidatorMixin = require '../../commons/components/mixins/refs-validator-mixin.coffee'
actions = require '../actions.coffee'
stores = require '../stores.coffee'

module.exports = React.createClass
  displayName: 'datasource-editor'
  mixins: [ValidatorMixin, Reflux.ListenerMixin]

  getInitialState: ->
    initialState = {}

    initialState.basicFields =
      [
        inputFor('name')
          .withI18n('dataSourceName')
          .withValidator('require')
          .withValidator('maxLength=10')

        inputFor('description')
          .withValidator('require')

        selectFor('dataSourceType')
          .withOptions([{value: 'RDB'}, {value: 'HDFS'}])
          .withDefaultValue('RDB')
          .withChangeCallback(@handleDataSourceTypeChange)
      ]

    initialState.databaseFields =
      [
        selectFor('databaseType')
          .withOptions([{value: 'mysql', i18nKey: "MySQL"}, {value: 'oracle', i18nKey: "Oracle"}])
          .withDefaultValue('mysql')

        inputFor('host')
          .withValidator('require')

        inputFor('port')

        inputFor('database')
          .withValidator('require')

        inputFor('username')
          .withValidator('require')

        inputFor('password')
          .withFieldType('password')
      ]

    initialState.dataSourceType = "RDB"

    initialState

  shouldShow: (dataSourceId)->
    if dataSourceId == @state.dataSourceType
      "block"
    else
      "none"

  handleDataSourceTypeChange: (e)->
    @setState
      dataSourceType: e.target.value

  shouldSubmit: ()->
    @isRefsValid @refs['basicFields'].refs, @refs['rdbFields'].refs

  shouldCheck: ()->
    @isRefsValid @refs['rdbFields'].refs

  submit: (event)->
    actions.create.shouldEmit = @shouldSubmit

    dataSource = formToJson('#basic-form')
    dataSource.options = formToJson('#rdb-form')
    actions.create dataSource

  checkConnection: (event)->
    actions.check.shouldEmit = @shouldCheck
    actions.check {dataSource: formToJson('#rdb-form')}

  componentWillMount: ->
      this.listenTo stores.create, (event)=>
        @setState
          createErrorMessage: event.error

      this.listenTo stores.check, (event)=>
        @setState
          checkConnectionMessage: event.message
  render: require './datasource-editor.jade'