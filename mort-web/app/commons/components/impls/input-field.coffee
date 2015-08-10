commonStores = require '../../common-stores.coffee'
ValidatorMixin = require '../mixins/basic-validator-mixin.coffee'

module.exports = React.createClass
	displayName: 'input-field'
	mixins: [Reflux.ListenerMixin, ValidatorMixin]

	componentDidMount: ->
		@listenTo commonStores.validate, @validate

	getInitialState: ->
		this.props.holder

	render: require './input-field.jade'