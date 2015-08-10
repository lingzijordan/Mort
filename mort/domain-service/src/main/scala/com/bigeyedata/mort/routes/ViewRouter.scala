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
import com.bigeyedata.mort.commons.implicits.StringImplicits._
import com.bigeyedata.mort.dataset.FieldMessages._
import com.bigeyedata.mort.dataset.ViewFieldImplicits._
import com.bigeyedata.mort.dataset.services.AnalysisResultFetcher
import com.bigeyedata.mort.infrastructure.metadata.models.View
import com.bigeyedata.mort.message.analysisdata.AnalysisResultMessage.DataAnalysisResponse
import com.bigeyedata.mort.message.mapping.ViewMapper._
import com.bigeyedata.mort.message.view.{CreateViewRequest, FetchViewWithDataResponse}
import com.bigeyedata.mort.report.services.{DataSetIdRetriever, ViewCreator, ViewFieldFetcher, ViewsFetcher}
import spray.http.HttpHeaders.Location
import spray.http.StatusCodes._
import spray.routing.HttpService

trait ViewRouter
  extends HttpService
  with ViewCreator
  with ViewsFetcher
  with AnalysisResultFetcher
  with ViewFieldFetcher with DataSetIdRetriever {

  def viewRoute = pathPrefix("views") {
    pathEnd {
      post {
        entity(as[CreateViewRequest]) { request =>
          if (request.hasErrors) {
            complete(BadRequest, request.errorMessages)
          } else {
            createView(request.toCreateView)
            respondWithHeaders(List(new Location("/reports" / request.reportId))) {
              complete(Created)
            }
          }
        }
      } ~ get {
        complete(fetchViewWithReports().response)
      }
    } ~
      path(IntNumber) { viewId =>
        get {
          fetchView(viewId) match {
            case Some(view: View) =>
              val (metrics, categories): (Metrics, Categories) = fetchViewFields(viewId)
              val analysisDataResult: AnalysisResult = fetchAnalysisResult(AnalyzeData(retrieveDataSetId(view.reportId), metrics, categories))
              complete(FetchViewWithDataResponse(view, DataAnalysisResponse(analysisDataResult.fields, analysisDataResult.rows)))
            case None => complete(NotFound, "view is not existed")
          }
        }
      }
  }
}
