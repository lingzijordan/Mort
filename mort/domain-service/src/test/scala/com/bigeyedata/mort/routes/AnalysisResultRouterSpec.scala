/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import akka.actor.ActorRefFactory
import com.bigeyedata.mort.ApiCommonSpec
import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.AnalyzeData
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.dataset.FieldMessages.{MetricUnit, CategoryUnit}

class AnalysisResultRouterSpec extends ApiCommonSpec with AnalysisResultRouter with MortExceptionHandler {
  override def actorContext: ActorRefFactory = mortActorSystem

  ignore should "receive post data and response analysis result" in{
    Post("/analysis-data",createDataSetMetaData) ~> analysisResultRoute ~> check{

    }

    def createDataSetMetaData: AnalyzeData = {

      val metrics    = List[MetricUnit](MetricUnit(4, "sum"))
      val categories = List[CategoryUnit](CategoryUnit(1), CategoryUnit(2))
      val dataSetId  = 1

      AnalyzeData(dataSetId, metrics, categories)
    }

  }


}
