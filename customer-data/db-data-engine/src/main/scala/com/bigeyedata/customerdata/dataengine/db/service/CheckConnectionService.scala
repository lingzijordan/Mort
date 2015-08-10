package com.bigeyedata.customerdata.dataengine.db.service

import com.bigeyedata.customerdata.common.db.SqlExecutor
import com.bigeyedata.customerdata.common.model.{DataFrame, DatabaseProperties}

/* *\
** **
** __ __ _________ _____ ©Mort BI **
** | \/ / () | () |_ _| (c) 2015 **
** |_|\/|_\____|_|\_\ |_| http://www.bigeyedata.com **
** **
\* */
trait CheckConnectionService {

  def connectionAvailable(properties: DatabaseProperties): Boolean = {
    SqlExecutor(properties).connectionAvailable
  }

}
