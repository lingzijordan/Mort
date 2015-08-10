package com.bigeyedata.mort

import akka.actor.ActorRefFactory
import com.bigeyedata.mort.TestEnvironment.guranteeActorSystemAvailable
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

import scala.concurrent.duration._


class ApiCommonSpec
  extends FlatSpec
  with Matchers
  with HttpService
  with ScalatestRouteTest
  with BeforeAndAfterAll
  with ActorSystemProvider {
  def actorRefFactory: ActorRefFactory = system

  implicit val timeout = RouteTestTimeout(25.seconds)

  override def beforeAll() {
    guranteeActorSystemAvailable
  }

}
