/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.dashboard.DashboardMessages.CreateDashboard
import com.bigeyedata.mort.infrastructure.metadata.services.DashboardService

trait DashboardCreator extends DashboardService{
  def createDashboard(createDashboard: CreateDashboard): PrimaryKey = {
    if(hasExistDashboard(createDashboard.name))
      throw BadRequestException(s"Dashboard name [${createDashboard.name}] exist")

    create(createDashboard)
  }
}
