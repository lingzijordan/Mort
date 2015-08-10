/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.infrastructure.metadata.models.Field

trait FieldService {

  def fetchFieldById(fieldId: Int): Option[Field] = Field.find(fieldId)
}
