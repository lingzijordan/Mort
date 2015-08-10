package com.bigeyedata.mort

import akka.actor.Props
import com.bigeyedata.mort.hooks.{InitHook, ShutdownHook}

trait MortDomainService extends InitHook with ShutdownHook with ActorSystemProvider {
  implicit val system = mortActorSystem
  val mortRouteActor = system.actorOf(Props[MortDomainRouterActor], "mort-service")
  init(mortActorSystem)
  addShutdownHook()
}
