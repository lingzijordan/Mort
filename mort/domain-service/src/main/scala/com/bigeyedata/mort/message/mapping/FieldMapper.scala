/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.mapping

import com.bigeyedata.mort.infrastructure.metadata.models.Field
import com.bigeyedata.mort.message.dataset.{FetchFieldResponse, FieldResponse}

object FieldMapper {
  implicit class FetchFieldResponseMapper(fields: List[Field]) {
    def response: FetchFieldResponse = {
      def toFieldResponse(field: Field) = {
        FieldResponse(field.id, field.codeName, field.aliasName, field.fieldType, field.dataClassType)
      }

      def filterByFieldTypeAndMapToFieldResponse(fieldType: String = "metric"): List[FieldResponse] = {
        fields filter(_.fieldType.toLowerCase == fieldType) map toFieldResponse
      }

      FetchFieldResponse(
        filterByFieldTypeAndMapToFieldResponse("metric"),
        filterByFieldTypeAndMapToFieldResponse("category"),
        filterByFieldTypeAndMapToFieldResponse("timecategory"))
    }
  }
}
