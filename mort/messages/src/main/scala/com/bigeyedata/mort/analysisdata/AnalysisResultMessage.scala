/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.analysisdata

import com.bigeyedata.mort.dataset.FieldMessages._

object AnalysisResultMessage {

  type DataSetId = Int

  case class AnalysisResult(fields: Columns, rows: Rows)

  case class AnalyzeData(dataSetId: Int, metrics: Metrics, categories: Categories) {

    def idAndOperation: List[(Int, String)] = metrics.map {
      metric => (metric.fieldId, metric.operation)
    } :::
      categories.map { category => (category.fieldId, "") }
  }

  case class AnalysisExecutionContext(tableName: String, sql: String, analyzeData: AnalyzeData)

}
