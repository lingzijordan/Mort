/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.customerdata.domainservice.db.router

import spray.http.StatusCodes
import spray.routing.HttpService

trait HealthRouter extends HttpService {

  def healthRoute = pathPrefix("health") {
    pathEnd {
      get {
        complete {
          StatusCodes.OK
        }
      }
    }
  }
}
