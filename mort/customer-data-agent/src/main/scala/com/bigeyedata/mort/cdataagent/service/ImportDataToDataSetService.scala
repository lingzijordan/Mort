/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.cdataagent.service

import akka.actor.Actor
import com.bigeyedata.mort.agent.ImportDataSetMessage.ImportDataServiceSetRequest
import com.bigeyedata.mort.cdataagent.utils.ServiceConfigurationReader
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import spray.http.StatusCodes._
import scala.concurrent.ExecutionContext.Implicits._

trait ImportDataToDataSetService extends ServiceConfigurationReader with MortHttpPostService {
  self: Actor =>

  def importCustomerData(message: ImportDataServiceSetRequest)(successHandler: => Unit)(failedHandler: => Unit): Unit = {

    val serverUrlPath = servicePath(importService)
    asyncPost(serverUrlPath, message) { response =>
      response.onComplete { code =>
        code.get.status.intValue match {
          case Accepted.intValue => {
            successHandler
            log.info(s"send import command to ${serverUrlPath} success and processed")
          }
          case _ => {
            failedHandler
            log.error(BadRequestException(s"import service failed response code ${code.get.status.intValue}"), s"${serverUrlPath} import service failed")
          }
        }

      }
    }
  }

}

