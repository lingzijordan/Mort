/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.common.implicits

import java.sql.ResultSet
import java.text.SimpleDateFormat
import com.bigeyedata.customerdata.common.global.Variables._

import com.bigeyedata.customerdata.common.model._

object ResultSetEnhancement {

  implicit class RichResultSet(private[this] val rs: ResultSet) {

    private val handlers: Map[String, (Int, ResultSet) => String] = {
      val converter = (colNo: Int, rs: ResultSet) => convertDateToString(colNo, rs)
      Map(
        "java.sql.Date" -> converter,
        "java.sql.Time" -> converter,
        "java.sql.Timestamp" -> converter
      )
    }

    private def convertDateToString(index: Int, resultSet: ResultSet): String = {
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      format.format(resultSet.getTimestamp(index))
    }

    def totalRowCount: Long = if (rs.next()) rs.getLong(ROW_COUNT_COLUMN_NAME) else 0

    def toDataFrame: DataFrame = {
      val metadata = rs.getMetaData

      def rows: List[Row] = {
        var rows = List[Row]()

        def getColumnValue(columnNo: Int, resultSet: ResultSet): String = {
          handlers.get(metadata.getColumnClassName(columnNo)) match {
            case Some(handler) => handler(columnNo, resultSet)
            case None => resultSet.getString(columnNo)
          }
        }

        while (rs.next()) {
          val row: Row = Row(
            (1 to getOriginalColumnCount)
              .map(x => getColumnValue(x, rs))
              .toList)
          rows = row :: rows
        }
        rows.reverse
      }

      def fields: List[Column] = {
        def getColumn(columnNo: Int, resultSet: ResultSet): Column = {
          metadata.getPrecision(columnNo)
          metadata.getScale(columnNo)

          Column(metadata.getColumnName(columnNo),
            metadata.getColumnClassName(columnNo),
            metadata.getPrecision(columnNo),
            metadata.getScale(columnNo))
        }

        (1 to getOriginalColumnCount).map(getColumn(_, rs)).toList
      }

      def getOriginalColumnCount: Int = {
        (1 to metadata.getColumnCount)
          .map(metadata.getColumnName).count(!_.equals(ROW_COUNT_COLUMN_NAME))
      }

      DataFrame(fields, rows)
    }
  }

}
