/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.sqlgenerator

import com.bigeyedata.mort.sqlmaterial.ClauseExpression

case class From(tableName: String) extends ClauseExpression {
  override def evaluate: String = s"from ${tableName}"
}
