/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.commons.implicits

import org.joda.time.DateTime

object DateTimeImplicits {

  implicit def dateTimeToString(dateTime: DateTime) = {
    dateTime.toString("yyyy-MM-dd")
  }

}
