/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.services

import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.infrastructure.metadata.models.Field
import com.bigeyedata.mort.infrastructure.metadata.services.DataSetService

trait FieldFetcher extends DataSetService {
  def fetchFields(dataSetId: Int):List[Field] = {
    if(hasNotExistField(dataSetId)) throw ResourceNotExistException(s"Field with dataSetId ${dataSetId} does not exist")
    fetchFieldsBy(dataSetId)
  }
}
