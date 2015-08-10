/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.sqlgenerator

import com.bigeyedata.mort.dataset.sqlgenerator.Group
import com.bigeyedata.mort.sqlmaterial.{Category, Metric}
import org.scalatest._

class GroupSpec extends FlatSpec with ShouldMatchers {
  "group expression" should "evaluate group clause for all metrics" in {
    val group = Group(Metric("price", "sum"), Metric("salary", "avg"))
    group.evaluate should be("")
  }

  "group expression" should "evaluate group clause for all categories" in {
    val group = Group(Category("name"), Category("address"))
    group.evaluate should be("group by name,address")
  }

  "group expression" should "evaluate group clause for metrics and categories" in {
    val group = Group(Metric("price", "sum"), Metric("salary", "avg"), Category("name"), Category("address"))
    group.evaluate should be("group by name,address")
  }

}
