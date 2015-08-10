#$ = require 'jquery'
serialize = require('form-serialize')

module.exports = (formSelector) ->
  #jsonObj = {}
  serialize formSelector, hash:true
  # $.each($(formSelector).serializeArray(), ->
  #   if (jsonObj[this.name])
  #     if (!jsonObj[this.name].push)
  #       jsonObj[this.name] = [jsonObj[this.name]]

  #     jsonObj[this.name].push(this.value || '')
  #   else
  #     jsonObj[this.name] = this.value || ''
  # )
  # jsonObj