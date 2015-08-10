/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.Types._
import com.bigeyedata.mort.commons.enum.FieldType._
import com.bigeyedata.mort.infrastructure.metadata.models.View._
import com.bigeyedata.mort.infrastructure.metadata.models.{View, ViewField, ViewWithReport}
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction.withTransaction
import com.bigeyedata.mort.report.ViewMessage.{CreateView, CreateViewField}
import org.joda.time.DateTime
import scalikejdbc._

trait ViewService {
  def create(view: CreateView): PrimaryKey =
    withTransaction() { session =>
    val viewId = View.create(view.name, view.description, view.viewType.toString, view.generatedQuery,
      //todo: use real user in the future
      DateTime.now(), DateTime.now(), "admin", "admin",
      view.reportId)(session).id
    createViewField(viewId, view.viewField)(session)
    viewId
  }

  def hasExistViewName(reportId: Int, name: String): Boolean = {
    View.countBy(sqls.eq(View.v.name, name).and.eq(View.v.reportId, reportId)) > 0
  }

  private def createViewField(viewId: PrimaryKey, viewField: CreateViewField)(implicit session: DBSession): Unit = {
    viewField.metrics.zipWithIndex.foreach { m =>
      val (metric, index) = m
      ViewField.create(viewId, metric.fieldId, index, Metric, Some(metric.operation))
    }
    viewField.categories.zipWithIndex.foreach { c =>
      val (category, index) = c
      ViewField.create(viewId, category.fieldId, index, Category, None)
    }
  }

  def fetchByReportId(reportId: ID): List[View] = {
    View.findAllBy(sqls.eq(v.reportId, reportId))
  }

  def fetch(viewId: ID): Option[View] = {
    View.find(viewId)
  }

  def fetchViewWithReports(): List[ViewWithReport] = ViewWithReport.findAll()

  implicit def fieldTypeToString(fieldType: FieldType): String = fieldType.toString

}
