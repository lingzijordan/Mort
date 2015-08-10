/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.services

import akka.actor.ActorRefFactory
import akka.pattern.ask
import akka.util.Timeout
import com.bigeyedata.mort.commons.RequestTimeOutSupport
import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.messages.Messages.{ExecutionFailed, ExecutionSuccess}
import com.bigeyedata.mort.dataset.DataSetConfiguration
import com.bigeyedata.mort.dataset.DataSetMessages.{CreateDataSet, ImportDataSet}
import com.bigeyedata.mort.infrastructure.metadata.services._

import scala.concurrent.Await

trait DataSetCreator extends DataSetService with DataSetConfiguration {
  def actorContext: ActorRefFactory

  def createDataSet(dataSet: CreateDataSet): PrimaryKey = {
    if (hasExistDataSet(dataSet.name)) throw BadRequestException(s"${dataSet.name} has exist!")
    val actor = actorContext.selectSiblingActor(s"createDataSetTable-${persistanceType}")

    implicit val defaultRequestTimeout: Timeout = RequestTimeOutSupport.requestTimeout
    val createDataSetResult = actor ? dataSet
    Await.result(createDataSetResult, defaultRequestTimeout.duration) match {
      case success: ExecutionSuccess =>
        val dataSetId: PrimaryKey = create(dataSet)
        val importDataSetActor = actorContext.selectSiblingActor(s"dataSet-import-${persistanceType}")
        importDataSetActor ! ImportDataSet(dataSetId)
        dataSetId
      case ExecutionFailed(_, message) =>
        throw BadRequestException(message)
    }
  }
}
