/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.service.actor

import akka.actor.{ActorSystem, Props}
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.AnalyzeData
import com.bigeyedata.mort.dataset.FieldMessages.{CategoryUnit, MetricUnit}
import com.bigeyedata.mort.dataset.services.PrepareDataAnalysisActor
import com.bigeyedata.mort.infrastructure.metadata.LifeCycle
import org.scalatest.{FlatSpecLike, Matchers}

class PrepareDataAnalysisActorSpec extends TestKit(ActorSystem("DbSqlComposerActor"))
with FlatSpecLike
with Matchers with
DefaultTimeout
with ImplicitSender {


  ignore should "receive DataSetMetaData and execute".trim in {
    LifeCycle.start()
    val dbSqlComposerActorSpec = system.actorOf(Props[PrepareDataAnalysisActor], "dbSqlComposerActorSpec")
    dbSqlComposerActorSpec ! createDataSetMetaData
    Thread.sleep(4000)

    def createDataSetMetaData: AnalyzeData = {
      val metrics = List[MetricUnit](MetricUnit(4, "sum"))
      val categories = List[CategoryUnit](CategoryUnit(1), CategoryUnit(2))
      val dataSetId = 1
      AnalyzeData(dataSetId, metrics, categories)
    }
  }


  /**
   * {
   * "dataSetId":1
   * "metrics":[{"fieldId":4,"aggregate":"sum"}]
   * "categories":[{"fieldId":1},{"fieldId":2}]
   * }
   *
   *
   *
   *
   */


}
