/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dbdataset.implicits

import java.time.LocalTime
import java.time.temporal.ChronoUnit

import com.bigeyedata.mort.analysisdata.AnalysisResultMessage._
import com.bigeyedata.mort.dataset.FieldMessages._
import com.bigeyedata.mort.infrastructure.metadata.implicities.FieldsImplicits._
import com.bigeyedata.mort.infrastructure.metadata.models.Field
import com.bigeyedata.mort.infrastructure.metadata.services.DataSetService
import org.apache.spark.Logging
import org.apache.spark.sql.DataFrame

object DataFrameImplicits {

  implicit class AnalysisResultWrapper(dataFrame: DataFrame) extends DataSetService with Logging {

    def toAnalysisResult(analyzeData: AnalyzeData): AnalysisResult = {

      def composeCounterHeader: List[Column] = {
        var extraHeader: List[Column] = Nil
        if (dataFrame.columns.exists(_.equalsIgnoreCase("counter")))
          extraHeader = Column(0, "counter", "*", "count", "Metric") :: extraHeader
        extraHeader
      }

      def composeStructure[T](analyzeData: AnalyzeData)(fun: (List[(Int, String)]) => T): T = {
        fun(analyzeData.idAndOperation)
      }

      def composeColumn(id: Int, op: String, fields: List[Field]): Column = {
        val operation = if (op.isEmpty) "" else s"${op}"
        val field = fields.fieldById(id)
        val fieldType = field.fieldType
        val aliasName = field.aliasName
        val codeName = field.codeName
        Column(id, codeName, aliasName, operation, fieldType)
      }

      def composeRows: Array[Row] = {
        val columnSize = dataFrame.columns.length
        val start = LocalTime.now()
        val rs = dataFrame.collect().map { singleRow =>
          (0 until columnSize).map(colIndex => singleRow(colIndex).toString).toArray
        }
        val end = LocalTime.now()
        logDebug("fetch row from dataFrame cost time is:" + ChronoUnit.MILLIS.between(start, end))
        rs
      }

      def composeHeader: List[Column] = {
        val commonHeader = composeStructure[Columns](analyzeData) { list =>
          val fields = fetchFieldsBy(analyzeData.dataSetId)
          list.map { tmp =>
            composeColumn(tmp._1, tmp._2, fields)
          }
        }
        commonHeader ::: composeCounterHeader
      }

      AnalysisResult(composeHeader, composeRows)
    }
  }

}
