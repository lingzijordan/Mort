/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.implicits

import com.bigeyedata.mort.dataset.DataSetMessages.{SqlStatement, DataSource}
import com.bigeyedata.mort.dbdataset.DatabasePropertiesSupport
import com.bigeyedata.mort.dbdataset.implicits.ConfigImplicits._
import com.bigeyedata.mort.infrastructure.metadata.models.DataSet
import com.bigeyedata.mort.infrastructure.metadata.services.{DataSetService, DatabaseCustomerDataSourceService}
import com.typesafe.config.ConfigFactory

object DbDataSetImplicits {

  implicit class RichDbDataSet(dbDataSet: DataSet) extends DatabaseCustomerDataSourceService with DataSetService with DatabasePropertiesSupport {

    def fetchDbSrcDataSource: DataSource = {
      fetchDatabaseDataSource(dbDataSet.dataSourceId) match {
        case Some(ds) => DataSource(ds.databaseType, ds.host, ds.port, ds.databaseName, ds.userName, ds.password)
        case _ => null
      }
    }

    def query: SqlStatement = SqlStatement(dbDataSet.queryStatement)

    def fetchDbTargetDataSource: DataSource = ConfigFactory.load().targetDataSource

    type ColumnNames = List[String]

    def fetchColumns: ColumnNames = {
      fetchFieldsBy(dbDataSet.id).map(_.codeName)
    }
  }

}
