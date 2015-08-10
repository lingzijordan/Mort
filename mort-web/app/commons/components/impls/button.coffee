module.exports = React.createClass
  displayName: 'button'

  getInitialState: ->
    this.props.holder

  render: require './button.jade'

  handleClick: (e) ->
    @state.handleClick(e)