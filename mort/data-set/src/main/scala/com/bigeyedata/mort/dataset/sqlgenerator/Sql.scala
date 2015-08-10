/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.sqlgenerator

import com.bigeyedata.mort.sqlmaterial.{ClauseExpression, FieldExpression}

object Sql {
  def select(clauseExp: FieldExpression*): SqlDSL = {
    new SqlDSL(clauseExp:_*)
  }

  def select(clauseExp: List[FieldExpression]): SqlDSL = {
    new SqlDSL(clauseExp:_*)
  }
}

private[sqlgenerator] class SqlDSL(clauseExp: FieldExpression*) {
  private var sqlParts: List[ClauseExpression] = Select(clauseExp: _*) :: Nil

  def groupBy(clauseExp: FieldExpression*): SqlDSL = {
    sqlParts = Group(clauseExp: _*) :: sqlParts
    this
  }

  def groupBy(clauseExp: List[FieldExpression]): SqlDSL = {
    sqlParts = Group(clauseExp: _*) :: sqlParts
    this
  }

  def from(tableName: String): SqlDSL = {
    sqlParts = From(tableName) :: sqlParts
    this
  }

  def toSql = sqlParts.reverse.map(_.evaluate).mkString(" ")
}
