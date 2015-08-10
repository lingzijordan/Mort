package com.bigeyedata.mort.dbdataset.service

import com.bigeyedata.mort.commons.messages.Messages
import Messages.{ExecutionFailed, ExecutionResult}
import com.bigeyedata.mort.dataset.DataSetMessages.Field
import com.bigeyedata.mort.dbdataset.SqlExecutor
import org.scalatest.{FlatSpec, Matchers}
import tags.DB

@DB
class CreateDBDataSetSpec extends FlatSpec with Matchers with CreateDBDataSetService {

  val dataSetName = "dataSetName"
  val tableName = "createTestTableName"

  val fields = List(
    fieldGenerator("java.lang.String", 10, 0),

    fieldGenerator("java.math.BigDecimal", 12, 3),

    fieldGenerator("java.lang.Integer", 11, 0),
    fieldGenerator("java.lang.Double", 10, 2),
    fieldGenerator("java.lang.Long", 16, 0),
    fieldGenerator("java.lang.Float", 12, 3),
    fieldGenerator("java.lang.Short", 5, 0),
    fieldGenerator("java.lang.Byte"),
    fieldGenerator("java.lang.Boolean"),

    fieldGenerator("java.sql.Date"),
    fieldGenerator("java.sql.Time"),
    fieldGenerator("java.sql.Timestamp"),
    fieldGenerator("java.sql.Blob")
  )

  val testFields = List(Field("key1",
    "key1_aliasName",
    "",
    "",
    "java.lang.String",
    10,
    0)
  )

  "CreateDBDataSetService" should "create mysql create DataSet table" in {
    implicit val databaseType = "mysql"

    SqlExecutor.executeCommand(s"drop table if exists $tableName")

    val result: ExecutionResult = createDBDataSet(dataSetName, tableName, fields)

    result match {
      case ExecutionFailed(e, errorMessage) => fail(errorMessage)
      case _ =>
    }
  }

  def fieldGenerator(dataClassName: String): Field = fieldGenerator(dataClassName, 0, 0)
  
  def fieldGenerator(dataClassName: String, length: Int, scale: Int): Field = {
    val codeName = dataClassName.split("""\.""").last
    Field(s"key$codeName".toLowerCase, s"alias$codeName".toLowerCase, "", "", dataClassName, length, scale)
  }

}
