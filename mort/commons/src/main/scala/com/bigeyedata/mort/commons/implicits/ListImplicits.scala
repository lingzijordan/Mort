/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.commons.implicits

import com.bigeyedata.mort.commons.Verifiable

object ListImplicits {
  implicit class ListImplicit[A <: Verifiable](val value: List[A]) {
    def hasErrors = value.exists(_.hasErrors)

    def errorMessages: List[String] = value.flatMap(_.errorMessages)
  }
}
