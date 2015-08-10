/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.mort.dbdataset.sql

import com.bigeyedata.mort.dataset.context.SparkSqlSupport
import com.bigeyedata.mort.dbdataset.DatabasePropertiesSupport
import org.apache.spark.sql.{DataFrame, SQLContext}

trait DatabaseSparkSql extends SparkSqlSupport with DatabasePropertiesSupport {

  override def load(tableName: String)(implicit sqlContext: SQLContext): DataFrame = {
    sqlContext.read.format("jdbc")
      .options(
        Map(
          "url" -> databaseProperties.dbUrl,
          "driver" -> databaseProperties.driverClass,
          "dbtable" -> tableName
        )).load
  }
}