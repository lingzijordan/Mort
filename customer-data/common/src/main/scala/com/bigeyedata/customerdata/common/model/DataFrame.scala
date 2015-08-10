/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.common.model

object Row {
  def apply(cells: String*): Row = Row(cells.toList)
}
case class Row(cells: List[String])

case class Column(name: String, dataClassType: String, precision: Int, scale: Int)

object DataFrame {
  def apply(schema: List[Column], rows: Row*):DataFrame =
    new DataFrame(schema.toList, rows.toList)
}

trait SqlSupport {
  self: DataFrame =>
  
  def fieldsPart:String = schema.map(_.name).mkString(",")
  def valuesPart:String = rows.map(row => s"(${row.cells.map(cell => s"'$cell'").mkString(",")})").mkString(",")

  def rebindSchema(aliasNames: List[String]): DataFrame = {
    val fields = schema.zipWithIndex.map {
      case (field, index) => Column(aliasNames(index), field.dataClassType, field.precision, field.scale)
    }
    DataFrame(fields, rows)
  }

}

case class DataFrame(schema: List[Column], rows: List[Row]) extends SqlSupport

