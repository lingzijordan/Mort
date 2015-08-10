module.exports = React.createClass
  displayName: 'field-set'

  getInitialState: ->
    fields: this.props.fields

  render: require './field-set.jade'