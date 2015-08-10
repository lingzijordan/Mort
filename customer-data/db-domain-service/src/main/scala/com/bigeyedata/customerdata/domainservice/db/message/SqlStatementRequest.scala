/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.message

import com.bigeyedata.customerdata.domainservice.db.message.composer.CountSqlComposer

case class SqlStatementRequest(statement: String) extends CountSqlComposer