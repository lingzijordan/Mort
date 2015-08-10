/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.datawriter.service

import akka.actor.{ActorRef, Actor, Props}
import com.bigeyedata.customerdata.common.db.SqlExecutor
import com.bigeyedata.customerdata.datawriter.model.DataWriterMessage.{WriteCompleted, SqlGenerated, GenerateInsertSql, WriteDataToDB}

class DbDataWriterActor(tracker: ProgressTracker) extends Actor {
  
  lazy val generateSqlActor = context.actorOf(Props[GenerateSqlActor], "generateSql")
  var executor: SqlExecutor = _
  var originalSender: ActorRef = _

  def receive = {
    case WriteDataToDB(properties, datasetId, tableName, dataFrame) => {
      executor = new SqlExecutor(properties)
      originalSender = sender
      generateSqlActor ! GenerateInsertSql(datasetId, tableName, dataFrame)
    }
    case SqlGenerated(datasetId, sql) => {
      val effectedRowCount = executor.executeCommand(sql)
      tracker.update(datasetId, effectedRowCount)
      originalSender ! WriteCompleted
    }
  }

}
