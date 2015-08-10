package com.bigeyedata.mort.dataset.services

import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.infrastructure.metadata.models.DataSet
import com.bigeyedata.mort.infrastructure.metadata.services.{MasterCustomerDataSourceService, DataSetService}

trait DataSetFetcher extends DataSetService with MasterCustomerDataSourceService{
  def fetchDataSets(dataSourceId: Int): List[DataSet] = {
    if(hasNotExistDataSource(dataSourceId)) throw ResourceNotExistException(s"DataSource ${dataSourceId} does not exist")
    fetchDataSetsBy(dataSourceId)
  }
}
