/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.common.model.{Field, FieldType}
import com.bigeyedata.customerdata.domainservice.db.common.{Demonstrate, RouteApiSpec}
import com.bigeyedata.customerdata.domainservice.db.message.{FieldsRequest, FieldsResponse}

class FetchFieldsServiceRouterSpec extends RouteApiSpec with FetchFieldsServiceRouter with Demonstrate{

  "FetchFieldsServiceRouter" should "fetch fields information" in {
    val request = FieldsRequest(srcDataSource, sqlStatement)
    Post("/fields", request) ~> fieldsRoute ~> check {
      status.intValue should be(200)
      val result = responseAs[FieldsResponse]
      result.fields.size should be(7)
      assertHeadItem(result)
      assertFourthItem(result)
    }
  }
  def assertHeadItem(result: FieldsResponse): Unit = {
    val headItem: Field = result.fields.head
    headItem.name.toLowerCase should be("id_")
    headItem.dataClassName should be("java.lang.Integer")
    headItem.fieldType should be(FieldType.Metric)
    headItem.length should be(11)
    headItem.scale should be(0)
  }

  def assertFourthItem(result: FieldsResponse): Unit = {
    val headItem: Field = result.fields.drop(3).head
    headItem.name.toLowerCase should be("start_date")
    headItem.dataClassName should be("java.sql.Timestamp")
    headItem.fieldType should be(FieldType.TimeCategory)
    headItem.length should be(19)
    headItem.scale should be(0)
  }
}
