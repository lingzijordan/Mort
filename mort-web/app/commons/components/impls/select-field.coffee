module.exports = React.createClass
  displayName: "select-field"

  getInitialState: ->
    this.props.holder

  handleChange: (e) ->
    @setState
      defaultValue: e.target.value
    this.state.changeCallback e

  render: require './select-field.jade'