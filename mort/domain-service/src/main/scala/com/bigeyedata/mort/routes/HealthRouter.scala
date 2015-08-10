/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

import akka.actor.{Actor, ActorNotFound, ActorRefFactory}
import com.bigeyedata.mort.cdataagent.service.MortHttpGetService
import com.bigeyedata.mort.cdataagent.utils.ServiceConfigurationReader
import com.bigeyedata.mort.commons.implicits.AkkaImplicits._
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import com.bigeyedata.mort.dataset.{DataSetConfiguration, GlobalContext}
import com.bigeyedata.mort.dbdataset.{DatabasePropertiesSupport, SqlExecutor}
import com.bigeyedata.mort.hooks.InitHook
import spray.http.StatusCodes
import spray.routing.HttpService

import scala.collection.mutable
import scala.concurrent.duration._

trait HealthRouter extends HttpService with MortHttpGetService {
  self: Actor =>

  def actorContext: ActorRefFactory

  def healthRoute = pathPrefix("health") {
    pathEnd {
      get {
        complete {
          List(
            checkForPredefinedActors,
            checkForCData,
            checkForSpark,
            checkForDataSet
          )
        }
      }
    }
  }

  private[this] def checkForDataSet: RESULT = {

    new CheckFor with DataSetConfiguration {
      def result = persistanceType match {
        case "db" => new CheckFor with DatabasePropertiesSupport {
          def result = RESULT("DataSet Database", if (SqlExecutor.connectionAvailable) OK() else FAILURE())
        }.result
      }
    }.result

  }

  private[this] def checkForSpark: RESULT = {

    try {
      GlobalContext.sparkContext
      RESULT("SPARK", OK())
    }
    catch {
      case _ => RESULT("SPARK", FAILURE())
    }
  }

  private[this] def checkForCData: RESULT = {
    new CheckFor with ServiceConfigurationReader {
      def result = syncGet(servicePath("health"), 5 second).status.intValue match {
        case StatusCodes.OK.intValue => {
          RESULT("CustomerData", OK())
        }
        case _ => {
          RESULT("CustomerData", FAILURE())
        }

      }
    }.result

  }

  private[this] def checkForPredefinedActors: RESULT = {
    val checkStatus = mutable.MutableList[CheckStatus]()

    new CheckFor with InitHook {
      def result = {
        mappingService.foreach {
          case (name, _) =>
            try {
              actorContext.selectSiblingActor(name)
              checkStatus += OK(name)
            } catch {
              case notFound: ActorNotFound =>
                checkStatus += FAILURE(notFound.getMessage)
            }
        }
        RESULT("PreDefined Actors", checkStatus.toList: _*)
      }

    }.result
  }

  private[this] def OK(name: String = "") = {
    CheckStatus(s"OK ${name}")
  }

  private[this] def FAILURE(name: String = "") = {
    CheckStatus(s"FAILED ${name}")
  }

}

case class RESULT(name: String, checkStatus: CheckStatus*)

case class CheckStatus(message: String)

trait CheckFor {
  def result: RESULT
}
