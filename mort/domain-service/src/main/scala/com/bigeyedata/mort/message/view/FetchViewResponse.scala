/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.view

import org.joda.time.DateTime

case class FetchViewResponse(
                              id: Int,
                              name: String,
                              description: String,
                              createdBy: String,
                              createdAt: DateTime,
                              reportId: Int,
                              reportName: String
                              )
