
/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.dashboard.DashboardMessages.CreateDashboard
import com.bigeyedata.mort.infrastructure.metadata.models.{View, Dashboard, DashboardView}
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction._
import org.joda.time.DateTime
import scalikejdbc._

trait DashboardService {
  def hasExistDashboard(name: String): Boolean = {
    Dashboard.countBy(sqls.eq(Dashboard.d.name, name)) > 0
  }

  def create(createDashboard: CreateDashboard): PrimaryKey = {
    withTransaction() { session =>
      val dashboardId = Dashboard.create(
        createDashboard.name,
        createDashboard.description,
        DateTime.now(), Option(DateTime.now()), "admin", "admin")(session).id

      createDashboard.views.zipWithIndex.foreach { viewIdWithOrder =>
        val (viewId, order) = viewIdWithOrder
        DashboardView.create(dashboardId, viewId, order)
      }
      dashboardId
    }
  }

  def fetchDashboards: List[Dashboard] = Dashboard.findAll()

  def fetchDashboard(dashboardId: Int): Dashboard = Dashboard.find(dashboardId).get

  def fetchViewsByDashboardId(dashboardId: Int): List[View] = {

    if(!existsDashboard(dashboardId)){
      throw ResourceNotExistException(s"dashboard ${dashboardId} does not exist")
    }
    DB localTx { implicit session =>
      sql"""
           |select id,
           |name,
           |description,
           |view_type  as  viewType,
           |generated_query,
           |created_at as  createdAt,
           |updated_at as  updatedAt,
           |created_by as  createdBy,
           |updated_by as  updatedBy,
           |report_id  as  reportId
           |from bigeye_views
           |where exists(
           |   select 1
           |   from (select view_id
           |   from bigeye_join_dashboard_view
           |   where dashboard_id = ${dashboardId}) tab_01
                                                    |   where bigeye_views.id = tab_01.view_id
                                                    |)
         """.stripMargin.map { rs =>
        View(
          rs.int("id"),
          rs.string("name"),
          rs.string("description"),
          rs.string("viewType"),
          rs.string("generated_query"),
          rs.jodaDateTime("createdAt"),
          rs.jodaDateTime("updatedAt"),
          rs.string("createdBy"),
          rs.string("updatedBy"),
          rs.int("reportId")
        )
      }.list().apply()
    }
  }

  private[this] def existsDashboard(dashboardId: Int): Boolean = {
    Dashboard.find(dashboardId) match {
      case Some(dashboard) => true
      case None => false
    }
  }

}
