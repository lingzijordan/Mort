/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.service

import com.bigeyedata.mort.dataset.DataSetMessages.Field
import org.scalatest.{FlatSpec, Matchers}
import tags.UT

@UT
class CreateDBDataSetServiceSpec extends FlatSpec with Matchers with CreateDBDataSetService {

  val dataSetName = "dataSetName"
  val tableName = "testTableName"

  val fields = List(
    Field("string-key",
      "alias-string",
      "",
      "",
      "java.lang.String",
      10,
      0),
    Field("text-key",
      "alias-string",
      "",
      "",
      "java.lang.String",
      10000,
      0),
    Field("integer-key",
      "alias-integer",
      "",
      "",
      "java.lang.Integer",
      11,
      0))

  "CreateDBDataSetService" should "generate mysql create DataSet table DDL" in {
    implicit val databaseType = "mysql"

    val mysqlDDL: String = createTableSql(dataSetName, tableName, fields)

    mysqlDDL startsWith s"create table IF NOT EXISTS $tableName"
    mysqlDDL contains("string-key VARCHAR(10) COMMENT 'alias-string'")
    mysqlDDL contains("integer-key INTEGER(11) COMMENT 'alias-integer'")
    mysqlDDL contains("text-key TEXT COMMENT 'alias-text'")
    mysqlDDL endsWith s"COMMENT '$dataSetName'"
  }

  it should "get not supported database type exception" in {
    implicit val databaseType = "unknown-type"

    intercept[IllegalArgumentException] {
      createTableSql(dataSetName, tableName, fields)
    }

  }

  it should "get not supported field type exception" in {
    implicit val databaseType = "unknown-type"
    intercept[IllegalArgumentException] {
      createTableSql(dataSetName, tableName, Field("integer-key", "alias-integer", "", "", "java.lang.Unknown", 11, 0) :: fields)
    }

  }
}
