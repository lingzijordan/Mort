package com.bigeyedata.mort.hooks

import com.bigeyedata.mort.infrastructure.metadata.LifeCycle

trait ShutdownHook {

  def addShutdownHook(): Unit = {
    Runtime.getRuntime.addShutdownHook(new Thread() {
      override def run() = {
        shutdownSpray()

      }
    })
  }

  def shutdownSpray(): Unit = {
//    IO(Http) ! PoisonPill
  }

  def shutdownMetadata(): Unit = {
    LifeCycle.stop()
  }
}
