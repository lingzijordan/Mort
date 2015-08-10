/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.sqlgenerator

import com.bigeyedata.mort.sqlmaterial.{ClauseExpression, FieldExpression, Fields}

case class Select(fieldsExp: FieldExpression*) extends ClauseExpression {
  val fields = Fields(fieldsExp: _*)

  override def evaluate: String = {
    val fieldClause: String = fields.exp.map(_.evaluate).mkString(",")
    if (fields.onlyCategory)
      s"select ${fieldClause},count(*) as counter"
    else
      s"select ${fieldClause}"
  }
}

