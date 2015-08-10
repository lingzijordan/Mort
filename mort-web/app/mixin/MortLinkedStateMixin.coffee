LinkedStateMixin = require 'react-catalyst/src/catalyst/LinkedStateMixin'

exports.for = (specificName)=>
  linkSpecificState: (path)=>
      LinkedStateMixin.linkState.call self, if specificName then specificName + '.' + path else path