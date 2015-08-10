/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.implicits

import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.dataset.DataSetMessages.DataSource
import com.bigeyedata.mort.dbdataset.ConfigurationKeys._
import com.bigeyedata.mort.dbdataset.model.DatabaseProperties
import com.typesafe.config.Config
object ConfigImplicits {

  implicit class ConfigImplicit(config: Config) {

    def extractValue(key: String): String = {
      config.getString(s"$DB_CONFIG.$key")
    }

    val host = extractValue(HOST)
    val port = extractValue(PORT)
    val dbName = extractValue(DB_NAME)
    val user = extractValue(USER)
    val dbType = extractValue(DB_TYPE)
    val password = extractValue(PASSWORD)

    type DriverClass = String
    type DbUrl = String

    def targetDataSource = DataSource(dbType, host, port.toInt, dbName, user, password)

    def databaseProperties = {

      val drivers: Map[String, (DbUrl, DriverClass)] = {
        Map(
          "oracle" ->(s"jdbc:oracle:thin:@${host}:${port}:${dbName}", "oracle.jdbc.driver.OracleDriver"),
          "mysql" ->(s"jdbc:mysql://${host}:${port}/${dbName}?user=$user&password=$password", "com.mysql.jdbc.Driver")
        )
      }

      def driver: (DbUrl, DriverClass) = {
        drivers.get(dbType.toLowerCase) match {
          case Some((url, driver)) =>
            (url, driver)
          case None => throw BadRequestException(s"$dbType is not supported")
        }
      }

      DatabaseProperties(dbType.toLowerCase, driver._1, driver._2, user, password)
    }

  }

}
