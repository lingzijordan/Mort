allValidators =
	require : () ->
		errorMessage: if(isEmpty(extractInputValue.call(this))) then this.state.displayName + ' is required!' else null

	maxLength: (length) ->
		if(extractInputValue.call(this).trim().length > length)
			errorMessage: this.state.displayName + ' more than ' + length

extractInputValue= ()->
	this.refs[this.state.key].getDOMNode().value

isEmpty = (inputValue) ->
	not inputValue.trim()

module.exports =
	validate: ()->
		for validator in this.state.validators when not error?.errorMessage
			validator = validator.split('=')
			this.setState(error = allValidators[validator[0]].call(this, validator[1]))

	isValid: ()->
		not this.state.errorMessage
