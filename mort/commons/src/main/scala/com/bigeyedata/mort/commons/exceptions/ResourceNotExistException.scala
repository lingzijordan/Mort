package com.bigeyedata.mort.commons.exceptions

class ResourceNotExistException(val message: String) extends RuntimeException(message)

object ResourceNotExistException {
  def apply(message: String): ResourceNotExistException = new ResourceNotExistException(message)
}
