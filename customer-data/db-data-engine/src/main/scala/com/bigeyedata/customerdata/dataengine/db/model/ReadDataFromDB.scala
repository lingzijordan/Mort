/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.dataengine.db.model

import com.bigeyedata.customerdata.common.model.DatabaseProperties

case class ReadDataFromDB(dsId: Int,
                             tableName: String,
                             sql: String,
                             codes: List[String],
                             srcDbProperties: DatabaseProperties,
                             targetDbProperties: DatabaseProperties)
