/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.services

import com.bigeyedata.mort.commons.ImportDataSetStatus
import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.dataset.DataSetMessages.CreateDataSet
import com.bigeyedata.mort.infrastructure.metadata.models.DataSet._
import com.bigeyedata.mort.infrastructure.metadata.models.{DataSet, Field}
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction.withTransaction
import org.joda.time.DateTime
import scalikejdbc._

trait DataSetService {
  def create(dataSet: CreateDataSet): PrimaryKey = {
    withTransaction() { session =>
      val dataSetId = DataSet.create(
        dataSet.name,
        dataSet.description,
        ImportDataSetStatus.PENDING.id,
        dataSet.sql,
        "",
        dataSet.tableName,
        DateTime.now(), DateTime.now(), "admin", "admin",
        dataSet.dataSourceId
      )(session).id


      dataSet.fields.foreach { field =>
        Field.create(
          field.codeName,
          field.aliasName,
          field.fieldName,
          field.fieldType,
          field.dataClassName,
          field.length,
          field.scale,
          dataSetId
        )(session)
      }
      dataSetId
    }
  }

  def hasExistDataSet(name: String): Boolean = {
    DataSet.countBy(sqls.eq(ds.name, name)) > 0
  }

  def hasNotExistDataSet(id: Int): Boolean = {
    DataSet.find(id).isEmpty
  }

  def tableNameBy(dataSetId: Int): String = fetchDataSet(dataSetId).map(_.tableName).getOrElse("")

  def fetchDataSet(dataSetId: Int): Option[DataSet] = DataSet.find(dataSetId)

  def fetchFieldsBy(dataSetId: Int): List[Field] = {
    DB localTx{
      implicit  session =>
        sql"""select id as id,
             |code_name as codeName,
             |alias_name as aliasName,
             |field_name as fieldName,
             |field_type as fieldType,
             |data_class_type as dataClassType,
             |field_length as fieldLength,
             |scale,
             |data_set_id as dataSetId
             |from bigeye_fields
             |where data_set_id= ${dataSetId}""".stripMargin.map(rs=>Field(rs.int("id"),
          rs.string("codeName"),
          rs.string("aliasName"),rs.string("fieldName"),rs.string("fieldType"),
          rs.string("dataClassType"),rs.int("fieldLength"),rs.int("scale"),rs.int("dataSetId"))).list().apply()
    }
  }

  def hasNotExistField(dataSetId: Int): Boolean = {
    fetchFieldsBy(dataSetId).isEmpty
  }

  def changeStatus(dataSetId: Int, status: Int): Unit = {
    DB localTx {
      implicit session =>
        sql"update bigeye_data_sets set status = ${status} where id = ${dataSetId}".update.apply()
    }
  }

  def fetchDataSetsBy(dataSourceId: Int): List[DataSet] = {
    DataSet.findAllBy(sqls.eq(DataSet.ds.dataSourceId, dataSourceId))
  }

}
