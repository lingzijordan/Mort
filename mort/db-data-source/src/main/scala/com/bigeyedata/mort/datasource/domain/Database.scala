/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.datasource.domain

object Database {
  def database(databaseType: String): DatabaseType = databaseType.toLowerCase match {
    case "oracle" => Oracle
    case "mysql" => MySQL
  }

  def isNotSupported(databaseType: String): Boolean = databaseType.toLowerCase match {
    case "oracle" => false
    case "mysql" => false
    case _ => true
  }
}


sealed abstract class DatabaseType {
  def defaultPort: Int
}

case object Oracle extends DatabaseType {
  override def defaultPort: Int = 1521
}

case object MySQL extends DatabaseType {
  override def defaultPort: Int = 3306
}


