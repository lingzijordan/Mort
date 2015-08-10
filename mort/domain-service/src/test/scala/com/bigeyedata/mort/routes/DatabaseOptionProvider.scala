/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.routes

trait DatabaseOptionProvider {
  val databaseOptions = Map(
    "host" -> "localhost",
    "port" -> "3306",
    "database" -> "bigeye_test",
    "username" -> "bigeye",
    "password" -> "bigeye123",
    "databaseType" -> "mysql"
  )
}
