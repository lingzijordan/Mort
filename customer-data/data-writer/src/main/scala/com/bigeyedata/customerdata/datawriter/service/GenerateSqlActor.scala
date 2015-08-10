/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.datawriter.service

import akka.actor.Actor
import com.bigeyedata.customerdata.datawriter.model.DataWriterMessage.{SqlGenerated, GenerateInsertSql}

class GenerateSqlActor extends Actor {

  def receive = {
    case GenerateInsertSql(datasetId, tableName, dataFrame) =>
      val sql = s"insert into ${tableName} (${dataFrame.fieldsPart}) values ${dataFrame.valuesPart}"
      sender ! SqlGenerated(datasetId, sql)
  }
}
