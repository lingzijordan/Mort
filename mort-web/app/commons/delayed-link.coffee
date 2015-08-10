{Link} = require 'react-router-component'
Link = React.createFactory Link

module.exports = React.createClass
	onClick: (e)->
		e.preventDefault()
		switch typeof this.props.delay
			when 'function'
				await this.props.delay defer()
			when 'number'
				await setTimeout defer(), this.props.delay

		this.getDOMNode().click()

	render: ->
		children = React.DOM.div
			onClick: this.onClick
		, this.props.children
		
		Link this.props, children
