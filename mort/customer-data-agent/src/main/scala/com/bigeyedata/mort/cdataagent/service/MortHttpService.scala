/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.cdataagent.service

import akka.actor.Actor
import akka.event.Logging
import spray.client.pipelining._
import spray.http.{HttpRequest, HttpResponse}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

trait MortHttpService {

  self: Actor =>

  lazy val log = Logging(context.system, this)

  type URL = String

  def asyncRequest(handleResponse: Future[HttpResponse] => Unit)
                  (buildRequest: => HttpRequest): Unit = {
    implicit val fun = context.dispatcher
    val pipeline = sendReceive
    val response: Future[HttpResponse] = pipeline(buildRequest)
    handleResponse(response)
  }

  def syncRequest(atMost: Duration)(buildRequest: => HttpRequest): HttpResponse = {
    implicit val fun = context.dispatcher
    val pipeline = sendReceive
    Await.result(pipeline(buildRequest), atMost)
  }
}
