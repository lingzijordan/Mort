/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.core

import java.sql.SQLException

import org.json4s.{DefaultFormats, Formats}
import spray.http.StatusCodes.InternalServerError
import spray.httpx.Json4sSupport
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

trait BaseHttpService extends HttpService with Json4sSupport {
  implicit def customerDataExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: SQLException =>
        requestUri { uri =>
          log.error("Request for {} is failed. Error: {} is {} {} {}", uri.path, e.getMessage, e.getStackTraceString, e.getCause)
          complete(InternalServerError, s"Request to ${uri.path} could not be handled due to ${e.getMessage}")
        }
    }
  implicit lazy val json4sFormats: Formats = DefaultFormats
}
