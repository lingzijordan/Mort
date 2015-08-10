/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.dataset

import scala.util.Random

trait RandomStringGenerator {

  def randomString(length: Int) =
    s"${System.currentTimeMillis().toHexString}${Random.alphanumeric.take(length).mkString}"
}
