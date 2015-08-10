/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset

import com.bigeyedata.mort.commons.enum.FieldType
import com.bigeyedata.mort.dataset.FieldMessages.{CategoryUnit, MetricUnit}
import com.bigeyedata.mort.dataset.ViewFieldImplicits.viewFieldToMetricsAndCategories
import com.bigeyedata.mort.infrastructure.metadata.models.ViewField
import org.scalatest.{FlatSpec, LoneElement, Matchers}

class ViewFieldImplicitsSpec extends FlatSpec with Matchers with LoneElement{

  val ViewID = 10

  it should "get empty Metrics and Categories" in {
    val viewFields: List[ViewField] = List()

    val (metrics, categories) = viewFieldToMetricsAndCategories(viewFields)

    metrics should have size 0
    categories should have size 0
  }

  it should "get single element of Metrics and empty Categories" in {
    val viewFields: List[ViewField] = List(ViewField(ViewID, 1, 2, FieldType.Metric.toString, Some("sum")))

    val (metrics, categories) = viewFieldToMetricsAndCategories(viewFields)

    metrics.loneElement should be (MetricUnit(1, "sum"))
    categories should have size 0
  }

  it should "get single element of Categories and empty Metrics" in {
    val viewFields: List[ViewField] = List(ViewField(ViewID, 1, 2, FieldType.Category.toString, None))

    val (metrics, categories) = viewFieldToMetricsAndCategories(viewFields)

    categories.loneElement should be (CategoryUnit(1))
    metrics should have size 0
  }

  it should "get sorted Categories and sorted Metrics" in {
    val viewFields: List[ViewField] = List(
      ViewField(ViewID, 24, 4, FieldType.Metric.toString, Some("sum")),
      ViewField(ViewID, 1, 3, FieldType.Category.toString, None),
      ViewField(ViewID, 41, 2, FieldType.Category.toString, None),
      ViewField(ViewID, 51, 5, FieldType.Category.toString, None),
      ViewField(ViewID, 11, 1, FieldType.Metric.toString, Some("count"))
    )

    val (metrics, categories) = viewFieldToMetricsAndCategories(viewFields)

    metrics should be (List(MetricUnit(11, "count"), MetricUnit(24, "sum")))
    categories should be (List(CategoryUnit(41), CategoryUnit(1), CategoryUnit(51)))
  }

}
