package com.bigeyedata.mort.commons

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import com.typesafe.config.ConfigFactory

object RequestTimeOutSupport {
  implicit val requestTimeout: Timeout =
    Timeout(ConfigFactory.load().getDuration("spray.can.server.request-timeout", TimeUnit.SECONDS), TimeUnit.SECONDS)
}
