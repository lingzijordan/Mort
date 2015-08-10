/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.service

import com.bigeyedata.mort.commons.messages.Messages.{ExecutionFailed, ExecutionResult, ExecutionSuccess}
import com.bigeyedata.mort.dataset.DataSetMessages.Field
import com.bigeyedata.mort.dbdataset.{DatabasePropertiesSupport, MysqlCreateTableDDL, SqlExecutor}

trait CreateDBDataSetService extends DatabasePropertiesSupport {

  def createTableSql(dataSetName: String, tableName: String, fields: List[Field])(implicit databaseType: String): String = {

    databaseType.toLowerCase match {
      case "mysql" => MysqlCreateTableDDL(tableName, dataSetName, fields).generateDDL

      case _ => throw new IllegalArgumentException(s"$databaseType is not supported")
    }

  }

  def createDBDataSet(dataSetName: String, tableName: String, fields: List[Field]): ExecutionResult = {
    try {
      SqlExecutor.executeCommand(s"${createTableSql(dataSetName, tableName, fields)}")
      ExecutionSuccess()
    } catch {
      case e: Exception => ExecutionFailed(e, e.getMessage)
    }
  }
}
