/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.sqlgenerator

import com.bigeyedata.mort.dataset.sqlgenerator.Sql
import Sql._
import com.bigeyedata.mort.sqlmaterial.{Category, Metric}
import org.scalatest.{FlatSpec, ShouldMatchers}

class SqlSpec extends FlatSpec with ShouldMatchers {

  "sql expression" should "evaluate select clause for all metrics" in {
    val sql = select(allMetric).from(tableName).groupBy(allMetric).toSql
    sql should be(s"select sum(price) as sum_price,avg(salary) as avg_salary from ${tableName} ")
  }

  "sql expression" should "evaluate select clause for all categories" in {
    val sql = select(allCategory).from(tableName).groupBy(allCategory).toSql
    sql should be(s"select name,address,count(*) as counter from ${tableName} group by name,address")
  }

  "sql expression" should "evaluate select clause for metrics and categories" in {
    val sql = select(metricsAndCategories).from(tableName).groupBy(metricsAndCategories)
    sql.toSql should be(s"select sum(price) as sum_price,avg(salary) as avg_salary,name,address from ${tableName} group by name,address")
  }

  val tableName = "tab_xx"
  val allMetric = List(Metric("price", "sum"), Metric("salary", "avg"))
  val allCategory = List(Category("name"), Category("address"))
  val metricsAndCategories = List(Metric("price", "sum"), Metric("salary", "avg"), Category("name"), Category("address"))

}
