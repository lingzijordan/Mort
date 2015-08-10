action = require '../actions.coffee'
stores = require '../stores.coffee'
app_actions = require '../../app-actions.coffee'

module.exports = React.createClass
  displayName: __filename
  render: require './dashboard.page.jade'
  mixins: [Reflux.connect(stores.dashboardDetail)]
  getInitialState: ()->
    action.dashboardDetail this.props
    {dashboardDetail:{}}