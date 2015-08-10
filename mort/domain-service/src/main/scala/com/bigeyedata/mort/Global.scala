package com.bigeyedata.mort

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

object Global {
  val mortActorSystem = ActorSystem()

  implicit val requestTimeout: Timeout =
    Timeout(ConfigFactory.load().getDuration("spray.can.server.request-timeout", TimeUnit.SECONDS), TimeUnit.SECONDS)

}
