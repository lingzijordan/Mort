/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.datawriter.model

import com.bigeyedata.customerdata.common.model.{DatabaseProperties, DataFrame}

object DataWriterMessage {
  case class GenerateInsertSql(datasetId: String, tableName: String, dataFrame: DataFrame)
  case class SqlGenerated(datasetId: String, sql: String)

  sealed trait WriteData
  case class WriteDataToDB(properties: DatabaseProperties, datasetId: String, tableName: String, dataFrame: DataFrame) extends WriteData
  case class WriteDataToFile(dataFrame: DataFrame) extends WriteData
  case object WriteCompleted
}
