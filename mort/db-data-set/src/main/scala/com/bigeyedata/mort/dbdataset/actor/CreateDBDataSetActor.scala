/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.actor

import akka.actor.Actor
import com.bigeyedata.mort.commons.ActorExceptionHandler
import com.bigeyedata.mort.dataset.DataSetMessages.CreateDataSet
import com.bigeyedata.mort.dbdataset.service.CreateDBDataSetService

class CreateDBDataSetActor extends Actor with CreateDBDataSetService with ActorExceptionHandler {

  override def mortReceive = {
    case CreateDataSet(name, description, dataSourceId, sql, tableName, fields) => {
      sender ! createDBDataSet(name, tableName, fields)
    }
  }
}