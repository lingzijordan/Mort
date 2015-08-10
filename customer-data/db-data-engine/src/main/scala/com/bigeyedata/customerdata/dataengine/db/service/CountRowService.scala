/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.service

import com.bigeyedata.customerdata.common.db.SqlExecutor
import com.bigeyedata.customerdata.common.implicits.ResultSetEnhancement
import com.bigeyedata.customerdata.common.model.DatabaseProperties

trait CountRowService {
  import ResultSetEnhancement._

  def count(properties: DatabaseProperties, countSql: String): Long = {
    SqlExecutor(properties).executeQuery(countSql) { rs =>
      rs.totalRowCount
    }
  }

}
