package com.bigeyedata.mort.routes

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import com.bigeyedata.mort.ActorSystemProvider
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.commons.implicits.StringImplicits._
import com.bigeyedata.mort.commons.messages.Messages.ExecutionFailed
import com.bigeyedata.mort.datasource.CreateDataSourceMessages.CreateSuccess
import com.bigeyedata.mort.datasource.service.{MasterCustomerDataSourceCreateActor, MasterDataSourceFetchActor}
import com.bigeyedata.mort.datasource.{DataSourceFetched, FetchMasterDataSource}
import com.bigeyedata.mort.infrastructure.metadata.models.CustomerDataSource
import com.bigeyedata.mort.infrastructure.metadata.services.MasterCustomerDataSourceService
import com.bigeyedata.mort.message.datasource.{CreateDataSourceRequest, FetchDataSourceResponse, MasterDataSourceResponse}
import spray.http.HttpHeaders.Location
import spray.http.StatusCodes._
import spray.routing.HttpService

import scala.concurrent.{Await, Future}


trait DataSourceRouter extends HttpService with ActorSystemProvider with MasterCustomerDataSourceService {
  lazy val dataSourceCreateActor: ActorRef = mortActorSystem.actorOf(Props[MasterCustomerDataSourceCreateActor])
  lazy val dataSourceFetchActor: ActorRef = mortActorSystem.actorOf(Props[MasterDataSourceFetchActor])
  def dataSourceRoute = pathPrefix("data-sources") {
    pathEnd {
      post {
        entity(as[CreateDataSourceRequest]) { request: CreateDataSourceRequest =>
          val createDataSourceResult: Future[Any] = dataSourceCreateActor ? request.toCreateDataSource

          val result: Any = Await.result(createDataSourceResult, requestTimeout.duration)
          result match {
            case CreateSuccess(id) =>
              respondWithHeaders(List(new Location("/data-sources" / id))) {
                complete(Created)
              }
            case ExecutionFailed(_, message) =>
              complete(BadRequest, message)
          }
        }
      } ~ get {
        complete {
          fetchMasterDataSources.map(ds =>
            MasterDataSourceResponse(ds.id, ds.name, ds.dataSourceType)
          )
        }
      }
    } ~ path(IntNumber) { dataSourceId =>
      get {
        val result = dataSourceFetchActor ? FetchMasterDataSource(dataSourceId)
        Await.result(result, requestTimeout.duration) match {
          case ds: DataSourceFetched => {
            complete{
              val dataSource: CustomerDataSource = ds.dataSource
              FetchDataSourceResponse(
                dataSource.id,
                dataSource.name,
                dataSource.description.getOrElse(""),
                dataSource.dataSourceType,
                ds.options
              )
            }
          }
          case ExecutionFailed(_, message) => complete(BadRequest, message)
        }
      }
    }
  }


}
