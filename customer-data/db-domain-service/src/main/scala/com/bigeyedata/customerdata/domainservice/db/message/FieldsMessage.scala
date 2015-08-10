/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.message

import com.bigeyedata.customerdata.common.model.{DatabaseProperties, Field}
import com.bigeyedata.customerdata.domainservice.db.message.composer.QueryFieldsSqlComposer

case class FieldsResponse(fields:List[Field])

case class FieldsRequest(dataSource: DataSourceRequest, rawSql: SqlStatementRequest)
  extends QueryFieldsSqlComposer {
  def databaseProperties: DatabaseProperties = dataSource.databaseProperties
}
