/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.mort.agent

import com.bigeyedata.mort.dataset.DataSetMessages.{SqlStatement, DataSource}

object ImportDataSetMessage {

  sealed class CDataServiceMessage


  type TargetDataSource = DataSource
  type SrcDtaSource = DataSource
  type Sql = SqlStatement
  type Columns = List[String]


  case class ImportDataServiceSetRequest(destination: String,
                                  dateSetId: Int,
                                  srcDataSource: SrcDtaSource,
                                  targetDataSource: TargetDataSource,
                                  rawSql: Sql,
                                  fields: Columns
                                   ) extends CDataServiceMessage

}
