/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset.services

import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.AnalyzeData
import com.bigeyedata.mort.dataset.FieldMessages._
import com.bigeyedata.mort.infrastructure.metadata.implicities.FieldsImplicits._
import com.bigeyedata.mort.dataset.sqlgenerator.Sql
import com.bigeyedata.mort.infrastructure.metadata.services.{DataSetService, FieldService}
import com.bigeyedata.mort.sqlmaterial.{Category, Metric}

trait QueryStatementGenerator extends DataSetService with  FieldService {

  def dataSetTableNameFrom(analysisData: AnalyzeData) :String = fetchDataSet(analysisData.dataSetId).map(_.tableName).getOrElse("")

  def generate(analysisData: AnalyzeData): String = {

    def createMetricFields(metric:Metrics)(fun:Int=>String):List[Metric] = {
      metric.map{ field =>
        Metric(fun(field.fieldId),field.operation)
      }
    }

    val tableName  = tableNameBy(analysisData.dataSetId)
    val fields     = fetchFieldsBy(analysisData.dataSetId)

    if (tableName.isEmpty) throw new Exception("table name is not exists")
    if (fields.isEmpty)    throw new Exception(s"dataSet ${tableName} don't have fields")

    val metric     = createMetricFields(analysisData.metrics){ fieldId => fields.codeNameFor(fieldId) }
    val categories = analysisData.categories.map { f => Category(fields.codeNameFor(f.fieldId)) }
    val allFields  = metric ::: categories

    Sql.select(allFields).from(tableName).groupBy(allFields).toSql
  }


}
