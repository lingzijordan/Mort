/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.mapping

import com.bigeyedata.mort.infrastructure.metadata.models.ViewWithReport
import com.bigeyedata.mort.message.view.FetchViewResponse

object ViewMapper {
  implicit class FetchViewResponseMapper(viewWithReports: List[ViewWithReport]) {
    def response = viewWithReports.map { viewWithReport =>
      FetchViewResponse(
        viewWithReport.id,
        viewWithReport.name,
        viewWithReport.description,
        viewWithReport.createdBy,
        viewWithReport.createdAt,
        viewWithReport.reportId,
        viewWithReport.reportName
      )
    }
  }
}
