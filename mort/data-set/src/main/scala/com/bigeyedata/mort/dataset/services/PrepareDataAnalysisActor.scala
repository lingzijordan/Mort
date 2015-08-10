/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.services

import java.time.LocalTime
import java.time.temporal.ChronoUnit

import akka.actor._
import akka.event.Logging
import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.{AnalysisExecutionContext, AnalysisResult, AnalyzeData}
import com.bigeyedata.mort.commons.ActorExceptionHandler
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.messages.Messages.{ExecutionFailed, ExecutionSuccess}
import com.bigeyedata.mort.dataset.DataSetConfiguration

class PrepareDataAnalysisActor extends Actor with ActorExceptionHandler with QueryStatementGenerator with DataSetConfiguration {

  override def mortReceive: Receive = {
    case analyzeData: AnalyzeData => {
      originalSender = sender()
      val tableName = dataSetTableNameFrom(analyzeData)
      val log  = Logging(context.system,this)
      val start = LocalTime.now()
      val sql = generate(analyzeData)
      val end = LocalTime.now()
      val cost =  ChronoUnit.MILLIS.between(start,end)
      log.debug("generate sql cost time is=======>"+cost)
      val executeAnalysisActor = context.selectSiblingActor(s"execute-data-analysis-${persistanceType}")
      executeAnalysisActor ! AnalysisExecutionContext(tableName, sql, analyzeData)
    }
    case (analysisResult: AnalysisResult) => {
      originalSender ! ExecutionSuccess(analysisResult)
    }
    case failed: ExecutionFailed => originalSender ! failed
  }

  var originalSender: ActorRef = null
}
