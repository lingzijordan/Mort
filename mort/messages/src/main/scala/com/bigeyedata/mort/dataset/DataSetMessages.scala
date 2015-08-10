/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset

object DataSetMessages {

  case class CreateDataSet(
                            name: String,
                            description: String,
                            dataSourceId: Int,
                            sql: String,
                            tableName: String,
                            fields: List[Field]
                            )

  case class Field(
                    codeName: String,
                    aliasName: String,
                    fieldName: String,
                    fieldType: String,
                    dataClassName: String,
                    length: Int,
                    scale: Int)

  case class ImportDataSet(dataSetId: Int)

  case class SqlStatement(statement: String)

  case class DataSource(databaseType: String,
                        host: String,
                        port: Int,
                        database: String,
                        username: String,
                        password: String)



}
