package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types.ID
import com.bigeyedata.mort.infrastructure.metadata.models.Report
import com.bigeyedata.mort.infrastructure.metadata.services.ReportService

trait ReportsFetcher extends ReportService {
  def fetchReports(): List[Report] = {
    fetch()
  }

  def fetchReport(id: ID): Option[Report] = {
    fetchReportById(id)
  }
}
