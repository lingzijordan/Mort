package com.bigeyedata.mort.message.mapping

import com.bigeyedata.mort.commons.implicits.DateTimeImplicits._
import com.bigeyedata.mort.infrastructure.metadata.models.{View, Report}
import com.bigeyedata.mort.message.report.{FetchReportDetailResponse, FetchReportResponse}

object ReportMapper {
  implicit class FetchReportsResponseMapper(reports: List[Report]) {
    def response = {
      reports.map {report =>
        FetchReportResponse(
          report.id,
          report.name,
          report.description,
          report.createdBy,
          report.createdAt
        )
      }
    }
  }
  
  implicit class FetchReportResponseMapper(reportWithViews: (Report, List[View])) {
    def response = {
      FetchReportDetailResponse(reportWithViews._1, reportWithViews._2)
    }
  }
}
