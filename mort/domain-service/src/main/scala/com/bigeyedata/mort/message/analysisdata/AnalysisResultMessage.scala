/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.analysisdata

import com.bigeyedata.mort.dataset.FieldMessages.{Rows, Columns, Categories, Metrics}

object AnalysisResultMessage {

  case class DataAnalysisResponse(fields: Columns, rows: Rows)
  case class DataAnalysisRequest(reportId: Int, metrics: Metrics, categories: Categories)

}
