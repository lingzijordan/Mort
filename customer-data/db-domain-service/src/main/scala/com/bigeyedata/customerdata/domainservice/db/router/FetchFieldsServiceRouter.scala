/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.common.model.FieldType
import com.bigeyedata.customerdata.dataengine.db.service.FetchFieldsService
import com.bigeyedata.customerdata.domainservice.db.core.BaseHttpService
import com.bigeyedata.customerdata.domainservice.db.message.{FieldsRequest, FieldsResponse}
import org.json4s.ext.EnumNameSerializer
import org.json4s.{DefaultFormats, Formats}

trait FetchFieldsServiceRouter extends BaseHttpService with FetchFieldsService {
  override implicit lazy val json4sFormats: Formats = DefaultFormats + new EnumNameSerializer(FieldType)

  val fieldsRoute = path("fields") {
    post {
      entity(as[FieldsRequest]) { request =>
        complete {
          val fields = fetchFields(request.databaseProperties, request.queryFieldsSql)
          FieldsResponse(fields)
        }
      }
    }
  }
}
