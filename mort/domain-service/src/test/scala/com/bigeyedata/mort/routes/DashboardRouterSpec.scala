/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import com.bigeyedata.mort.ApiCommonSpec
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.message.dashboard.{DashboardRecord, DashboardDetailResponse}
import spray.http.StatusCodes
import spray.http.StatusCodes._

class DashboardRouterSpec extends ApiCommonSpec with DashboardRouter with MortExceptionHandler {

  ignore should "response get request" in {
    Get("/dashboards") ~> dashboardRoute ~> check {
      status should be(OK)
      val result = responseAs[List[DashboardRecord]]
      assert(result.head.id == 1)
    }
  }

  ignore should "display view by owning view id" in {
    Get("/dashboards/1") ~> dashboardRoute ~> check {
      status should be(OK)
      val result = responseAs[DashboardDetailResponse]
      assert(result.id == 1)
    }
  }

  it should "display illegal view id tips" in {
    Get("/dashboards/100000") ~> dashboardRoute ~> check {
      status should be(StatusCodes.NotFound)
    }
  }

}
