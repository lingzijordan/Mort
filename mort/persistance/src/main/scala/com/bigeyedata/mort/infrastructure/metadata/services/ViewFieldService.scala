/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.Types.ID
import com.bigeyedata.mort.infrastructure.metadata.models.ViewField
import com.bigeyedata.mort.infrastructure.metadata.models.ViewField._
import scalikejdbc._

trait ViewFieldService {

  def fetchByViewId(viewId: ID): List[ViewField] = {
    ViewField.findAllBy(sqls.eq(vf.viewId, viewId))
  }
}
