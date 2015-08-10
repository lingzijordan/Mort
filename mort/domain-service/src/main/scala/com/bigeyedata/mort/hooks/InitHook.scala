package com.bigeyedata.mort.hooks

import akka.actor._
import akka.pattern.gracefulStop
import com.bigeyedata.mort.dataset.services.PrepareDataAnalysisActor
import com.bigeyedata.mort.datasource.service.DatabaseCustomerDataSourceActor
import com.bigeyedata.mort.dbdataset.actor.{ExecuteDataAnalysisActor, ImportDbDataSetActor, CreateDBDataSetActor}
import com.bigeyedata.mort.infrastructure.metadata.LifeCycle

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, FiniteDuration, _}

trait InitHook {
  val mappingService: Map[String, Props] = Map (
    "dataSource-rdb" -> Props[DatabaseCustomerDataSourceActor],
    "createDataSetTable-db" -> Props[CreateDBDataSetActor],
    "dataSet-import-db" -> Props[ImportDbDataSetActor],
    "prepare-data-analysis-db" -> Props[PrepareDataAnalysisActor],
    "execute-data-analysis-db" -> Props[ExecuteDataAnalysisActor]
  )

  var createdActors: List[ActorRef] = Nil

  def init(system: ActorSystem): Unit = {
    mappingService.foreach {
      case (name, props) =>
        createdActors = system.actorOf(props, name) :: createdActors
    }
    LifeCycle.start()
  }


  def stop(system: ActorSystem): Unit = {
    val shutdownTimeout: Duration = 1 second

    val actorTimeout: FiniteDuration = 1 second

    createdActors.foreach { actor =>
      Await.result(gracefulStop(actor, actorTimeout), shutdownTimeout)
    }
  }
}
