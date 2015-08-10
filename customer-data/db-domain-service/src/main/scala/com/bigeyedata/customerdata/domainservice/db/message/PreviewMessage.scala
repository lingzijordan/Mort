/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.message

import com.bigeyedata.customerdata.common.model.DatabaseProperties
import com.bigeyedata.customerdata.domainservice.db.message.composer.PreviewSqlComposer

case class PreviewRequest(dataSource: DataSourceRequest, rawSql: SqlStatementRequest, rowCount: Int = 10)
  extends PreviewSqlComposer {
  def databaseProperties: DatabaseProperties = dataSource.databaseProperties
  def countSql: String = rawSql.countSql
}

case class PreviewResponse(fields: List[String], rows: List[List[String]], totalRowCount: Long)
