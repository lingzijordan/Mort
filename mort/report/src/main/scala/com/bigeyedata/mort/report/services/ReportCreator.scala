package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.infrastructure.metadata.services.{DataSetService, ReportService}
import com.bigeyedata.mort.report.ReportMessage.CreateReport

trait ReportCreator extends ReportService with DataSetService {
  def createReport(createReport: CreateReport): PrimaryKey = {
    if(hasNotExistDataSet(createReport.dataSetId))
      throw BadRequestException(s"Data Set ${createReport.dataSetId} does not exist")
    if(hasExistReportName(createReport.name))
      throw BadRequestException(s"Report name [${createReport.name}] exist")
    create(createReport)
  }
}
