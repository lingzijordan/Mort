package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class View(
  id: Int,
  name: String,
  description: String,
  viewType: String,
  generatedQuery: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  createdBy: String,
  updatedBy: String,
  reportId: Int) {

  def save()(implicit session: DBSession = View.autoSession): View = View.save(this)(session)

  def destroy()(implicit session: DBSession = View.autoSession): Unit = View.destroy(this)(session)

}


object View extends SQLSyntaxSupport[View] {

  override val tableName = "bigeye_views"

  override val columns = Seq("id", "name", "description", "view_type", "generated_query", "created_at", "updated_at", "created_by", "updated_by", "report_id")

  def apply(v: SyntaxProvider[View])(rs: WrappedResultSet): View = apply(v.resultName)(rs)
  def apply(v: ResultName[View])(rs: WrappedResultSet): View = new View(
    id = rs.get(v.id),
    name = rs.get(v.name),
    description = rs.get(v.description),
    viewType = rs.get(v.viewType),
    generatedQuery = rs.get(v.generatedQuery),
    createdAt = rs.get(v.createdAt),
    updatedAt = rs.get(v.updatedAt),
    createdBy = rs.get(v.createdBy),
    updatedBy = rs.get(v.updatedBy),
    reportId = rs.get(v.reportId)
  )

  val v = View.syntax("v")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[View] = {
    withSQL {
      select.from(View as v).where.eq(v.id, id)
    }.map(View(v.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[View] = {
    withSQL(select.from(View as v)).map(View(v.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(View as v)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[View] = {
    withSQL {
      select.from(View as v).where.append(where)
    }.map(View(v.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[View] = {
    withSQL {
      select.from(View as v).where.append(where)
    }.map(View(v.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(View as v).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: String,
    viewType: String,
    generatedQuery: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    createdBy: String,
    updatedBy: String,
    reportId: Int)(implicit session: DBSession = autoSession): View = {
    val generatedKey = withSQL {
      insert.into(View).columns(
        column.name,
        column.description,
        column.viewType,
        column.generatedQuery,
        column.createdAt,
        column.updatedAt,
        column.createdBy,
        column.updatedBy,
        column.reportId
      ).values(
        name,
        description,
        viewType,
        generatedQuery,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy,
        reportId
      )
    }.updateAndReturnGeneratedKey.apply()

    View(
      id = generatedKey.toInt,
      name = name,
      description = description,
      viewType = viewType,
      generatedQuery = generatedQuery,
      createdAt = createdAt,
      updatedAt = updatedAt,
      createdBy = createdBy,
      updatedBy = updatedBy,
      reportId = reportId)
  }

  def save(entity: View)(implicit session: DBSession = autoSession): View = {
    withSQL {
      update(View).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.viewType -> entity.viewType,
        column.generatedQuery -> entity.generatedQuery,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.createdBy -> entity.createdBy,
        column.updatedBy -> entity.updatedBy,
        column.reportId -> entity.reportId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: View)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(View).where.eq(column.id, entity.id) }.update.apply()
  }

}
