/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.common

import com.bigeyedata.customerdata.domainservice.db.message.{SqlStatementRequest, DataSourceRequest}

trait Demonstrate {
  val srcDataSource = DataSourceRequest("mysql", "localhost", "3306", "customerdata_dev", "bigeye", "bigeye123")
  val targetDataSource = DataSourceRequest("mysql", "localhost", "3306", "bigeye_dev", "bigeye", "bigeye123")
  val dataSetId = 1
  val sqlStatement = SqlStatementRequest("select * from rental_record")
  val complexSqlStatement = SqlStatementRequest(
    s"""
       |select tx0.name_ as car_name,tx1.START_DATE as startDate,tx1.END_DATE as endDate,tx0.COST_ as cost
       | from cars tx0
       | inner join RENTAL_RECORD tx1
       | on tx0.BRAND_ID = tx1.ID_
     """.trim.stripMargin)
  val tableName = "datasetForTest"
  val codes = List[String]("c1", "c2", "c3", "c4", "c5", "c6", "c7")
}
