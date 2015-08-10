/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.{AnalysisResult, AnalyzeData}
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.dataset.services.AnalysisResultFetcher
import com.bigeyedata.mort.message.analysisdata.AnalysisResultMessage.{DataAnalysisRequest, DataAnalysisResponse}
import com.bigeyedata.mort.report.services.DataSetIdRetriever
import spray.routing.HttpService

trait AnalysisResultRouter extends HttpService with AnalysisResultFetcher with DataSetIdRetriever {

  def analysisResultRoute = pathPrefix("analysis-result") {
    pathEnd {
      post {
        entity(as[DataAnalysisRequest]) { request =>
          val analysisDataResult: AnalysisResult = fetchAnalysisResult(AnalyzeData(retrieveDataSetId(request.reportId), request.metrics, request.categories))
          complete(DataAnalysisResponse(analysisDataResult.fields, analysisDataResult.rows))
        }
      }
    }
  }
}
