/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.commons.implicits

import java.text.SimpleDateFormat

import com.bigeyedata.mort.commons.enum.ViewType
import org.json4s.ext.EnumNameSerializer
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport

object MortJsonSupport extends Json4sSupport {
  
  object mortFormat extends DefaultFormats {
    override protected def dateFormatter: SimpleDateFormat = {
      val f = new SimpleDateFormat("yyyy-MM-dd")
      f.setTimeZone(DefaultFormats.UTC)
      f
    }
  }

  implicit lazy val json4sFormats: Formats = mortFormat ++ org.json4s.ext.JodaTimeSerializers.all + new EnumNameSerializer(ViewType)
}
