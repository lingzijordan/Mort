/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import akka.actor.Props
import com.bigeyedata.customerdata.dataengine.db.model.PrepareImportData
import com.bigeyedata.customerdata.dataengine.db.service.{CountRowService, ImportDataActor}
import com.bigeyedata.customerdata.domainservice.db.core.BaseHttpService
import com.bigeyedata.customerdata.domainservice.db.message.ImportRequest
import spray.http.StatusCodes

trait ImportServiceRouter extends BaseHttpService with CountRowService {
  lazy val importActor = actorRefFactory.actorOf(Props[ImportDataActor])
  lazy val importRoute = {
    path("import") {
      post {
        entity(as[ImportRequest]) { request =>
          complete {
            val totalRows: Long = count(request.srcDataSource.databaseProperties, request.rawSql.countSql)
            val sqlStatements: Array[String] = request.splitSqlSections(totalRows)
            importActor ! PrepareImportData(sqlStatements,
              request.destination,
              request.dateSetId,
              request.srcDataSource.databaseProperties,
              request.targetDataSource.databaseProperties,
              request.fields)
            StatusCodes.Accepted
          }
        }
      }
    }
  }


}
