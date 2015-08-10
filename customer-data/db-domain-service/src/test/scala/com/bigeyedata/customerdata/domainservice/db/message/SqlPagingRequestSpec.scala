package com.bigeyedata.customerdata.domainservice.db.message

import com.bigeyedata.customerdata.domainservice.db.common.UnitSpec

/**
 * Created by FuChun on 6/29/15.
 */
class SqlPagingRequestSpec extends UnitSpec {

  val expectedSqlSection =
    s"""
       |select * from (select t1.*,rownum as rowCount from (select * from RENTAL_RECORD) t1) where rowCount between 1 and 500
     """.stripMargin.trim

  "SqlPagingSpec" should "split query to Sections" in {
    val request = ImportRequest("ds_test1", 1,
      DataSourceRequest("oracle", "", "", "", "", ""),
      DataSourceRequest("mysql", "", "", "", "", ""),
      SqlStatementRequest("select * from RENTAL_RECORD"), List[String]("c1,c2"))
    val totalRecords = 5002
    val sqlSections = request.splitSqlSections(totalRecords)
    sqlSections.head should be (expectedSqlSection)
  }
}
