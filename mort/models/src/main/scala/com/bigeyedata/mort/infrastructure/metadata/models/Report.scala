package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class Report(
  id: Int,
  name: String,
  description: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  createdBy: String,
  updatedBy: String,
  dataSetId: Int) {

  def save()(implicit session: DBSession = Report.autoSession): Report = Report.save(this)(session)

  def destroy()(implicit session: DBSession = Report.autoSession): Unit = Report.destroy(this)(session)

}


object Report extends SQLSyntaxSupport[Report] {

  override val tableName = "bigeye_reports"

  override val columns = Seq("id", "name", "description", "created_at", "updated_at", "created_by", "updated_by", "data_set_id")

  def apply(r: SyntaxProvider[Report])(rs: WrappedResultSet): Report = apply(r.resultName)(rs)
  def apply(r: ResultName[Report])(rs: WrappedResultSet): Report = new Report(
    id = rs.get(r.id),
    name = rs.get(r.name),
    description = rs.get(r.description),
    createdAt = rs.get(r.createdAt),
    updatedAt = rs.get(r.updatedAt),
    createdBy = rs.get(r.createdBy),
    updatedBy = rs.get(r.updatedBy),
    dataSetId = rs.get(r.dataSetId)
  )

  val r = Report.syntax("r")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Report] = {
    withSQL {
      select.from(Report as r).where.eq(r.id, id)
    }.map(Report(r.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Report] = {
    withSQL(select.from(Report as r)).map(Report(r.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Report as r)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Report] = {
    withSQL {
      select.from(Report as r).where.append(where)
    }.map(Report(r.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Report] = {
    withSQL {
      select.from(Report as r).where.append(where)
    }.map(Report(r.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Report as r).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    createdBy: String,
    updatedBy: String,
    dataSetId: Int)(implicit session: DBSession = autoSession): Report = {
    val generatedKey = withSQL {
      insert.into(Report).columns(
        column.name,
        column.description,
        column.createdAt,
        column.updatedAt,
        column.createdBy,
        column.updatedBy,
        column.dataSetId
      ).values(
        name,
        description,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy,
        dataSetId
      )
    }.updateAndReturnGeneratedKey.apply()

    Report(
      id = generatedKey.toInt,
      name = name,
      description = description,
      createdAt = createdAt,
      updatedAt = updatedAt,
      createdBy = createdBy,
      updatedBy = updatedBy,
      dataSetId = dataSetId)
  }

  def save(entity: Report)(implicit session: DBSession = autoSession): Report = {
    withSQL {
      update(Report).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.createdBy -> entity.createdBy,
        column.updatedBy -> entity.updatedBy,
        column.dataSetId -> entity.dataSetId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Report)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Report).where.eq(column.id, entity.id) }.update.apply()
  }

}
