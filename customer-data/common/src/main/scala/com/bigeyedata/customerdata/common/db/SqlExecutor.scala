/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.common.db

import java.sql.{SQLException, ResultSet, Connection, DriverManager}

import com.bigeyedata.customerdata.common.model.DatabaseProperties

class SqlExecutor(properties: DatabaseProperties) {
  def executeQuery[T](query: String)(converter: ResultSet => T): T = {
    using { conn =>
      val pst = conn.prepareStatement(query)
      converter(pst.executeQuery())
    }
  }

  def executeCommand(sql: String): Long = {
    using { conn =>
      val ps = conn.prepareStatement(sql)
      ps.executeUpdate.toLong
    }
  }

  def connectionAvailable: Boolean = {
    try {
      createConnection.close()
      true
    } catch {
      case _ => false
    }
  }

  private def using[T](f: Connection => T): T = {
    val conn = createConnection
    try {
      f(conn)
    } finally {
      conn.close()
    }
  }

  @throws(classOf[SQLException])
  private def createConnection: Connection = {
    Class.forName(properties.driverClass)
    DriverManager.getConnection(properties.dbUrl, properties.userName, properties.password)
  }
}

object SqlExecutor {
  def apply(properties: DatabaseProperties) = new SqlExecutor(properties)
}
