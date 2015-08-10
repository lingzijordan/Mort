/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.sqlgenerator

import com.bigeyedata.mort.dataset.sqlgenerator.Select
import com.bigeyedata.mort.sqlmaterial.{Category, Metric}
import org.scalatest.{ShouldMatchers, FlatSpec}

class SelectSpec extends FlatSpec with ShouldMatchers {
  "select expression" should "evaluate select clause for all metrics" in {
    val select = Select(Metric("price", "sum"), Metric("salary", "avg"))
    select.evaluate should be("select sum(price) as sum_price,avg(salary) as avg_salary")
  }

  "select expression" should "evaluate select clause for all categories" in {
    val select = Select(Category("name"), Category("address"))
    select.evaluate should be("select name,address,count(*) as counter")
  }

  "select expression" should "evaluate select clause for metrics and categories" in {
    val select = Select(Metric("price", "sum"), Metric("salary", "avg"), Category("name"), Category("address"))
    select.evaluate should be("select sum(price) as sum_price,avg(salary) as avg_salary,name,address")
  }
}
