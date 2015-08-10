package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class DataSet(
  id: Int,
  name: String,
  description: String,
  status: Int,
  queryStatement: String,
  executionPlan: String,
  tableName: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  createdBy: String,
  updatedBy: String,
  dataSourceId: Int) {

  def save()(implicit session: DBSession = DataSet.autoSession): DataSet = DataSet.save(this)(session)

  def destroy()(implicit session: DBSession = DataSet.autoSession): Unit = DataSet.destroy(this)(session)

}


object DataSet extends SQLSyntaxSupport[DataSet] {

  override val tableName = "bigeye_data_sets"

  override val columns = Seq("id", "name", "description", "status", "query_statement", "execution_plan", "table_name", "created_at", "updated_at", "created_by", "updated_by", "data_source_id")

  def apply(ds: SyntaxProvider[DataSet])(rs: WrappedResultSet): DataSet = apply(ds.resultName)(rs)
  def apply(ds: ResultName[DataSet])(rs: WrappedResultSet): DataSet = new DataSet(
    id = rs.get(ds.id),
    name = rs.get(ds.name),
    description = rs.get(ds.description),
    status = rs.get(ds.status),
    queryStatement = rs.get(ds.queryStatement),
    executionPlan = rs.get(ds.executionPlan),
    tableName = rs.get(ds.tableName),
    createdAt = rs.get(ds.createdAt),
    updatedAt = rs.get(ds.updatedAt),
    createdBy = rs.get(ds.createdBy),
    updatedBy = rs.get(ds.updatedBy),
    dataSourceId = rs.get(ds.dataSourceId)
  )

  val ds = DataSet.syntax("ds")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DataSet] = {
    withSQL {
      select.from(DataSet as ds).where.eq(ds.id, id)
    }.map(DataSet(ds.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DataSet] = {
    withSQL(select.from(DataSet as ds)).map(DataSet(ds.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DataSet as ds)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DataSet] = {
    withSQL {
      select.from(DataSet as ds).where.append(where)
    }.map(DataSet(ds.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DataSet] = {
    withSQL {
      select.from(DataSet as ds).where.append(where)
    }.map(DataSet(ds.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DataSet as ds).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: String,
    status: Int,
    queryStatement: String,
    executionPlan: String,
    tableName: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    createdBy: String,
    updatedBy: String,
    dataSourceId: Int)(implicit session: DBSession = autoSession): DataSet = {
    val generatedKey = withSQL {
      insert.into(DataSet).columns(
        column.name,
        column.description,
        column.status,
        column.queryStatement,
        column.executionPlan,
        column.tableName,
        column.createdAt,
        column.updatedAt,
        column.createdBy,
        column.updatedBy,
        column.dataSourceId
      ).values(
        name,
        description,
        status,
        queryStatement,
        executionPlan,
        tableName,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy,
        dataSourceId
      )
    }.updateAndReturnGeneratedKey.apply()

    DataSet(
      id = generatedKey.toInt,
      name = name,
      description = description,
      status = status,
      queryStatement = queryStatement,
      executionPlan = executionPlan,
      tableName = tableName,
      createdAt = createdAt,
      updatedAt = updatedAt,
      createdBy = createdBy,
      updatedBy = updatedBy,
      dataSourceId = dataSourceId)
  }

  def save(entity: DataSet)(implicit session: DBSession = autoSession): DataSet = {
    withSQL {
      update(DataSet).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.status -> entity.status,
        column.queryStatement -> entity.queryStatement,
        column.executionPlan -> entity.executionPlan,
        column.tableName -> entity.tableName,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.createdBy -> entity.createdBy,
        column.updatedBy -> entity.updatedBy,
        column.dataSourceId -> entity.dataSourceId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DataSet)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DataSet).where.eq(column.id, entity.id) }.update.apply()
  }

}
