/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.domainservice.db.common.{Demonstrate, RouteApiSpec}
import com.bigeyedata.customerdata.domainservice.db.message.{PreviewRequest, PreviewResponse, SqlStatementRequest}

class PreviewServiceRouterSpec extends RouteApiSpec with PreviewServiceRouter with Demonstrate {

  "PreviewService" should "return preview result" in {
    val request = PreviewRequest(srcDataSource, sqlStatement)
    Post("/preview", request) ~> previewRoute ~> check {
      status.intValue should be(200)
      val result = responseAs[PreviewResponse]
      result.fields.size should be(7)
      result.fields.head should be("id_")
      result.fields.reverse.head should be("status")
      result.rows.head.size should be(7)
      result.rows.head.head should be("1")  //id
      result.rows.head.reverse.head should be("1")  //status
      result.totalRowCount should not be 0
    }
  }

  ignore should "return preview with complex Sql" in {
    val request = PreviewRequest(srcDataSource, complexSqlStatement)
    Post("/preview", request) ~> previewRoute ~> check {
      status.intValue should be(200)
      val result = responseAs[PreviewResponse]
      result.rows.length should be(10)
      result.totalRowCount should not be (0)
    }
  }

  ignore should "return 500 code given wrong sql query" in {
    val request = PreviewRequest(srcDataSource, SqlStatementRequest("select * from xx"))
    Post("/preview", request) ~> previewRoute ~> check {
      status.intValue should be(500)
    }
  }
}
