/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.sqlmaterial

case class Fields(exp: FieldExpression*) {
  def onlyMetric: Boolean = exp.forall(_.isMetric)
  def onlyCategory: Boolean = exp.forall(_.isCategory)
}

trait FieldExpression extends Expression {
  def isCategory:Boolean = false
  def isMetric:Boolean = false
}

case class Metric(codeName: String, operation: String) extends FieldExpression {
  override def isMetric: Boolean = true

  override def evaluate: String = {
    if (!codeName.isEmpty) s"${operation}($codeName) as ${operation}_${codeName}" else ""
  }
}

case class Category(codeName: String) extends FieldExpression {
  override def isCategory: Boolean = true

  override def evaluate: String = codeName
}
