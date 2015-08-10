/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.sqlgenerator

import com.bigeyedata.mort.sqlmaterial.{ClauseExpression, FieldExpression, Fields}

case class Group(fieldsExp: FieldExpression*) extends ClauseExpression {
  val fields = Fields(fieldsExp: _*)

  override def evaluate: String = {
    if (fields.onlyMetric)
      ""
    else
      s"group by ${fields.exp.filter(_.isCategory).map(_.evaluate).mkString(",")}"
  }
}