/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset

import com.bigeyedata.mort.commons.enum.FieldType._
import com.bigeyedata.mort.dataset.FieldMessages._
import com.bigeyedata.mort.infrastructure.metadata.models.ViewField

object FieldMessages {

  type Metrics = List[MetricUnit]
  type Categories = List[CategoryUnit]
  type Columns = List[Column]
  type Rows = Array[Row]
  type Row = Array[String]

  case class CategoryUnit(fieldId: Int)

  case class MetricUnit(fieldId: Int, operation: String)

  case class Column(id: Int, codeName: String, aliasName: String, operation: String, fieldType: String)

}

object ViewFieldImplicits {

  implicit def viewFieldToMetricsAndCategories(viewFields: List[ViewField]): (Metrics, Categories) = {

    val categories: Categories = viewFields
      .filter(_.fieldType == Category.toString)
      .sortWith(_.fieldOrder < _.fieldOrder)
      .map(viewField => CategoryUnit(viewField.fieldId))

    val metrics: Metrics = viewFields
      .filter(_.fieldType == Metric.toString)
      .sortWith(_.fieldOrder < _.fieldOrder)
      .map(viewField => MetricUnit(viewField.fieldId, viewField.operation.get))

    (metrics, categories)
  }
}