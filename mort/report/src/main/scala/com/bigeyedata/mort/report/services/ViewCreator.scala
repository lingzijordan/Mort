/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.infrastructure.metadata.services.{ViewService, ReportService}
import com.bigeyedata.mort.report.ViewMessage.CreateView

trait ViewCreator extends ReportService with ViewService {
  def createView(view: CreateView): PrimaryKey = {
    if (hasNotExistReport(view.reportId)) {
      throw BadRequestException(s"Report ${view.reportId} does not exist")
    }
    if (hasExistViewName(view.reportId, view.name)) {
      throw BadRequestException(s"Duplicated View name [${view.name}] with Same Report ${view.reportId}")
    }
    create(view)
  }
}
