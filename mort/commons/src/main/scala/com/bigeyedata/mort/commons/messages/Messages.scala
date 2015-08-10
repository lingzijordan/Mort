/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.commons.messages

object Messages {

  trait ExecutionResult

  case class ExecutionSuccess(result: Any) extends ExecutionResult

  case class ExecutionFailed(result: Any, errorMessage: String) extends ExecutionResult

}
