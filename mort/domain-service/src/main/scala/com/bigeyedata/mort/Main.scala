package com.bigeyedata.mort

import akka.io.IO
import spray.can.Http
import spray.routing.SimpleRoutingApp

object Main extends App with SimpleRoutingApp with MortDomainService {
  IO(Http) ! Http.Bind(mortRouteActor, interface = "127.0.0.1", port = 8080)
}

