/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.context

import com.bigeyedata.mort.dataset.GlobalContext
import org.apache.spark.sql.{DataFrame, SQLContext}

trait SparkSqlSupport {
  implicit val sqlContext = new SQLContext(GlobalContext.sparkContext)

  def load(tableName: String)(implicit sqlContext: SQLContext): DataFrame

  def executeAnalysis(tableName: String, statement: String)(implicit sqlContext: SQLContext): DataFrame = {
    load(tableName)(sqlContext).registerTempTable(tableName)
    sqlContext.sql(statement)
  }

}
