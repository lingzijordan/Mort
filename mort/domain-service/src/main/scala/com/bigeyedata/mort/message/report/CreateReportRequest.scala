package com.bigeyedata.mort.message.report

import com.bigeyedata.mort.commons.Verifiable
import com.bigeyedata.mort.report.ReportMessage.CreateReport
import skinny.validator.{Validator, _}


case class CreateReportRequest(dataSetId: Int, name: String, description: String) extends Verifiable {
  def toCreateReport() = CreateReport(dataSetId, name, description)

  override def validationRules: Validator = Validator(
    param("dataSetId" -> dataSetId) is intMinValue(0),
    param("name" -> name) is notEmpty
  )
}
