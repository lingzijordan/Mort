/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.implicities

import com.bigeyedata.mort.infrastructure.metadata.models.Field

object FieldsImplicits {

  implicit class FieldsWrapper(fields: List[Field]) {

    def codeNameFor(fieldId: Int): String = fields.find(_.id == fieldId).get.codeName

    def fieldTypeFor(fieldId: Int): String = fields.find(_.id == fieldId).get.fieldType

    //todo: if target field is not existed, how to handle?
    def fieldById(fieldId: Int): Field = fields.filter(_.id == fieldId).head

  }

}
