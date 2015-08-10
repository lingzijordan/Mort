package com.bigeyedata.mort.requests.datasource

import com.bigeyedata.mort.message.datasource.CreateDataSourceRequest
import org.scalatest.{FlatSpec, Matchers}

class DataSourceRequestSpec extends FlatSpec with Matchers{

  it should "throw IllegalArgumentException when without data source name" in {
    intercept[IllegalArgumentException] {
      CreateDataSourceRequest(-1, "", "rdb", "This is a test data source", Map.empty)
    }
  }

  it should "throw IllegalArgumentException when data source name less than 3 chars" in {
    intercept[IllegalArgumentException] {
      CreateDataSourceRequest(-1, "te", "rdb", "This is a test data source", Map.empty)
    }
  }

  it should "throw IllegalArgumentException when data source name more than 20 chars" in {
    intercept[IllegalArgumentException] {
      CreateDataSourceRequest(-1, "ThisIsALongDataSourceNameTest", "rdb", "This is a test data source", Map.empty)
    }
  }

  it should "throw IllegalArgumentException when data source type is empty" in {
    intercept[IllegalArgumentException] {
      CreateDataSourceRequest(-1, "datasource", "", "This is a test data source", Map.empty)
    }
  }

  it should "throw IllegalArgumentException when options is empty" in {
    intercept[IllegalArgumentException] {
      CreateDataSourceRequest(-1, "datasource", "rdb", "This is a test data source", Map.empty)
    }
  }
}
