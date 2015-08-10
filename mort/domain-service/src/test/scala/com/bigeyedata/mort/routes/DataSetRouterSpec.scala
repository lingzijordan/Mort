package com.bigeyedata.mort.routes

import com.bigeyedata.mort.{RandomGenerator, ApiCommonSpec}
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.message.dataset.{CreateDataSetFieldRequest, CreateDataSetRequest, FetchDataSetResponse, FetchFieldResponse}
import spray.http.StatusCodes._

class DataSetRouterSpec extends ApiCommonSpec
with DataSetRouter
with DataSourceRouter
with MortExceptionHandler
with DatabaseOptionProvider
with RandomGenerator {

  override def actorContext = mortActorSystem

  "Create Data Set" should "success and return the location in the response" in {
    val dataSourceId = 1
    val fields = List(CreateDataSetFieldRequest("c1", "name", "Category", "java.lang.String", 10, 0))
    val createDataSetRequest = CreateDataSetRequest("mort-data-set" + randomString(), "for test", dataSourceId, "select * from car_brand", fields)
    Post("/data-sets", createDataSetRequest) ~> dataSetRoute ~> check {
      val location = header("location") match {
        case Some(header) => header.value
        case _ => "fail"
      }

      status should be(Created)
      location should not be ("fail")
    }
  }

  "Create Data Set" should "failed when given an existed data set name" in {
    val dataSourceId = 1
    val fields = List(CreateDataSetFieldRequest("c1", "cc1", "Category", "java.lang.String", 10, 0))
    val existedDataSetName: String = "测试数据集"
    val createDataSetRequest = CreateDataSetRequest(existedDataSetName, "for test", dataSourceId, "select * from car_brand", fields)
    Post("/data-sets", createDataSetRequest) ~> dataSetRoute ~> check {
      val location = header("location") match {
        case Some(header) => header.value
        case _ => "fail"
      }

      Post("/data-sets", createDataSetRequest) ~> dataSetRoute ~> check {
        status should be(BadRequest)
      }
    }
  }

  it should "return all data sets in the response" in {
    val dataSourceId = 1
    Get(s"/data-sources/${dataSourceId}/data-sets") ~> dataSetRoute ~> check {
      val listDataSetResponses: List[FetchDataSetResponse] = responseAs[List[FetchDataSetResponse]]
      listDataSetResponses should not be empty
    }
  }

  it should "return all related fields in the response" in {
    val dataSetId = 1
    Get(s"/data-sets/${dataSetId}/fields") ~> dataSetRoute ~> check {
      val fetchFieldResponse: FetchFieldResponse = responseAs[FetchFieldResponse]
      fetchFieldResponse.metrics.length should be(3)
      fetchFieldResponse.categories.length should be(1)
      fetchFieldResponse.timeCategories.length should be(3)
    }
  }

  "Get fields by data set id" should "return NotFound status code giving existed data set id" in {
    val dataSetId = 10000
    Get(s"/data-sets/${dataSetId}/fields") ~> dataSetRoute ~> check {
      status should be(NotFound)
    }
  }

}
