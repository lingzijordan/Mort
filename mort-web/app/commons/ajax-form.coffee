formToJson = require './formToJson.coffee'
http = require './request.coffee'

dot = require 'dot-object'

module.exports = React.createClass
	displayName: 'AjaxForm'
	
	render: ->
		React.DOM.form
			action: @props.action
			method: @props.method
			onSubmit: @onSubmit
			ref: 'form'
		, this.props.children

	onSubmit: (e)->
		e.preventDefault()
		e.stopPropagation()

		data = this.data()
		if 'function' is typeof this.props.onSubmit
			return unless this.props.onSubmit data

		http[@props.method.toLowerCase()] @props.action, data, @props.onResponse

	data: ->
		data = formToJson this.refs.form.getDOMNode()
		dot.object data
		data	