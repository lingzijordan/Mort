package com.bigeyedata.mort.datasource.service

import java.lang.reflect.Field

import akka.actor.Actor
import com.bigeyedata.mort.commons.MapVerifiable
import com.bigeyedata.mort.commons.Types.PrimaryKey
import com.bigeyedata.mort.commons.exceptions.ResourceNotExistException
import com.bigeyedata.mort.commons.messages.Messages.ExecutionFailed
import com.bigeyedata.mort.datasource.CreateDataSourceMessages.{CreateSpecificDataSource, CreatedSpecificDataSourceSuccess, ValidateFailed}
import com.bigeyedata.mort.datasource.domain._
import com.bigeyedata.mort.datasource.{DataSourceFetched, FetchSpecificDataSource}
import com.bigeyedata.mort.infrastructure.metadata.models.DatabaseCustomerDataSource
import com.bigeyedata.mort.infrastructure.metadata.services.DatabaseCustomerDataSourceService
import com.bigeyedata.mort.infrastructure.metadata.transaction.Transaction

class DatabaseCustomerDataSourceActor extends Actor with DatabaseCustomerDataSourceService {

  import com.bigeyedata.mort.datasource.service.DatabaseOptionsImplicits._

  override def receive: Receive = {
    case CreateSpecificDataSource(id: PrimaryKey, options: Map[String, String], transaction: Transaction) =>
      val (invalid, invalidMessage) = options.isInvalid()
      if (invalid) {
        sender ! ValidateFailed(invalidMessage, transaction)
      } else {
        createDatabaseDataSource(id, options, transaction)
        sender ! CreatedSpecificDataSourceSuccess(id, transaction)
      }
    case FetchSpecificDataSource(dataSource) =>
      fetchDatabaseDataSource(dataSource.id) match {
        case Some(ds) =>
          ds.options
          sender ! DataSourceFetched(dataSource, ds.options)
        case None => sender ! ExecutionFailed(ResourceNotExistException(s"dataSourceId:${dataSource.id} not exists"), s"dataSourceId:${dataSource.id} not exists")
      }
  }

  def createDatabaseDataSource(id: PrimaryKey, options: Map[String, String], transaction: Transaction) {
    createDatabaseDataSource(
      id,
      options.host,
      options.port,
      options.database,
      options.username,
      options.password,
      options.databaseType
    )(transaction)
  }
}

object DatabaseDataSourceOptionsValidator extends MapVerifiable {

  import com.bigeyedata.mort.commons.implicits.StringImplicits._

  override def validateFor(implicit options: Map[String, String]): Unit = {
    "databaseType".required.nonEmpty
    "host".required.nonEmpty
    "database".required.nonEmpty
    "username".required.nonEmpty

    if (options.contains("port") && options("port").length > 0) require(options("port").forall(_.isDigit), "port".mustBeNumber)
    if (Database.isNotSupported(options("databaseType"))) throw new IllegalArgumentException("database is not supported by Bigeye now!")
  }
}

object DatabaseOptionsImplicits {

  implicit class DatabaseCustomerDataSourceImplict(databaseCustomerDataSource: DatabaseCustomerDataSource) {
    def options: Map[String, Any] = {
      val fields: Array[Field] = databaseCustomerDataSource.getClass.getDeclaredFields
      (Map[String, Any]() /: fields) { (options, field) =>
        field.setAccessible(true)
        options + (field.getName -> field.get(databaseCustomerDataSource))
      }
    }
  }

  implicit class MapImplicit(options: Map[String, String]) {
    var databaseTypeKey: String = "databaseType"
    val hostKey: String = "host"
    val portKey: String = "port"
    val databaseKey: String = "database"
    val usernameKey: String = "username"
    val passwordKey: String = "password"

    def databaseType = options(databaseTypeKey)

    def host = options(hostKey)

    def port = if (options.contains(portKey) && options(portKey).length > 0) options(portKey).toInt else Database.database(databaseType).defaultPort

    def database = options(databaseKey)

    def username = options(usernameKey)

    def password = if (options.contains(passwordKey)) options(passwordKey) else ""

    def isInvalid(): (Boolean, String) = {
      try {
        DatabaseDataSourceOptionsValidator.validateFor(options)
        (false, "Validate")
      } catch {
        case e: IllegalArgumentException => (true, e.getMessage)
      }
    }

  }

}
