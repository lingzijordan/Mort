package com.bigeyedata.mort.commons.implicits

object StringImplicits {

  implicit class StringImplicit(value: String) {
    def /(id: Int) = value + "/" + id

    def isRequired: String = s"$value is required"

    def mustBeNumber: String = s"$value must be number"

    def shouldNotBeEmpty: String = s"$value should not be empty"

  }

}
