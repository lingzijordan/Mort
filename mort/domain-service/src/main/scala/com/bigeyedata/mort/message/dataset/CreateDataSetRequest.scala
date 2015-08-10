/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.dataset

import com.bigeyedata.mort.commons.Verifiable
import com.bigeyedata.mort.commons.implicits.ListImplicits._
import com.bigeyedata.mort.dataset.DataSetMessages.{Field, CreateDataSet}
import com.bigeyedata.mort.dataset.{DataSetConfiguration, RandomStringGenerator}
import skinny.validator.{Validator, _}

case class CreateDataSetRequest(
                                 name: String,
                                 description: String,
                                 dataSourceId: Int,
                                 sql: String,
                                 fields: List[CreateDataSetFieldRequest]
                                 ) extends Verifiable with RandomStringGenerator with DataSetConfiguration {

  def validationRules = Validator(
    param("name" -> name) is notEmpty,
    param("dataSourceId" -> dataSourceId) is checkAll(intValue, intMinValue(0)),
    param("sql" -> sql) is notEmpty,
    param("fields" -> fields) is checkAll(notNull, notEmpty)
  )

  override def hasErrors = super.hasErrors || (fields != null && fields.hasErrors)

  override def errorMessages: List[String] = {
    val fieldsErrorMessage: List[String] = if (fields != null) fields.errorMessages else List[String]()
    super.errorMessages ::: fieldsErrorMessage
  }

  def toCreateDataSet(): CreateDataSet = {
    val tableName = s"${prefix}_${randomString(tableNameLength)}"
    CreateDataSet(
      name,
      description,
      dataSourceId,
      sql,
      tableName,
      for (fieldWithIndex <- fields.zipWithIndex) yield fieldWithIndex._1.toField(fieldWithIndex._2)
    )
  }
}

case class CreateDataSetFieldRequest(
                                      aliasName: String,
                                      fieldName: String,
                                      fieldType: String,
                                      dataClassName: String,
                                      length: Int,
                                      scale: Int
                                      ) extends Verifiable{
  def validationRules = Validator(
    param("aliasName" -> aliasName) is notEmpty,
    param("fieldName" -> fieldName) is notEmpty,
    param("fieldType" -> fieldType) is notEmpty,
    param("dataClassName" -> dataClassName) is notEmpty,
    param("length" -> length) is checkAll(intValue, intMinValue(1))
  )

  def toField(index: Int): Field = {
    val codeName = s"c${index}"
    Field(codeName, aliasName, fieldName, fieldType, dataClassName, length, scale)
  }
}
