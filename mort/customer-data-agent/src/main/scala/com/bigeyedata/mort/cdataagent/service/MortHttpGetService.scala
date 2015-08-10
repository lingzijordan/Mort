/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.cdataagent.service

import akka.actor.Actor
import spray.client.pipelining._
import spray.http.HttpResponse

import scala.concurrent.Future
import scala.concurrent.duration.Duration

trait MortHttpGetService extends MortHttpService {
  self: Actor =>

  def asyncGet(url: URL)(handleResponse: Future[HttpResponse] => Unit) = {
    asyncRequest(handleResponse) {
      Get(url)
    }
  }

  def syncGet(url: URL, atMost: Duration) = {
    syncRequest(atMost) {
      Get(url)
    }
  }
}
