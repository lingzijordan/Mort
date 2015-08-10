/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types.ID
import com.bigeyedata.mort.infrastructure.metadata.models.ViewField
import com.bigeyedata.mort.infrastructure.metadata.services.ViewFieldService

trait ViewFieldFetcher extends ViewFieldService {

  def fetchViewFields(viewId: ID): List[ViewField] = {
    fetchByViewId(viewId)
  }

}
