/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.datasource.service

import akka.actor.{Actor, ActorRef}
import com.bigeyedata.mort.commons.ActorExceptionHandler
import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.messages.Messages.ExecutionFailed
import com.bigeyedata.mort.datasource.{DataSourceFetched, FetchMasterDataSource, FetchSpecificDataSource}
import com.bigeyedata.mort.infrastructure.metadata.services.MasterCustomerDataSourceService

class MasterDataSourceFetchActor extends Actor with MasterCustomerDataSourceService with ActorExceptionHandler {
  var originalSender: ActorRef = _

  override def mortReceive: Receive = {
    case FetchMasterDataSource(dataSourceId) => {
      originalSender = sender()
      val dataSourceOpt = fetchMasterDataSourceById(dataSourceId)
      dataSourceOpt match {
        case Some(dataSource) =>
          val dbDataSourceActor = context.selectSiblingActor(s"dataSource-${dataSource.dataSourceType.toLowerCase}")
          dbDataSourceActor ! FetchSpecificDataSource(dataSource)
        case None => originalSender ! ExecutionFailed(ResourceNotExistException(s"$dataSourceId not exists"), s"$dataSourceId not exists")
      }
    }
    case result: DataSourceFetched => originalSender ! result

    case failed: ExecutionFailed => originalSender ! failed
  }
}
