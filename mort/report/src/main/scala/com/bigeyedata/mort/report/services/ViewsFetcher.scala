/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types.ID
import com.bigeyedata.mort.infrastructure.metadata.models.View
import com.bigeyedata.mort.infrastructure.metadata.services.ViewService

trait ViewsFetcher extends ViewService{

  def fetchWithReportId(reportId: ID): List[View] = {
    fetchByReportId(reportId)
  }

  def fetchView(viewId: ID): Option[View] = {
    fetch(viewId)
  }
}
