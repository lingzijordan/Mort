action = require '../actions.coffee'
stores = require '../stores.coffee'
app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
  displayName: __filename
  render: require './dashboards.page.jade'
  mixins: [Reflux.connect(stores.dashboards)]
  getInitialState: ()->
    action.dashboards()
