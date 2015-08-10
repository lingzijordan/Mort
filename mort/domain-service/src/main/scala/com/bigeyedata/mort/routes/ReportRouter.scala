package com.bigeyedata.mort.routes

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.commons.implicits.StringImplicits._
import com.bigeyedata.mort.dataset.services.FieldFetcher
import com.bigeyedata.mort.message.mapping.FieldMapper._
import com.bigeyedata.mort.message.mapping.ReportMapper._
import com.bigeyedata.mort.message.report.CreateReportRequest
import com.bigeyedata.mort.report.services.{DataSetIdRetriever, ReportCreator, ReportsFetcher, ViewsFetcher}
import spray.http.HttpHeaders.Location
import spray.http.StatusCodes._
import spray.routing.HttpService


trait ReportRouter extends HttpService with ReportCreator with ReportsFetcher with ViewsFetcher with FieldFetcher with DataSetIdRetriever {
  def reportRoute = pathPrefix("reports") {
    pathEnd {
      post {
        entity(as[CreateReportRequest]) { request =>
          if (request.hasErrors) {
            complete(BadRequest, request.errorMessages)
          } else {
            val id: PrimaryKey = createReport(request.toCreateReport())
            respondWithHeaders(List(new Location("/reports" / id))) {
              complete(Created)
            }
          }
        }
      } ~
        get {
          complete {
            fetchReports().response
          }
        }
    } ~
      path(IntNumber) { reportId =>
        get {
          fetchReport(reportId) match {
            case Some(report) =>
              complete {
                (report, fetchWithReportId(reportId)).response
              }
            case None => complete(NotFound, "report is not existed")
          }
        }
      } ~
      path(IntNumber / "fields") { reportId =>
        get {
          complete {
            fetchFields(retrieveDataSetId(reportId)).response
          }
        }
      }
  }
}
