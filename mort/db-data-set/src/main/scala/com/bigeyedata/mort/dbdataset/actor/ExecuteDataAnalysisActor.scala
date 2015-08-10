/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.actor

import akka.actor.Actor
import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.AnalysisExecutionContext
import com.bigeyedata.mort.commons.ActorExceptionHandler
import com.bigeyedata.mort.dbdataset.implicits.DataFrameImplicits._
import com.bigeyedata.mort.dbdataset.sql.DatabaseSparkSql

class ExecuteDataAnalysisActor extends Actor with ActorExceptionHandler with DatabaseSparkSql {

  override def mortReceive: Receive = {
    case AnalysisExecutionContext(tableName, sql, analysisData) => {
      val df = executeAnalysis(tableName, sql)
      sender ! df.toAnalysisResult(analysisData)
    }
    case _ => println("ExecuteDataAnalysisActor not receive data")
  }

}


