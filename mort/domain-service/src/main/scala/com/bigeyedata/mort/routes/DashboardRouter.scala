/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.commons.implicits.DateTimeImplicits._
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.commons.implicits.StringImplicits._
import com.bigeyedata.mort.message.dashboard.{DashboardDetailResponse, CreateDashboardRequest}
import com.bigeyedata.mort.message.mapping.DashboardMapper._
import com.bigeyedata.mort.report.services.DashboardCreator
import spray.http.HttpHeaders.Location
import spray.http.StatusCodes._
import spray.routing.HttpService

trait DashboardRouter extends HttpService with DashboardCreator{
  def dashboardRoute = pathPrefix("dashboards") {
    pathEnd {
      post {
        entity(as[CreateDashboardRequest]) { request =>
          if (request.hasErrors) {
            complete(BadRequest, request.errorMessages)
          } else {
            val id: PrimaryKey = createDashboard(request.toCreateDashboard())
            respondWithHeaders(List(new Location("dashboards" / id))) {
              complete(Created)
            }
          }
        }
      } ~
        get {
          complete(fetchDashboards.toResponse)
        }
    } ~
      path(IntNumber) { dashboardId =>
        val views = fetchViewsByDashboardId(dashboardId).toResponse
        val dashboard = fetchDashboard(dashboardId)
        complete(
          DashboardDetailResponse(
            dashboard.id,
            dashboard.name,
            dashboard.description.getOrElse(""),
            dashboard.createdAt,
            dashboard.updatedAt.get,
            dashboard.createdBy, dashboard.updatedBy, views)
        )
      }

  }
}
