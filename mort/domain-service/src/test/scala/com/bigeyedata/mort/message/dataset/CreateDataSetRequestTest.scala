/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.dataset

import org.scalatest.{Matchers, FlatSpec}
import skinny.validator.{Errors, Messages, Validator}

class CreateDataSetRequestTest extends FlatSpec with Matchers {
  val createDataSetFieldRequest = CreateDataSetFieldRequest("alias", "fieldName", "Category", "java.lang.String", 10, 0)

  "CreateDataSetRequest" should "not give an empty name" in {
    val request = CreateDataSetRequest("", "", 1, "sql", List(createDataSetFieldRequest))

    request.hasErrors should be(true)
    request.errorMessages.head should be("name is required")
  }

  it should "not give an empty sql" in {
    val request = CreateDataSetRequest("ds1", "", 1, "", List(createDataSetFieldRequest))

    request.hasErrors should be(true)
    request.errorMessages.head should be("sql is required")
  }

  it should "give an integer data source id and must bigger than 0" in {
    val request = CreateDataSetRequest("ds1", "", -1, "sql", List(createDataSetFieldRequest))

    request.hasErrors should be(true)
    request.errorMessages.head should be("dataSourceId must be greater than or equal to 0")
  }

  "fields" should "not be null" in {
    val request = CreateDataSetRequest("ds1", "", 1, "sql", null)

    request.hasErrors should be(true)
    request.errorMessages.head should be("fields is required")
  }

  "fields" should "not be empty" in {
    val request = CreateDataSetRequest("ds1", "", 1, "sql", List.empty)

    request.hasErrors should be(true)
    request.errorMessages.head should be("fields is required")
  }

  "field in fields" should "not be empty" in {
    val errorField = CreateDataSetFieldRequest("", "", "", "", 0, 0)
    val request = CreateDataSetRequest("ds1", "", 1, "sql", List(errorField))

    request.hasErrors should be(true)
    request.errorMessages.head should be("aliasName is required")
  }
}
