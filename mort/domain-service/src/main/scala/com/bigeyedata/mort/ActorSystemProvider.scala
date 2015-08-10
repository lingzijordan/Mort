package com.bigeyedata.mort

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

trait ActorSystemProvider {
  val mortActorSystem = Global.mortActorSystem
  implicit val requestTimeout = Timeout(ConfigFactory.load().getDuration("spray.can.server.request-timeout", TimeUnit.SECONDS), TimeUnit.SECONDS)
}
