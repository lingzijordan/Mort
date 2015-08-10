/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.model

import com.bigeyedata.customerdata.common.model.DatabaseProperties

case class PrepareImportData(sqlStatements:Array[String],
                             destination: String,
                             dateSetId: Int,
                             srcDataSource: DatabaseProperties,
                             targetDataSource: DatabaseProperties,
                             codes: List[String]) {

}
