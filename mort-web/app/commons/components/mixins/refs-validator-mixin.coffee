module.exports =
  isRefsValid: ()->
    valid = true
    for refs in arguments
      for key, value of refs
        refs[key].validate?()
        if valid and refs[key].isValid
          valid = refs[key].isValid()
    valid