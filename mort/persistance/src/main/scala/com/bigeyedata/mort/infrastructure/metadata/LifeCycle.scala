package com.bigeyedata.mort.infrastructure.metadata

import scalikejdbc.config.DBsWithEnv

object LifeCycle {
  private def environment: String = System.getProperty("env", "dev")

  def start() = {
    DBsWithEnv(environment).setupAll()
  }

  def stop() = {
    DBsWithEnv(environment).closeAll()
  }
}
