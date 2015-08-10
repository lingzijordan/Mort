/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.message

import com.bigeyedata.customerdata.common.model.DatabaseProperties

trait DatabasePropertiesComposer {
  self: DataSourceRequest =>

  type DriverClass = String
  type DbUrl = String

  private val drivers: Map[String, (DbUrl, DriverClass)] = {
    Map(
      "oracle" ->(s"jdbc:oracle:thin:@${host}:${port}:${database}", "oracle.jdbc.driver.OracleDriver"),
      "mysql" ->(s"jdbc:mysql://${host}:${port}/${database}", "com.mysql.jdbc.Driver")
    )
  }

  private def driver: (DbUrl, DriverClass) = {
    drivers.get(databaseType.toLowerCase).get
  }

  def databaseProperties: DatabaseProperties =
    DatabaseProperties(databaseType.toLowerCase, driver._1, driver._2, username, password)
}

case class DataSourceRequest(databaseType: String,
                      host: String,
                      port: String,
                      database: String,
                      username: String,
                      password: String) extends DatabasePropertiesComposer