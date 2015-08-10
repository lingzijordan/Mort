/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report.services

import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.infrastructure.metadata.services.ReportService

trait DataSetIdRetriever extends ReportService {
  type ReportID = Int
  type DataSetID = Int

  def retrieveDataSetId(reportId: ReportID): DataSetID = {
    dataSetIdWith(reportId) match {
      case Some(dsId) => dsId
      case _ => throw BadRequestException(s"Data Set related to Report ${reportId} does not exist")
    }
  }
}
