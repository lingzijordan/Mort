/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.cdataagent.utils

import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

class ServiceConfigurationReaderSpec extends FlatSpec with Matchers with ServiceConfigurationReader {

  "import-service" should "equal localhost/cdata/import" in {
    servicePath(importService) should be("http://localhost/cdata/import")
  }

  "import-configuration" should "get mapped key" in {
    val config = ConfigFactory.load()
    println(config.getString("cdata.import-service"))
  }


}
