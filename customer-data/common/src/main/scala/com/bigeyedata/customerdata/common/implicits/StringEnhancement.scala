/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.common.implicits

object StringEnhancement {
  implicit class RichString(str: String) {
    def dropLeftEndWith(ch: Char):String = {
      str.drop(str.lastIndexOf(ch) + 1)
    }
  }
}

