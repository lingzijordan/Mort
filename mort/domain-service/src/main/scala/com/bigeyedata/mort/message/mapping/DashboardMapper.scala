/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.mapping

import com.bigeyedata.mort.commons.implicits.DateTimeImplicits._
import com.bigeyedata.mort.infrastructure.metadata.models.{Dashboard, View}
import com.bigeyedata.mort.message.dashboard.{DashboardRecord, ViewDetail}

object DashboardMapper {

  implicit class FetchDashboardResponseMapper(dashboards: List[Dashboard]) {

    def toResponse: List[DashboardRecord] = {

      dashboards.map { dashboard =>
        DashboardRecord(dashboard.id,
          dashboard.name,
          dashboard.description.getOrElse(""),
          dashboard.createdAt,
          dashboard.updatedAt.get,
          dashboard.createdBy,
          dashboard.updatedBy)
      }

    }
  }

  implicit class FetchViewsOfDashBoards(views: List[View]) {
    def toResponse: List[ViewDetail] = {
      views.map { view =>
        ViewDetail(view.id,
          view.name,
          view.description,
          view.viewType,
          view.generatedQuery,
          view.createdAt,
          view.updatedAt,
          view.createdBy,
          view.updatedBy
        )
      }
    }
  }

}
