/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db

import com.bigeyedata.customerdata.domainservice.db.core.{ActorSystemProvider, AggregateServiceEntryRouter}
import spray.routing.SimpleRoutingApp

object Boot extends App with SimpleRoutingApp with ActorSystemProvider with AggregateServiceEntryRouter {
  startServer("localhost", 9000) {
    entryRoute
  }
}
