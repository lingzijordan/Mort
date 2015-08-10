/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.mort.commons

import skinny.validator.{Messages, Validator}

trait Verifiable {
  def validationRules: Validator

  def hasErrors = validationRules.hasErrors

  def errorMessages: List[String] = {
    val messages: Messages = Messages.loadFromConfig()
    val errors = validationRules.errors
    validationRules.validations.states.map(_.paramDef.key).flatMap { key: String =>
      errors.get(key).map { error =>
        messages.get(
          key = error.name,
          params = key :: error.messageParams.toList
        ).getOrElse(error.name)
      }
    }.toList
  }
}
