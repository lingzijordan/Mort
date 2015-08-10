package com.bigeyedata.customerdata.common.model

import com.bigeyedata.customerdata.domainservice.db.common.UnitSpec

class DataFrameSpec extends UnitSpec {
  val id = Column("id", "Int", 39, 2)
  val company = Column("company", "VARCHAR", 50, 2)
  val address = Column("address", "VARCHAR", 250, 2)
  
  val schema = List(id, company, address)
  
  val dataFrame = DataFrame(schema, Row("1000", "bigeye", "shenzhen city"), Row("1001", "suoxinda", "shenzhen city"))

  "dataframe" should "convert to fields part of insert statement" in {
    dataFrame.fieldsPart should be("id,company,address")
  }

  "dataframe" should "convert to values part of insert statement" in {
    dataFrame.valuesPart should be("('1000','bigeye','shenzhen city'),('1001','suoxinda','shenzhen city')")
  }
}
