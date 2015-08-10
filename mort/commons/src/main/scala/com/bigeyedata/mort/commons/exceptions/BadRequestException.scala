package com.bigeyedata.mort.commons.exceptions

class BadRequestException(val errorMessage: String, val cause: Exception) extends RuntimeException(errorMessage, cause)

object BadRequestException {
  def apply(message: String) = new BadRequestException(message, null)

  def apply(cause: Exception) = new BadRequestException(null, cause)

  def apply(message: String, cause: Exception) = new BadRequestException(message, cause)
}