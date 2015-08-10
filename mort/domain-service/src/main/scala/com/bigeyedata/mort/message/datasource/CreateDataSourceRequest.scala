/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.datasource

import com.bigeyedata.mort.datasource.CreateDataSourceMessages
import CreateDataSourceMessages.CreateDataSource

import scala.collection.immutable.Map

case class CreateDataSourceRequest(
                                     id: Int = -1,
                                     name: String,
                                     dataSourceType: String,
                                     description: String,
                                     options: Map[String, String]
                                     ) {
                                        require(!name.isEmpty, "name must not be empty")
                                        require(!dataSourceType.isEmpty, "dataSourceType must not be empty")
                                        require(3 <= name.length && name.length <= 20, "name's length must be between 3 and 20")
                                        require(!options.isEmpty, "options must not be empty")

                                        def toCreateDataSource = CreateDataSource(name,  dataSourceType, description, options)
                                      }

