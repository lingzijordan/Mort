package com.bigeyedata.mort.dbdataset.implicits

import com.bigeyedata.mort.dataset.DataSetMessages.SqlStatement

/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
object SqlStatementImplicits {

  implicit def convertToSqlStatement(sql: String): SqlStatement = SqlStatement(sql)
}
