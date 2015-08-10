package com.bigeyedata.mort

import akka.actor.{Actor, ActorRefFactory}
import akka.event.Logging._
import com.bigeyedata.mort.routes.MortRouter
import spray.http.{HttpResponse, HttpRequest}
import spray.routing.directives.LogEntry

class MortDomainRouterActor extends MortRouter with Actor {
  def actorRefFactory: ActorRefFactory = context

  def requestMethodAndResponseStatusAsInfo(req: HttpRequest): Any => Option[LogEntry] = {
    case res: HttpResponse => Some(
      LogEntry(
        "\nRequest:\n\t" + req.method + ":" + req.uri + "\n\tBody:" + req.entity.data.asString +
        "\nResponse:" + res.message.status + "\n\tBody:" + res.entity.data.asString,
        InfoLevel)
    )
    case _ => None // other kind of responses
  }

  def routeWithLogging = logRequestResponse(requestMethodAndResponseStatusAsInfo _)(rootRoute)

  def receive = runRoute(routeWithLogging)
}
