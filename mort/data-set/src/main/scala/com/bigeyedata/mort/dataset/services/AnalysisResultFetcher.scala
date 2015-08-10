/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.services

import akka.actor.ActorRefFactory
import akka.pattern._
import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.{AnalysisResult, AnalyzeData}
import com.bigeyedata.mort.commons.RequestTimeOutSupport.requestTimeout
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.messages.Messages.{ExecutionFailed, ExecutionSuccess}
import com.bigeyedata.mort.dataset.DataSetConfiguration
import scala.concurrent.Await

trait AnalysisResultFetcher extends DataSetConfiguration {

  def actorContext: ActorRefFactory

  def fetchAnalysisResult(analyzeData: AnalyzeData): AnalysisResult = {
    val prepareAnalysisActor = actorContext.selectSiblingActor(s"prepare-data-analysis-${persistanceType}")
    val viewData = prepareAnalysisActor ? analyzeData

    Await.result(viewData, requestTimeout.duration) match {
      case ExecutionSuccess(analysisDataResult: AnalysisResult) => analysisDataResult
      case ExecutionFailed(_, message) => throw BadRequestException(message)
    }
  }

}
