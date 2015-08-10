/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.router

import com.bigeyedata.customerdata.domainservice.db.common.{DatasetTableFixture, Demonstrate, RouteApiSpec}
import com.bigeyedata.customerdata.domainservice.db.message.ImportRequest

class ImportServiceRouterSpec extends RouteApiSpec with ImportServiceRouter with DatasetTableFixture with Demonstrate{

  "It" should "will connect importing router successfully" in {
    val request = ImportRequest(tableName, dataSetId, srcDataSource, targetDataSource, sqlStatement, codes)
    Post("/import", request) ~> importRoute ~> check {
      status.intValue should be(202)
      Thread.sleep(10000)
    }
  }


}
