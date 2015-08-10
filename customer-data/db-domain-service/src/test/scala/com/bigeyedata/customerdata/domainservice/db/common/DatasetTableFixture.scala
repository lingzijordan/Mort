package com.bigeyedata.customerdata.domainservice.db.common

import com.bigeyedata.customerdata.common.db.SqlExecutor
import org.scalatest.{BeforeAndAfter, Suite}


trait DatasetTableFixture extends Suite with BeforeAndAfter with Demonstrate {

  val sqlExecutor = new SqlExecutor(targetDataSource.databaseProperties)

  before {
    deleteTable
    createTable
  }

  def createTable: Unit = {
    val createTableSql =
      s"""
         create table if not exists datasetForTest (
         id int primary key auto_increment,
         c1 varchar(50),c2 varchar(50),c3 varchar(50),c4 varchar(50),c5 varchar(50),c6 varchar(50),c7 varchar(50)
         )
      """.stripMargin.trim
    sqlExecutor.executeCommand(createTableSql)
  }

  def deleteTable: Unit = {
    val dropTableSql = s"DROP TABLE IF EXISTS ${tableName}"
    sqlExecutor.executeCommand(dropTableSql)
  }
}
