package com.bigeyedata.mort.routes

import com.bigeyedata.mort.ApiCommonSpec
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.message.datasource.{CreateDataSourceRequest, FetchDataSourceResponse, MasterDataSourceResponse}
import spray.http.StatusCodes._

class DataSourceRouterSpec extends ApiCommonSpec with DataSourceRouter with DatabaseOptionProvider {
  "Create data source" should "success when post all fields" in {
    val dataSourceRequest = CreateDataSourceRequest(-1, "mort" + System.currentTimeMillis, "rdb", "This is a test data source", databaseOptions)
    Post("/data-sources", dataSourceRequest) ~> dataSourceRoute ~> check {
      val location = header("location") match {
        case Some(header) => header.value
        case _ => "fail"
      }

      status === Created
      location should not be "fail"
    }
  }

  it should "not create data source with existed data source name" in {
    val dataSourceName: String = "mort" + System.currentTimeMillis
    val dataSourceRequest = CreateDataSourceRequest(-1, dataSourceName, "rdb", "This is a test data source", databaseOptions)

    Post("/data-sources", dataSourceRequest) ~> dataSourceRoute ~> check {

      val location = header("location") match {
        case Some(header) => header.value
        case _ => "fail"
      }

      status should be(Created)
      location should not be "fail"
      Post("/data-sources", dataSourceRequest) ~> dataSourceRoute ~> check {
        status should be(BadRequest)
        response.entity.data === s"The data source name: $dataSourceName has exist"
      }
    }
  }


  it should "not create data source with not right database options" in {
    val databaseOptions = Map(
      "port" -> "3306",
      "database" -> "bigeye_test",
      "username" -> "bigeye",
      "password" -> "bigeye123",
      "databaseType" -> "mysql"
    )
    val dataSourceRequest = CreateDataSourceRequest(-1, "mort" + System.currentTimeMillis, "rdb", "This is a test data source", databaseOptions)
    Post("/data-sources", dataSourceRequest) ~> dataSourceRoute ~> check {
      status should be(BadRequest)
    }
  }

  it should "fetch master datasource" in {
    Get("/data-sources") ~> dataSourceRoute ~> check {
      val dataSourceResponse = responseAs[List[MasterDataSourceResponse]]
      dataSourceResponse.head.dataSourceId should be(1)
    }
  }

  it should "fetch customer datasource " in {
    Get("/data-sources/1") ~> dataSourceRoute -> check {
      val dataSourceResponse = responseAs[FetchDataSourceResponse]
      dataSourceResponse.name should be ("测试数据源A")
      dataSourceResponse.dataSourceType should be ("rdb")
      dataSourceResponse.options("host") should be ("127.0.0.1")
    }
    Thread.sleep(5000)
  }

}
