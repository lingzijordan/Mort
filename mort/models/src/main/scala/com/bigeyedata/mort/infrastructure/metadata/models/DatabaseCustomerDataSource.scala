package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._

case class DatabaseCustomerDataSource(
  id: Int,
  host: String,
  port: Int,
  databaseName: String,
  userName: String,
  password: String,
  databaseType: String) {

  def save()(implicit session: DBSession = DatabaseCustomerDataSource.autoSession): DatabaseCustomerDataSource = DatabaseCustomerDataSource.save(this)(session)

  def destroy()(implicit session: DBSession = DatabaseCustomerDataSource.autoSession): Unit = DatabaseCustomerDataSource.destroy(this)(session)

}


object DatabaseCustomerDataSource extends SQLSyntaxSupport[DatabaseCustomerDataSource] {

  override val tableName = "bigeye_rdb_data_sources"

  override val columns = Seq("id", "host", "port", "database_name", "user_name", "password", "database_type")

  def apply(dcds: SyntaxProvider[DatabaseCustomerDataSource])(rs: WrappedResultSet): DatabaseCustomerDataSource = apply(dcds.resultName)(rs)
  def apply(dcds: ResultName[DatabaseCustomerDataSource])(rs: WrappedResultSet): DatabaseCustomerDataSource = new DatabaseCustomerDataSource(
    id = rs.get(dcds.id),
    host = rs.get(dcds.host),
    port = rs.get(dcds.port),
    databaseName = rs.get(dcds.databaseName),
    userName = rs.get(dcds.userName),
    password = rs.get(dcds.password),
    databaseType = rs.get(dcds.databaseType)
  )

  val dcds = DatabaseCustomerDataSource.syntax("dcds")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[DatabaseCustomerDataSource] = {
    withSQL {
      select.from(DatabaseCustomerDataSource as dcds).where.eq(dcds.id, id)
    }.map(DatabaseCustomerDataSource(dcds.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[DatabaseCustomerDataSource] = {
    withSQL(select.from(DatabaseCustomerDataSource as dcds)).map(DatabaseCustomerDataSource(dcds.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(DatabaseCustomerDataSource as dcds)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[DatabaseCustomerDataSource] = {
    withSQL {
      select.from(DatabaseCustomerDataSource as dcds).where.append(where)
    }.map(DatabaseCustomerDataSource(dcds.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[DatabaseCustomerDataSource] = {
    withSQL {
      select.from(DatabaseCustomerDataSource as dcds).where.append(where)
    }.map(DatabaseCustomerDataSource(dcds.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(DatabaseCustomerDataSource as dcds).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    host: String,
    port: Int,
    databaseName: String,
    userName: String,
    password: String,
    databaseType: String)(implicit session: DBSession = autoSession): DatabaseCustomerDataSource = {
    withSQL {
      insert.into(DatabaseCustomerDataSource).columns(
        column.id,
        column.host,
        column.port,
        column.databaseName,
        column.userName,
        column.password,
        column.databaseType
      ).values(
        id,
        host,
        port,
        databaseName,
        userName,
        password,
        databaseType
      )
    }.update.apply()

    DatabaseCustomerDataSource(
      id = id,
      host = host,
      port = port,
      databaseName = databaseName,
      userName = userName,
      password = password,
      databaseType = databaseType)
  }

  def save(entity: DatabaseCustomerDataSource)(implicit session: DBSession = autoSession): DatabaseCustomerDataSource = {
    withSQL {
      update(DatabaseCustomerDataSource).set(
        column.id -> entity.id,
        column.host -> entity.host,
        column.port -> entity.port,
        column.databaseName -> entity.databaseName,
        column.userName -> entity.userName,
        column.password -> entity.password,
        column.databaseType -> entity.databaseType
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: DatabaseCustomerDataSource)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(DatabaseCustomerDataSource).where.eq(column.id, entity.id) }.update.apply()
  }

}
