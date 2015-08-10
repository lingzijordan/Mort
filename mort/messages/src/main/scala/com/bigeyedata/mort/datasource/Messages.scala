package com.bigeyedata.mort.datasource

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.infrastructure.metadata.models.CustomerDataSource
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction

import scala.collection.immutable.Map

object CreateDataSourceMessages {

  case class CreateDataSource(name: String,
                              dataSourceType: String,
                              description: String,
                              options: Map[String, String]
                               )

  case class CreateSuccess(id: Int)

  case class CreatedSpecificDataSourceSuccess(id: Int, transaction: Transaction)

  case class ValidateFailed(message: String, transaction: Transaction)

  case class CreateSpecificDataSource(id: PrimaryKey, options:Map[String, String], transaction: Transaction)

}


case class FetchMasterDataSource(dataSourceId: Int)

case class FetchSpecificDataSource(dataSource: CustomerDataSource)

case class DataSourceFetched(dataSource: CustomerDataSource, options:Map[String, Any])