/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.view

import com.bigeyedata.mort.analysisdata.AnalysisResultMessage.AnalyzeData
import com.bigeyedata.mort.commons.Verifiable
import com.bigeyedata.mort.commons.enum.ViewType.ViewType
import com.bigeyedata.mort.dataset.FieldMessages.{Categories, Metrics}
import com.bigeyedata.mort.dataset.services.QueryStatementGenerator
import com.bigeyedata.mort.report.ViewMessage.{CreateView, CreateViewField}
import com.bigeyedata.mort.report.services.DataSetIdRetriever
import skinny.validator._

case class CreateViewRequest(reportId: Int,
                             name: String,
                             description: String,
                             viewType: ViewType,
                             metrics: Metrics,
                             categories: Categories)
  extends Verifiable with QueryStatementGenerator with DataSetIdRetriever {
  override def validationRules: Validator = Validator(
    param("reportId" -> reportId) is intMinValue(0),
    param("name" -> name) is notEmpty,
    param("viewType" -> viewType) is notNull
  )

  def toCreateView: CreateView = {
    val viewField = CreateViewField(metrics, categories)
    val analysisData = AnalyzeData(retrieveDataSetId(reportId), metrics, categories)
    CreateView(reportId, name, description, viewType, generate(analysisData), viewField)
  }
}