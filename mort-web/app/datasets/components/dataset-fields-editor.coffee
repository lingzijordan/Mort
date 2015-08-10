datasource_stores = require '../../datasources/stores.coffee'
datasource_actions = require '../../datasources/actions.coffee'
LinkedStateMixin = require 'react-catalyst/src/catalyst/LinkedStateMixin'

getDuplicateValues = (map)->
	aliasNameToCount = {}
	for key, value of map
		aliasNameToCount[value] ?= []
		aliasNameToCount[value].push key

	ret = {}
	for aliasName, nameArray of aliasNameToCount
		if nameArray.length > 1
			ret[aliasName] = nameArray

	ret

module.exports = React.createClass
	displayName: __filename

	render: require './dataset-fields-editor.jade'
	
	mixins: [Reflux.ListenerMixin, Reflux.connect(datasource_stores.fields), LinkedStateMixin]

	getIntialState: ->
		show:false

	fetchFields: fetchFields = ->
		datasource_actions.fields this.props

	componentDidMount: fetchFields
	
	submit: ->
		this.setState duplicateValues: getDuplicateValues(this.state.nameToAliasName)

	getFieldTypes: (field)->
		switch field.dataClassName
			when "java.math.BigDecimal", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double"
				['Metric', 'Category']
			when "java.sql.Date", "java.sql.Time", "java.sql.Timestamp"
				['Metric', 'Category', 'TimeCategory']
			else
				['Category']