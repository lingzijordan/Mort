/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset

import java.util.function.BiConsumer

import com.typesafe.config.{ConfigFactory, ConfigObject, ConfigValue}

import scala.collection.mutable.ListBuffer

trait DataSetConfiguration {
  val prefix = ConfigFactory.load().getString("data-set.prefix")
  val fieldLength = ConfigFactory.load().getInt("data-set.field-length")
  val tableNameLength = ConfigFactory.load().getInt("data-set.table-name-length")
  val persistanceType = ConfigFactory.load().getString("data-set.persistance-type")
  val sparkConfig = ConfigFactory.load().getObject("data-set.spark-config")

  implicit def configObject2Traversable(configObject: ConfigObject): List[(String, String)] = {
    var listBuffer = ListBuffer[(String, String)]()
    configObject.forEach(new BiConsumer[String, ConfigValue] {
      override def accept(key: String, value: ConfigValue): Unit =
        listBuffer.+=((key.replaceAll("-", "."), value.unwrapped.asInstanceOf[String]))
    })

    listBuffer.toList
  }

}