package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._
import org.joda.time.{DateTime}

case class CustomerDataSource(
  id: Int,
  name: String,
  description: Option[String] = None,
  dataSourceType: String,
  createdAt: DateTime,
  updatedAt: DateTime,
  createdBy: String,
  updatedBy: String) {

  def save()(implicit session: DBSession = CustomerDataSource.autoSession): CustomerDataSource = CustomerDataSource.save(this)(session)

  def destroy()(implicit session: DBSession = CustomerDataSource.autoSession): Unit = CustomerDataSource.destroy(this)(session)

}


object CustomerDataSource extends SQLSyntaxSupport[CustomerDataSource] {

  override val tableName = "bigeye_data_sources"

  override val columns = Seq("id", "name", "description", "data_source_type", "created_at", "updated_at", "created_by", "updated_by")

  def apply(cds: SyntaxProvider[CustomerDataSource])(rs: WrappedResultSet): CustomerDataSource = apply(cds.resultName)(rs)
  def apply(cds: ResultName[CustomerDataSource])(rs: WrappedResultSet): CustomerDataSource = new CustomerDataSource(
    id = rs.get(cds.id),
    name = rs.get(cds.name),
    description = rs.get(cds.description),
    dataSourceType = rs.get(cds.dataSourceType),
    createdAt = rs.get(cds.createdAt),
    updatedAt = rs.get(cds.updatedAt),
    createdBy = rs.get(cds.createdBy),
    updatedBy = rs.get(cds.updatedBy)
  )

  val cds = CustomerDataSource.syntax("cds")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[CustomerDataSource] = {
    withSQL {
      select.from(CustomerDataSource as cds).where.eq(cds.id, id)
    }.map(CustomerDataSource(cds.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[CustomerDataSource] = {
    withSQL(select.from(CustomerDataSource as cds)).map(CustomerDataSource(cds.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(CustomerDataSource as cds)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[CustomerDataSource] = {
    withSQL {
      select.from(CustomerDataSource as cds).where.append(where)
    }.map(CustomerDataSource(cds.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[CustomerDataSource] = {
    withSQL {
      select.from(CustomerDataSource as cds).where.append(where)
    }.map(CustomerDataSource(cds.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(CustomerDataSource as cds).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    name: String,
    description: Option[String] = None,
    dataSourceType: String,
    createdAt: DateTime,
    updatedAt: DateTime,
    createdBy: String,
    updatedBy: String)(implicit session: DBSession = autoSession): CustomerDataSource = {
    val generatedKey = withSQL {
      insert.into(CustomerDataSource).columns(
        column.name,
        column.description,
        column.dataSourceType,
        column.createdAt,
        column.updatedAt,
        column.createdBy,
        column.updatedBy
      ).values(
        name,
        description,
        dataSourceType,
        createdAt,
        updatedAt,
        createdBy,
        updatedBy
      )
    }.updateAndReturnGeneratedKey.apply()

    CustomerDataSource(
      id = generatedKey.toInt,
      name = name,
      description = description,
      dataSourceType = dataSourceType,
      createdAt = createdAt,
      updatedAt = updatedAt,
      createdBy = createdBy,
      updatedBy = updatedBy)
  }

  def save(entity: CustomerDataSource)(implicit session: DBSession = autoSession): CustomerDataSource = {
    withSQL {
      update(CustomerDataSource).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.description -> entity.description,
        column.dataSourceType -> entity.dataSourceType,
        column.createdAt -> entity.createdAt,
        column.updatedAt -> entity.updatedAt,
        column.createdBy -> entity.createdBy,
        column.updatedBy -> entity.updatedBy
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: CustomerDataSource)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(CustomerDataSource).where.eq(column.id, entity.id) }.update.apply()
  }

}
