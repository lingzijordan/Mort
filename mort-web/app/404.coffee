{DOM} = React

module.exports = React.createClass
	render:->
		DOM.h1
			style:
				color: 'red'
				textAlign:'center'
				margin: 0
				lineHeight: 'calc(100vh - 64px)'
		, '404 / not implemented'