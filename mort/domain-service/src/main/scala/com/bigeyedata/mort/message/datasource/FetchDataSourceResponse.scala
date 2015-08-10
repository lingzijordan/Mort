/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.datasource

case class FetchDataSourceResponse(
                               id: Int,
                               name: String,
                               description: String,
                               dataSourceType: String,
                               options: Map[String, Any]
                               )

case class MasterDataSourceResponse(dataSourceId: Int,
                                    name: String,
                                    dataSourceType: String)