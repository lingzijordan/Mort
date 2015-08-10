/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.service

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import com.bigeyedata.customerdata.common.db.SqlExecutor
import com.bigeyedata.customerdata.common.implicits.ResultSetEnhancement
import com.bigeyedata.customerdata.dataengine.db.model.ReadDataFromDB
import com.bigeyedata.customerdata.datawriter.model.DataWriterMessage.{WriteCompleted, WriteDataToDB}
import com.bigeyedata.customerdata.datawriter.service.{DbDataWriterActor, ProgressTracker}


class DbDataReaderActor extends Actor {

  var dbWriteActor: ActorRef = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    dbWriteActor = context.actorOf(Props(classOf[DbDataWriterActor], new ProgressTracker()), "dbWriteActor")
  }

  override def receive: Receive = {
    case ReadDataFromDB(dsId, destination, sql, codes, srcDbProperties, targetDbProperties) => {
      val sqlExecutor = SqlExecutor(srcDbProperties)
      val dataFrame = sqlExecutor.executeQuery(sql) { rs =>
        import ResultSetEnhancement._
        rs.toDataFrame.rebindSchema(codes)
      }

      dbWriteActor ! WriteDataToDB(targetDbProperties, dsId.toString, destination, dataFrame)
    }
    case WriteCompleted => {
      self ! PoisonPill
    }
  }
}
