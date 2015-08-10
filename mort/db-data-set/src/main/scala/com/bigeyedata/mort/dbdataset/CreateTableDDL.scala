/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset

import com.bigeyedata.mort.dataset.DataSetMessages.Field

trait DatabaseTable {
  def comment: String

  def tableName: String

  def fields: List[Field]
}

trait CreateTableDDL {
  self: DatabaseTable =>

  def withComment(originalDDL: String): String

  def generateFieldSql(codeName: String, dataClassName: String, length: Int, scale: Int, comment: String): String

  def generateDDL: String = {

    val fieldsSql = fields.map(field => {
      generateFieldSql(field.codeName, field.dataClassName, field.length, field.scale, field.aliasName)
    }).mkString(",")

    withComment(s"create table IF NOT EXISTS $tableName ($fieldsSql)")
  }
}

case class MysqlCreateTableDDL(override val tableName: String, override val comment: String, override val fields: List[Field]) extends DatabaseTable with CreateTableDDL {

  override def withComment(originalDDL: String): String = s"$originalDDL COMMENT '$comment';"

  override def generateFieldSql(codeName: String, dataClassName: String, length: Int, scale: Int, comment: String): String =
    MysqlField(codeName, dataClassName, length, scale, comment).fieldSqlString
}
