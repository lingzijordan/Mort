package com.bigeyedata.customerdata.domainservice.db.common

import akka.actor.ActorRefFactory
import org.scalatest.{FlatSpecLike, Matchers}
import spray.routing.HttpService
import spray.testkit.ScalatestRouteTest

import scala.concurrent.duration._
/**
 * Created by zhangyi on 6/25/15.
 */
trait RouteApiSpec extends FlatSpecLike with Matchers with ScalatestRouteTest with HttpService{
  def actorRefFactory: ActorRefFactory = system

  implicit val timeout = RouteTestTimeout(25.seconds)
}
