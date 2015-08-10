/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.common.model

import com.bigeyedata.customerdata.common.model.FieldType.FieldType

object FieldType extends Enumeration {
  type FieldType = Value

  val Metric, Category, TimeCategory = Value
}

case class Field(name: String, aliasName: String, dataClassName: String, fieldType: FieldType, length: Int, scale: Int)
