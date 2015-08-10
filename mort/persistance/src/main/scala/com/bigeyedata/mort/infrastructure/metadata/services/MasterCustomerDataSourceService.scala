package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.infrastructure.metadata.models.CustomerDataSource
import com.bigeyedata.mort.infrastructure.metadata.models.CustomerDataSource._
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction
import org.joda.time.DateTime
import scalikejdbc._

trait MasterCustomerDataSourceService {
  def createMasterDataSource(name: String, dataSourceType: String, description: String)
                            (implicit transaction: Transaction): PrimaryKey = {
    implicit val session = transaction.session
    create(name, Option(description), dataSourceType, DateTime.now(), DateTime.now(), "admin", "admin").id
  }

  def hasExistDataSource(dataSourceName: String): Boolean = {
    countBy(sqls.eq(cds.name, dataSourceName)) > 0
  }

  def hasNotExistDataSource(id: Int): Boolean = {
    CustomerDataSource.find(id).isEmpty
  }


  def fetchMasterDataSources: List[CustomerDataSource] = CustomerDataSource.findAll()

  def fetchMasterDataSourceById(dataSourceId: Int): Option[CustomerDataSource] = CustomerDataSource.find(dataSourceId)
}
