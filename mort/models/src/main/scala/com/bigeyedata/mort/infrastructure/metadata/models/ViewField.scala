package com.bigeyedata.mort.infrastructure.metadata.models

import scalikejdbc._

case class ViewField(
  viewId: Int,
  fieldId: Int,
  fieldOrder: Int,
  fieldType: String,
  operation: Option[String] = None) {

  def save()(implicit session: DBSession = ViewField.autoSession): ViewField = ViewField.save(this)(session)

  def destroy()(implicit session: DBSession = ViewField.autoSession): Unit = ViewField.destroy(this)(session)

}


object ViewField extends SQLSyntaxSupport[ViewField] {

  override val tableName = "bigeye_join_view_field"

  override val columns = Seq("view_id", "field_id", "field_order", "field_type", "operation")

  def apply(vf: SyntaxProvider[ViewField])(rs: WrappedResultSet): ViewField = apply(vf.resultName)(rs)
  def apply(vf: ResultName[ViewField])(rs: WrappedResultSet): ViewField = new ViewField(
    viewId = rs.get(vf.viewId),
    fieldId = rs.get(vf.fieldId),
    fieldOrder = rs.get(vf.fieldOrder),
    fieldType = rs.get(vf.fieldType),
    operation = rs.get(vf.operation)
  )

  val vf = ViewField.syntax("vf")

  override val autoSession = AutoSession

  def find(fieldOrder: Int, fieldType: String, viewId: Int)(implicit session: DBSession = autoSession): Option[ViewField] = {
    withSQL {
      select.from(ViewField as vf).where.eq(vf.fieldOrder, fieldOrder).and.eq(vf.fieldType, fieldType).and.eq(vf.viewId, viewId)
    }.map(ViewField(vf.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[ViewField] = {
    withSQL(select.from(ViewField as vf)).map(ViewField(vf.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(ViewField as vf)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[ViewField] = {
    withSQL {
      select.from(ViewField as vf).where.append(where)
    }.map(ViewField(vf.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[ViewField] = {
    withSQL {
      select.from(ViewField as vf).where.append(where)
    }.map(ViewField(vf.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(ViewField as vf).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    viewId: Int,
    fieldId: Int,
    fieldOrder: Int,
    fieldType: String,
    operation: Option[String] = None)(implicit session: DBSession = autoSession): ViewField = {
    withSQL {
      insert.into(ViewField).columns(
        column.viewId,
        column.fieldId,
        column.fieldOrder,
        column.fieldType,
        column.operation
      ).values(
        viewId,
        fieldId,
        fieldOrder,
        fieldType,
        operation
      )
    }.update.apply()

    ViewField(
      viewId = viewId,
      fieldId = fieldId,
      fieldOrder = fieldOrder,
      fieldType = fieldType,
      operation = operation)
  }

  def save(entity: ViewField)(implicit session: DBSession = autoSession): ViewField = {
    withSQL {
      update(ViewField).set(
        column.viewId -> entity.viewId,
        column.fieldId -> entity.fieldId,
        column.fieldOrder -> entity.fieldOrder,
        column.fieldType -> entity.fieldType,
        column.operation -> entity.operation
      ).where.eq(column.fieldOrder, entity.fieldOrder).and.eq(column.fieldType, entity.fieldType).and.eq(column.viewId, entity.viewId)
    }.update.apply()
    entity
  }

  def destroy(entity: ViewField)(implicit session: DBSession = autoSession): Unit = {
    withSQL { delete.from(ViewField).where.eq(column.fieldOrder, entity.fieldOrder).and.eq(column.fieldType, entity.fieldType).and.eq(column.viewId, entity.viewId) }.update.apply()
  }

}
