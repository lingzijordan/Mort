package com.bigeyedata.mort.commons.implicits

import java.util.concurrent.TimeUnit

import akka.actor.{ActorContext, ActorRefFactory}
import akka.util.Timeout

import scala.concurrent.Await


object AkkaImplicits {

  val selectionTimeout: Timeout = Timeout(5, TimeUnit.SECONDS)
  
  implicit class ActorContextImplicit(context: ActorContext) {
    def selectSiblingActor(actorName: String) = {
      Await.result(context.actorSelection(context.parent.path / actorName).resolveOne()(selectionTimeout), selectionTimeout.duration)
    }
  }
  
  implicit class ActorRefFactoryImplicit(actorRefFactory: ActorRefFactory) {
    def selectSiblingActor(actorName: String) = {
      Await.result(actorRefFactory.actorSelection("akka://default/user/" + actorName).resolveOne()(selectionTimeout), selectionTimeout.duration)
    }
  }
}
