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
import com.bigeyedata.customerdata.common.model.{DatabaseProperties, DataFrame}

trait PreviewService {
  import ResultSetEnhancement._

  def preview(properties: DatabaseProperties, previewSql: String): DataFrame = {
    SqlExecutor(properties).executeQuery(previewSql) { rs =>
      rs.toDataFrame
    }
  }
}
