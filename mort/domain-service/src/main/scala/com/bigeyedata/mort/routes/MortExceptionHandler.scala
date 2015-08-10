package com.bigeyedata.mort.routes

import com.bigeyedata.mort.commons.exceptions.{ResourceNotExistException, BadRequestException}
import spray.http.StatusCodes._
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

trait MortExceptionHandler {
  self: HttpService =>

  implicit def mortExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: BadRequestException =>
        log.warning(e.errorMessage)
        complete(BadRequest, e.errorMessage)
      case e: ResourceNotExistException =>
        complete(NotFound, e.message)
    }

}
