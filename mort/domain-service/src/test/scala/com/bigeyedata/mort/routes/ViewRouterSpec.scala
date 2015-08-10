/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import com.bigeyedata.mort.{RandomGenerator, ApiCommonSpec}
import com.bigeyedata.mort.commons.enum.ViewType._
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.dataset.FieldMessages.{CategoryUnit, MetricUnit}
import com.bigeyedata.mort.message.view.CreateViewRequest
import org.scalatest.Matchers
import spray.http.StatusCodes._

class ViewRouterSpec extends ApiCommonSpec with ViewRouter with Matchers with MortExceptionHandler with RandomGenerator {

  val metrics = List(MetricUnit(1, "sum"))
  val categories: List[CategoryUnit] = List()

  override def actorContext = mortActorSystem

  it should "create view given right request" in {

    val request = CreateViewRequest(1, "test view " + randomString(), "test view description", BarChart, metrics, categories)
    Post("/views", request) ~> viewRoute ~> check {
      status should be(Created)
      header("location").getOrElse("fail") should not be("fail")
    }
  }

  it should "return BadRequest giving existed view name with same report id" in {
    val DUPLICATED_VIEW_NAME = "测试视图"

    val request = CreateViewRequest(1, DUPLICATED_VIEW_NAME, "error view description", BarChart, metrics, categories)
    Post("/views", request) ~> viewRoute ~> check {
      status should be(BadRequest)
    }
  }

  "View Router" should "return BadRequest giving not existed report id" in {
    val NOT_EXISTED_REPORT_ID = 10000

    val request = CreateViewRequest(NOT_EXISTED_REPORT_ID, "error view", "error view description", BarChart, metrics, categories)
    Post("/views", request) ~> viewRoute ~> check {
      status should be(BadRequest)
    }
  }
}
