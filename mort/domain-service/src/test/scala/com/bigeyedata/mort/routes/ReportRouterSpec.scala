package com.bigeyedata.mort.routes

import com.bigeyedata.mort.ApiCommonSpec
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.message.report.{CreateReportRequest, FetchReportResponse}
import org.scalatest.Matchers
import spray.http.StatusCodes._

class ReportRouterSpec extends ApiCommonSpec with Matchers with ReportRouter with MortExceptionHandler {

  "Given right parameters" should "create report" in {
    val request: CreateReportRequest = CreateReportRequest(1, "report" + System.currentTimeMillis(), "desc")
    Post("/reports", request) ~> reportRoute ~> check {
      status should be(Created)

      val location = header("location") match {
        case Some(header) => header.value
        case _ => "fail"
      }

      location should not be "fail"
    }
  }

  "The data set id does not exist" should "return  BadRequest Error code" in {
    val request: CreateReportRequest = CreateReportRequest(100000000, "report" + System.currentTimeMillis(), "desc")
    Post("/reports", request) ~> reportRoute ~> check {
      status should be(BadRequest)
    }
  }

  it should "return BadRequest Error code" in {
    val existingReportName = "测试报表"
    val request: CreateReportRequest = CreateReportRequest(1, existingReportName, "desc")
    Post("/reports", request) ~> reportRoute ~> check {
      status should be(BadRequest)
    }
  }

  it should "return all reports" in {
    Get("/reports") ~> reportRoute ~> check {
      status should be(OK)
      val response = responseAs[List[FetchReportResponse]]
      response should not be empty
    }
  }
}
