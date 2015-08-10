/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.message.composer

import com.bigeyedata.customerdata.domainservice.db.message.{FieldsRequest, ImportRequest, PreviewRequest, SqlStatementRequest}
import com.bigeyedata.customerdata.common.global.Variables._

trait SemicolonRemoval {
  def removeSemicolon(rawSql: String) = rawSql.last match {
    case ';' => rawSql.dropRight(1)
    case _ => rawSql
  }
}

trait PreviewSqlComposer extends SemicolonRemoval {
  self: PreviewRequest =>

  val previewSql: String = {
    dataSource.databaseType.toLowerCase match {
      case "oracle" =>
        s"""
           |select * from
           |(select t1.*,rownum as ${ROW_COUNT_COLUMN_NAME} from (${removeSemicolon(rawSql.statement)}) t1 order by ${ROW_COUNT_COLUMN_NAME} asc)
                                                               |where ${ROW_COUNT_COLUMN_NAME} <=$rowCount
         """.stripMargin.trim
      case "mysql" =>
        s"""
           |select * from
           |(SELECT t1.*, @rownum:=@rownum+1 AS ${ROW_COUNT_COLUMN_NAME} FROM (SELECT @rownum:=0) r, (${removeSemicolon(rawSql.statement)}) t1) t2
           |where ${ROW_COUNT_COLUMN_NAME} <= $rowCount order by ${ROW_COUNT_COLUMN_NAME} asc;
         """.stripMargin.trim
    }
  }
}

trait CountSqlComposer {
  self: SqlStatementRequest =>
  val countSql: String = {
    s"select count(*) as ${ROW_COUNT_COLUMN_NAME} ${
      statement.drop(statement.toLowerCase.indexOf("from"))
    }"
  }
}

trait QueryFieldsSqlComposer extends SemicolonRemoval {
  self: FieldsRequest =>

  val queryFieldsSql: String = {
    val statement = removeSemicolon(rawSql.statement)
    if (statement.contains("where")) statement + " and 1 = 0" else statement + " where 1 = 0"
  }
}

trait SqlPagingComposer extends SemicolonRemoval {
  self: ImportRequest =>

  def splitSqlSections(totalRows: Long): Array[String] = {
    (1.toLong to maxPageNum(totalRows)).map { pageNum =>
      def startCount: Long = (pageNum - 1) * batchRecords + 1
      def endCount: Long = if (pageNum < maxPageNum(totalRows)) pageNum * batchRecords else totalRows

      self.srcDataSource.databaseType.toLowerCase match {
        case "oracle" => oracleSqlWrapper(startCount, endCount)
        case "mysql" => mySqlWrapper(startCount, endCount)
        case _ => throw new Exception("not support such target database")
      }
    }.toArray
  }

  private def mySqlWrapper(startCnt: Long, endCnt: Long): String = {
    s"""
       |select *
       |from
       |(SELECT t1.*, @rownum:=@rownum+1 AS ${ROW_COUNT_COLUMN_NAME} FROM (SELECT @rownum:=0) r, (${removeSemicolon(rawSql.statement)}
    ) t1) t2
  |where ${ROW_COUNT_COLUMN_NAME} between $startCnt and $endCnt
     """.stripMargin.trim
  }

  private def oracleSqlWrapper(startNum: Long, endNum: Long) = {
    s"""
       |select * from (select t1.*,rownum as ${ROW_COUNT_COLUMN_NAME} from (${removeSemicolon(rawSql.statement)}) t1) where ${ROW_COUNT_COLUMN_NAME} between $startNum and $endNum
     """.stripMargin.trim
  }

  private def maxPageNum(totalRecords: Long) =
    if (totalRecords % batchRecords == 0) totalRecords / batchRecords else (totalRecords / batchRecords) + 1

  //todo: use configuration
  private def batchRecords = 500
}
