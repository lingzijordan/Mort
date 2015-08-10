package com.bigeyedata.mort.infrastructure.metadata.models

import org.joda.time.DateTime
import scalikejdbc._

case class Dashboard(
  id: Int,
  name: String,
  description: Option[String] = None,
  createdAt: DateTime,
  updatedAt: Option[DateTime] = None,
  createdBy: String,
  updatedBy: String) {

  def save()(implicit session: DBSession = Dashboard.autoSession): Dashboard = Dashboard.save(this)(session)

  def destroy()(implicit session: DBSession = Dashboard.autoSession): Unit = Dashboard.destroy(this)(session)

}


object Dashboard extends SQLSyntaxSupport[Dashboard] {

  override val tableName = "bigeye_dashboards"

  override val columns = Seq("id", "name", "description", "created_at", "updated_at", "created_by", "updated_by")

  def apply(d: SyntaxProvider[Dashboard])(rs: WrappedResultSet): Dashboard = apply(d.resultName)(rs)
  def apply(d: ResultName[Dashboard])(rs: WrappedResultSet): Dashboard = new Dashboard(
    id = rs.get(d.id),
    name = rs.get(d.name),
    description = rs.get(d.description),
    createdAt = rs.get(d.createdAt),
    updatedAt = rs.get(d.updatedAt),
    createdBy = rs.get(d.createdBy),
    updatedBy = rs.get(d.updatedBy)
  )

  val d = Dashboard.syntax("d")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Dashboard] = {
    withSQL {
      select.from(Dashboard as d).where.eq(d.id, id)
    }.map(Dashboard(d.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Dashboard] = {
    withSQL(select.from(Dashboard as d)).map(Dashboard(d.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Dashboard as d)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Dashboard] = {
    withSQL {
      select.from(Dashboard as d).where.append(where)
    }.map(Dashboard(d.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Dashboard] = {
    withSQL {
      select.from(Dashboard as d).where.append(where)
    }.map(Dashboard(d.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Dashboard as d).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: Option[String] = None,
    createdAt: DateTime,
    updatedAt: Option[DateTime] = None,
    createdBy: String,
    updatedBy: String)(implicit session: DBSession = autoSession): Dashboard = {
    val generatedKey = withSQL {
      insert.into(Dashboard).columns(
        column.name,
        column.description,
        column.createdAt,
        column.updatedAt,
        column.createdBy,
        column.updatedBy
      ).values(
        name,
        description,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy
      )
    }.updateAndReturnGeneratedKey.apply()

    Dashboard(
      id = generatedKey.toInt,
      name = name,
      description = description,
      createdAt = createdAt,
      updatedAt = updatedAt,
      createdBy = createdBy,
      updatedBy = updatedBy)
  }

  def save(entity: Dashboard)(implicit session: DBSession = autoSession): Dashboard = {
    withSQL {
      update(Dashboard).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.createdBy -> entity.createdBy,
        column.updatedBy -> entity.updatedBy
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Dashboard)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Dashboard).where.eq(column.id, entity.id) }.update.apply()
  }

}
