/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.service

import com.bigeyedata.customerdata.common.db.SqlExecutor
import com.bigeyedata.customerdata.common.implicits.ResultSetEnhancement
import com.bigeyedata.customerdata.common.model.{FieldType, DatabaseProperties, Field}

trait FetchFieldsService {

  import ResultSetEnhancement._

  def fetchFields(properties: DatabaseProperties, query: String): List[Field] = {
    SqlExecutor(properties).executeQuery(query) { rs =>
      rs.toDataFrame.schema.map { column =>
        val fieldType = column.dataClassType match {
          case "java.math.BigDecimal"
               | "java.lang.Byte"
               | "java.lang.Short"
               | "java.lang.Integer"
               | "java.lang.Long"
               | "java.lang.Float"
               | "java.lang.Double"
            => FieldType.Metric
          case "java.sql.Date"
               | "java.sql.Time"
               | "java.sql.Timestamp" => FieldType.TimeCategory
          case _ => FieldType.Category
        }

        Field(column.name, column.name, column.dataClassType, fieldType, column.precision, column.scale)
      }
    }
  }
}
