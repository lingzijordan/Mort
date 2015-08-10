package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._

case class DashboardView(
  dashboardId: Int,
  viewId: Int,
  viewOrder: Int) {

  def save()(implicit session: DBSession = DashboardView.autoSession): DashboardView = DashboardView.save(this)(session)

  def destroy()(implicit session: DBSession = DashboardView.autoSession): Unit = DashboardView.destroy(this)(session)

}


object DashboardView extends SQLSyntaxSupport[DashboardView] {

  override val tableName = "bigeye_join_dashboard_view"

  override val columns = Seq("dashboard_id", "view_id", "view_order")

  def apply(dv: SyntaxProvider[DashboardView])(rs: WrappedResultSet): DashboardView = apply(dv.resultName)(rs)
  def apply(dv: ResultName[DashboardView])(rs: WrappedResultSet): DashboardView = new DashboardView(
    dashboardId = rs.get(dv.dashboardId),
    viewId = rs.get(dv.viewId),
    viewOrder = rs.get(dv.viewOrder)
  )

  val dv = DashboardView.syntax("dv")

  override val autoSession = AutoSession

  def find(dashboardId: Int, viewId: Int)(implicit session: DBSession = autoSession): Option[DashboardView] = {
    withSQL {
      select.from(DashboardView as dv).where.eq(dv.dashboardId, dashboardId).and.eq(dv.viewId, viewId)
    }.map(DashboardView(dv.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DashboardView] = {
    withSQL(select.from(DashboardView as dv)).map(DashboardView(dv.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DashboardView as dv)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DashboardView] = {
    withSQL {
      select.from(DashboardView as dv).where.append(where)
    }.map(DashboardView(dv.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DashboardView] = {
    withSQL {
      select.from(DashboardView as dv).where.append(where)
    }.map(DashboardView(dv.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DashboardView as dv).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    dashboardId: Int,
    viewId: Int,
    viewOrder: Int)(implicit session: DBSession = autoSession): DashboardView = {
    withSQL {
      insert.into(DashboardView).columns(
        column.dashboardId,
        column.viewId,
        column.viewOrder
      ).values(
        dashboardId,
        viewId,
        viewOrder
      )
    }.update.apply()

    DashboardView(
      dashboardId = dashboardId,
      viewId = viewId,
      viewOrder = viewOrder)
  }

  def save(entity: DashboardView)(implicit session: DBSession = autoSession): DashboardView = {
    withSQL {
      update(DashboardView).set(
        column.dashboardId -> entity.dashboardId,
        column.viewId -> entity.viewId,
        column.viewOrder -> entity.viewOrder
      ).where.eq(column.dashboardId, entity.dashboardId).and.eq(column.viewId, entity.viewId)
    }.update.apply()
    entity
  }

  def destroy(entity: DashboardView)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DashboardView).where.eq(column.dashboardId, entity.dashboardId).and.eq(column.viewId, entity.viewId) }.update.apply()
  }

}
