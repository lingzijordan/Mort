package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.Types.{ID, PrimaryKey}
import com.bigeyedata.mort.infrastructure.metadata.models.Report
import com.bigeyedata.mort.report.ReportMessage.CreateReport
import org.joda.time.DateTime
import scalikejdbc._


trait ReportService {
  def create(report: CreateReport): PrimaryKey = {
    Report.create(
      report.name,
      report.description,
      DateTime.now(),
      DateTime.now(),
      "admin",
      "admin",
      report.dataSetId).id
  }

  def dataSetIdWith(reportId: Int): Option[Int] = {
    Report.find(reportId).map(_.dataSetId)
  }

  def hasNotExistReport(id: Int): Boolean = {
    Report.find(id).isEmpty
  }

  def hasExistReportName(name: String): Boolean = {
    Report.countBy(sqls.eq(Report.r.name, name)) > 0
  }

  def fetch(): List[Report] = {
    Report.findAll()
  }

  def fetchReportById(id: ID): Option[Report] = {
    Report.find(id)
  }
}
