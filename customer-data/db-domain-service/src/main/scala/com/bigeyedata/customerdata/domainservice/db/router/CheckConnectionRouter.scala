package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.dataengine.db.service.CheckConnectionService
import com.bigeyedata.customerdata.domainservice.db.core.BaseHttpService
import com.bigeyedata.customerdata.domainservice.db.message.{ConnectionRequest}
import spray.http.StatusCodes._

/* *\
** **
** __ __ _________ _____ ©Mort BI **
** | \/ / () | () |_ _| (c) 2015 **
** |_|\/|_\____|_|\_\ |_| http://www.bigeyedata.com **
** **
\* */
trait CheckConnectionRouter extends BaseHttpService with CheckConnectionService {
  val checkRoute =
    path("connection") {
      post {
        entity(as[ConnectionRequest]) { request =>
          complete {
            connectionAvailable(request.dataSource.databaseProperties)
            match {
              case true => OK
              case false => BadRequest
            }
          }
        }
      }
    }
}
