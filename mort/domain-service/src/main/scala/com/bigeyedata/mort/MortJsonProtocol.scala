package com.bigeyedata.mort

import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sSupport

object JsonImplicits extends Json4sSupport {
  implicit lazy val json4sFormats: Formats = DefaultFormats
}




