package com.bigeyedata.mort.dbdataset.service.actor

/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

import akka.actor.{ActorSystem, Props}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import com.bigeyedata.mort.dataset.DataSetMessages.ImportDataSet
import com.bigeyedata.mort.dbdataset.actor.ImportDbDataSetActor
import com.bigeyedata.mort.infrastructure.metadata.LifeCycle
import com.bigeyedata.mort.infrastructure.metadata.services.DataSetService
import org.scalatest.{FlatSpecLike, Matchers}

import scala.concurrent.duration._

class ImportDbDataSetActorSpec extends TestKit(ActorSystem("ImportDbDataStActor"))
with FlatSpecLike with Matchers with DefaultTimeout with ImplicitSender with DataSetService {

  ignore should "send dataSetId" in {
    LifeCycle.start()
    val importDbDataSetActor = system.actorOf(Props[ImportDbDataSetActor], "ImportDbDataSetActor")
    within(5000 millis) {
      importDbDataSetActor ! ImportDataSet(dataSetId)
    }
    Thread.sleep(5000)
  }

  "ImportDbDataSetActorSpec" should "change status" in {
    LifeCycle.start()
    changeStatus(1, 0)
  }

  val dataSetId = 1
}