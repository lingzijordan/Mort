/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset

import com.typesafe.config.ConfigFactory
import com.bigeyedata.mort.dbdataset.implicits.ConfigImplicits._


trait DatabasePropertiesSupport {

  implicit val databaseProperties = ConfigFactory.load().databaseProperties
  
  implicit val databaseType = databaseProperties.dbType
}
