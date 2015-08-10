package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.infrastructure.metadata.models.DatabaseCustomerDataSource
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction

trait DatabaseCustomerDataSourceService {
  def createDatabaseDataSource(
                                id: Int,
                                host: String,
                                port: Int,
                                databaseName: String,
                                userName: String,
                                password: String,
                                databaseType: String)
                              (transaction: Transaction): Unit = {
    implicit val session = transaction.session

    DatabaseCustomerDataSource.create(
      id,
      host,
      port,
      databaseName,
      userName,
      password,
      databaseType
    )
  }

  def fetchDatabaseDataSource(dataSourceId: Int): Option[DatabaseCustomerDataSource] = {
    DatabaseCustomerDataSource.find(dataSourceId)
  }


}
