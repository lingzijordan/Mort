package com.bigeyedata.mort.report

object ReportMessage {

  case class CreateReport(dataSetId: Int, name: String, description: String)

}
