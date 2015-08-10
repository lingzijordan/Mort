/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.service

import akka.actor.{Props, Actor}
import com.bigeyedata.customerdata.dataengine.db.model.{ReadDataFromDB, PrepareImportData}

class ImportDataActor extends Actor {

  override def receive: Receive = {
    case PrepareImportData(sqlStatements,
    destination,
    dateSetId,
    srcDbProperties,
    targetDbProperties,
    codes) => sqlStatements.foreach { sql =>
      val dbReaderActor = context.actorOf(Props[DbDataReaderActor])
      dbReaderActor ! ReadDataFromDB(dateSetId, destination, sql, codes, srcDbProperties, targetDbProperties)
    }
    case _ => throw new Exception("not match message")
  }
}
