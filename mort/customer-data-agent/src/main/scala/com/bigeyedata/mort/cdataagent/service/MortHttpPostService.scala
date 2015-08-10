/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.mort.cdataagent.service

import akka.actor.Actor
import com.bigeyedata.mort.agent.ImportDataSetMessage.CDataServiceMessage
import com.bigeyedata.mort.commons.implicits.MortJsonSupport._
import spray.client.pipelining._
import spray.http.HttpResponse

import scala.concurrent.Future


trait MortHttpPostService extends MortHttpService {
  self: Actor =>

  def asyncPost[TMessage <: CDataServiceMessage](url: URL, message: TMessage)
                                             (handleResponse: Future[HttpResponse] => Unit): Unit = {
    asyncRequest(handleResponse) {
      Post(url, message)
    }
  }
}
