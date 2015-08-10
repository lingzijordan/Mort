package com.bigeyedata.mort.commons

/* *\
** **
** __ __ _________ _____ ©Mort BI **
** | \/ / () | () |_ _| (c) 2015 **
** |_|\/|_\____|_|\_\ |_| http://www.bigeyedata.com **
** **
\* */
trait MapVerifiable {

  def validateFor(implicit options: Map[String, String]): Unit

  implicit class MapToValidator(key: String)(implicit options: Map[String, String]) {

    import com.bigeyedata.mort.commons.implicits.StringImplicits._

    def required = {
      require(options.contains(key), key.isRequired)
      this
    }

    def nonEmpty = {
      require(!options(key).isEmpty, key.shouldNotBeEmpty)
      this
    }
  }

}
