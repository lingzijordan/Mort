/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.actor

import akka.actor.Actor
import com.bigeyedata.mort.agent.ImportDataSetMessage.ImportDataServiceSetRequest
import com.bigeyedata.mort.cdataagent.service.ImportDataToDataSetService
import com.bigeyedata.mort.commons.ImportDataSetStatus
import com.bigeyedata.mort.dataset.DataSetMessages.ImportDataSet
import ImportDataSetStatus._
import com.bigeyedata.mort.dbdataset.implicits.DbDataSetImplicits._
import com.bigeyedata.mort.infrastructure.metadata.services.DataSetService

class ImportDbDataSetActor extends Actor with DataSetService with ImportDataToDataSetService {

  override def receive: Receive = {
    case ImportDataSet(dataSetId) => {
      val dataSetOption = fetchDataSet(dataSetId)
      dataSetOption match {
        case Some(dataSet) => {
          val importDbDataSetRequest = ImportDataServiceSetRequest(dataSet.tableName,
            dataSetId,
            dataSet.fetchDbSrcDataSource,
            dataSet.fetchDbTargetDataSource,
            dataSet.query,
            dataSet.fetchColumns)
          importCustomerData(importDbDataSetRequest) {
            changeStatus(importDbDataSetRequest.dateSetId, SUCCESS.id)
          } {
            changeStatus(importDbDataSetRequest.dateSetId, FAILURE.id)
          }

        }
        case None =>
      }
    }
  }

}
