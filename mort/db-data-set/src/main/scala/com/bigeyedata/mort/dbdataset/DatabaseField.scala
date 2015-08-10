/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset

trait DatabaseField {
  def codeName: String

  def dataClassName: String

  def length: Int

  def scale: Int

  def comment: String
}

trait FieldSqlGenerator {
  self: DatabaseField =>

  def forString(): String

  def forInt(): String

  def forDouble(): String

  def forLong(): String

  def forFloat(): String

  def forShort(): String

  def forByte(): String

  def forDate(): String

  def forTime(): String

  def forTimestamp(): String

  def forBigDecimal(): String

  def forBoolean(): String

  def forBlob(): String

  def withComment(fieldDDL: String): String

  def fieldSqlString: String = {
    val fieldDDL = dataClassName match {
      case "java.lang.String" => forString()

      case "java.math.BigDecimal" => forBigDecimal()
      case "java.lang.Integer" => forInt()
      case "java.lang.Double" => forDouble()
      case "java.lang.Long" => forLong()
      case "java.lang.Float" => forFloat()
      case "java.lang.Short" => forShort()
      case "java.lang.Byte" => forByte()
      case "java.lang.Boolean" => forBoolean()

      case "java.sql.Date" => forDate()
      case "java.sql.Time" => forTime()
      case "java.sql.Timestamp" => forTimestamp()

      case "java.sql.Blob" => forBlob()

      case unknownType => throw new IllegalArgumentException(s"$unknownType is not supported")
    }

    withComment(fieldDDL)
  }
}

case class MysqlField(
                       override val codeName: String,
                       override val dataClassName: String,
                       override val length: Int,
                       override val scale: Int,
                       override val comment: String) extends DatabaseField with FieldSqlGenerator {
  override def forString(): String = if (length > 4000) s"$codeName TEXT" else (s"$codeName VARCHAR($length)")

  override def forInt(): String = s"$codeName INTEGER($length)"

  override def forDouble(): String = s"$codeName DOUBLE($length, $scale)"

  override def forLong(): String = s"$codeName INTEGER UNSIGNED"

  override def forFloat(): String = s"$codeName FLOAT($length, $scale)"

  override def forTime(): String = s"$codeName TIME"

  override def forByte(): String = s"$codeName TINYINT"

  override def forTimestamp(): String = s"$codeName TIMESTAMP"

  override def forDate(): String = s"$codeName DATE"

  override def forShort(): String = s"$codeName SMALLINT($length)"

  override def forBigDecimal(): String = s"$codeName DECIMAL($length, $scale)"

  override def forBoolean(): String = s"$codeName BIT"

  override def forBlob(): String = s"$codeName BLOB"

  override def withComment(fieldDDL: String): String = s"$fieldDDL COMMENT '$comment'"
}