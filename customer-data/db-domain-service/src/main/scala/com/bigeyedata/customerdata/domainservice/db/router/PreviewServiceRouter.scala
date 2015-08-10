/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.dataengine.db.service.{CountRowService, PreviewService}
import com.bigeyedata.customerdata.domainservice.db.core.BaseHttpService
import com.bigeyedata.customerdata.domainservice.db.message.{PreviewRequest, PreviewResponse}

trait PreviewServiceRouter extends BaseHttpService with PreviewService with CountRowService {
  val previewRoute =
    path("preview") {
      post {
        entity(as[PreviewRequest]) { request =>
          complete {
            val dataFrame = preview(request.databaseProperties, request.previewSql)
            val totalRowCount = count(request.databaseProperties, request.countSql)
            PreviewResponse(
              dataFrame.schema.map(_.name),
              dataFrame.rows.map(_.cells),
              totalRowCount)
          }
        }
      }
    }
}
