package com.bigeyedata.mort.cdataagent.utils

import com.typesafe.config.ConfigFactory

/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
trait ServiceConfigurationReader {

  lazy val config = ConfigFactory.load()

  val pathRoot = "cdata"

  val importService = "import-service"

  def servicePath(serviceName: String): String = "http://" + config.getString(s"${pathRoot}.${serviceName}")
}
