package com.bigeyedata.mort.routes

import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.commons.implicits.StringImplicits._
import com.bigeyedata.mort.dataset.services.{DataSetCreator, DataSetFetcher, FieldFetcher}
import com.bigeyedata.mort.message.dataset.{CreateDataSetRequest, FetchDataSetResponse}
import com.bigeyedata.mort.message.mapping.FieldMapper._
import spray.http.HttpHeaders.Location
import spray.http.StatusCodes._
import spray.routing.HttpService


trait DataSetRouter extends HttpService with DataSetCreator with DataSetFetcher with FieldFetcher {
  def actorContext = actorRefFactory

  def dataSetRoute = pathPrefix("data-sets") {
    pathEnd {
      post {
        entity(as[CreateDataSetRequest]) { request =>
          if (request.hasErrors) {
            complete(BadRequest, request.errorMessages)
          } else {
            val id: PrimaryKey = createDataSet(request.toCreateDataSet())
            respondWithHeaders(List(new Location("/data-sets" / id))) {
              complete(Created)
            }
          }
        }
      }
    } ~ path(IntNumber / "fields") { dataSetId =>
      get {
        complete {
          fetchFields(dataSetId).response
        }
      }
    } ~
      path(IntNumber) { dataSetId =>
        get {
          fetchDataSet(dataSetId) match {
            case Some(dataSet) =>
              complete {
                FetchDataSetResponse(dataSet.id, dataSet.name, dataSet.description, dataSet.status)
              }
            case None => complete(NotFound, "dataset is not existed")
          }
        }
      }
  } ~ path("data-sources" / IntNumber / "data-sets") { dataSourceId =>
    get {
      complete {
        fetchDataSets(dataSourceId).map { dataSet =>
          FetchDataSetResponse(dataSet.id, dataSet.name, dataSet.description, dataSet.status)
        }
      }
    }
  }
}
