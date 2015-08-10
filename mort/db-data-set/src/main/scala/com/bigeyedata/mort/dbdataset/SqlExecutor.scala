/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.mort.dbdataset

import java.sql.{Connection, DriverManager, ResultSet, SQLException}

import com.bigeyedata.mort.dbdataset.model.DatabaseProperties

object SqlExecutor {
  def executeQuery[T](query: String)(converter: ResultSet => T)(implicit databaseProperties: DatabaseProperties): T = {
    using { conn =>
      val pst = conn.prepareStatement(query)
      converter(pst.executeQuery())
    }
  }

  def executeCommand(sql: String)(implicit databaseProperties: DatabaseProperties): Long = {
    using { conn =>
      val ps = conn.prepareStatement(sql)
      ps.executeUpdate.toLong
    }
  }

  def connectionAvailable(implicit databaseProperties: DatabaseProperties): Boolean = {
    try {
      createConnection.close()
      true
    } catch {
      case e:Exception => false
    }
  }

  private def using[T](f: Connection => T)(implicit databaseProperties: DatabaseProperties): T = {
    val conn = createConnection
    try {
      f(conn)
    } finally {
      conn.close()
    }
  }

  @throws(classOf[SQLException])
  private def createConnection(implicit databaseProperties: DatabaseProperties): Connection = {
    Class.forName(databaseProperties.driverClass)
    DriverManager.getConnection(databaseProperties.dbUrl)
  }
}
