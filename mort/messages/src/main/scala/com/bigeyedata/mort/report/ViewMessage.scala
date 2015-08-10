/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.report

import com.bigeyedata.mort.commons.enum.ViewType.ViewType
import com.bigeyedata.mort.dataset.FieldMessages.{Categories, Metrics}

object ViewMessage {
  case class CreateView(reportId: Int, name: String, description: String, viewType: ViewType, generatedQuery: String, viewField: CreateViewField)

  case class CreateViewField(metrics: Metrics, categories: Categories)
}
