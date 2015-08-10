actions = require '../actions.coffee'
stores = require '../stores.coffee'

module.exports = React.createClass
  displayName: __filename

  mixins: [Reflux.connect(stores.created), Reflux.connect(stores.check)]

  render: require './datasource-editor.jade'
  
  getInitialState:-> 
    dataSourceType: 'RDB'
  
  shouldShow: (dataSourceType)->
    dataSourceType is @state.dataSourceType

  handleDataSourceTypeChange: (e)->
    @setState
      dataSourceType: e.target.value

  checkConnection: (event)->
    actions.check dataSource: this.refs.form.data().options

  onResponse: actions.created