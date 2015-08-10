package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._

case class Field(
  id: Int,
  codeName: String,
  aliasName: String,
  fieldName: String,
  fieldType: String,
  dataClassType: String,
  fieldLength: Int,
  scale: Int,
  dataSetId: Int) {

  def save()(implicit session: DBSession = Field.autoSession): Field = Field.save(this)(session)

  def destroy()(implicit session: DBSession = Field.autoSession): Unit = Field.destroy(this)(session)

}


object Field extends SQLSyntaxSupport[Field] {

  override val tableName = "bigeye_fields"

  override val columns = Seq("id", "code_name", "alias_name", "field_name", "field_type", "data_class_type", "field_length", "scale", "data_set_id")

  def apply(f: SyntaxProvider[Field])(rs: WrappedResultSet): Field = apply(f.resultName)(rs)
  def apply(f: ResultName[Field])(rs: WrappedResultSet): Field = new Field(
    id = rs.get(f.id),
    codeName = rs.get(f.codeName),
    aliasName = rs.get(f.aliasName),
    fieldName = rs.get(f.fieldName),
    fieldType = rs.get(f.fieldType),
    dataClassType = rs.get(f.dataClassType),
    fieldLength = rs.get(f.fieldLength),
    scale = rs.get(f.scale),
    dataSetId = rs.get(f.dataSetId)
  )

  val f = Field.syntax("f")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[Field] = {
    withSQL {
      select.from(Field as f).where.eq(f.id, id)
    }.map(Field(f.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[Field] = {
    withSQL(select.from(Field as f)).map(Field(f.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(Field as f)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[Field] = {
    withSQL {
      select.from(Field as f).where.append(where)
    }.map(Field(f.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[Field] = {
    withSQL {
      select.from(Field as f).where.append(where)
    }.map(Field(f.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(Field as f).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    codeName: String,
    aliasName: String,
    fieldName: String,
    fieldType: String,
    dataClassType: String,
    fieldLength: Int,
    scale: Int,
    dataSetId: Int)(implicit session: DBSession = autoSession): Field = {
    val generatedKey = withSQL {
      insert.into(Field).columns(
        column.codeName,
        column.aliasName,
        column.fieldName,
        column.fieldType,
        column.dataClassType,
        column.fieldLength,
        column.scale,
        column.dataSetId
      ).values(
        codeName,
        aliasName,
        fieldName,
        fieldType,
        dataClassType,
        fieldLength,
        scale,
        dataSetId
      )
    }.updateAndReturnGeneratedKey.apply()

    Field(
      id = generatedKey.toInt,
      codeName = codeName,
      aliasName = aliasName,
      fieldName = fieldName,
      fieldType = fieldType,
      dataClassType = dataClassType,
      fieldLength = fieldLength,
      scale = scale,
      dataSetId = dataSetId)
  }

  def save(entity: Field)(implicit session: DBSession = autoSession): Field = {
    withSQL {
      update(Field).set(
        column.id -> entity.id,
        column.codeName -> entity.codeName,
        column.aliasName -> entity.aliasName,
        column.fieldName -> entity.fieldName,
        column.fieldType -> entity.fieldType,
        column.dataClassType -> entity.dataClassType,
        column.fieldLength -> entity.fieldLength,
        column.scale -> entity.scale,
        column.dataSetId -> entity.dataSetId
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: Field)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(Field).where.eq(column.id, entity.id) }.update.apply()
  }

}
