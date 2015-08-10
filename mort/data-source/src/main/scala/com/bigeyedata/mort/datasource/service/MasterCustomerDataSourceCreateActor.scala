package com.bigeyedata.mort.datasource.service

import akka.actor.{Actor, ActorRef}
import com.bigeyedata.mort.commons.ActorExceptionHandler
import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.messages.Messages.ExecutionFailed
import com.bigeyedata.mort.datasource.CreateDataSourceMessages._
import com.bigeyedata.mort.infrastructure.metadata.services.MasterCustomerDataSourceService
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction

class MasterCustomerDataSourceCreateActor extends Actor with MasterCustomerDataSourceService with ActorExceptionHandler {
  var originalSender: ActorRef = null

  override def mortReceive: Receive = {
    case CreateDataSource(dataSourceName, dataSourceType, description, options) =>
      originalSender = sender
      if (hasExistDataSource(dataSourceName)) {
        sender ! ExecutionFailed(ResourceNotExistException(s"The data source name: $dataSourceName has exist"), s"The data source name: $dataSourceName has exist")
      } else {
        implicit val transaction: Transaction = Transaction.begin()
        val id = createMasterDataSource(dataSourceName, dataSourceType, description)
        val specificDataSourceActor = context.selectSiblingActor(s"dataSource-${dataSourceType.toLowerCase}")
        specificDataSourceActor ! CreateSpecificDataSource(id, options, transaction)
      }
    case CreatedSpecificDataSourceSuccess(id, transaction: Transaction) =>
      transaction.commit()
      originalSender ! CreateSuccess(id)
    case ValidateFailed(invalidMessage, transaction) =>
      transaction.rollback()
      originalSender ! ExecutionFailed(invalidMessage, invalidMessage)
  }
}