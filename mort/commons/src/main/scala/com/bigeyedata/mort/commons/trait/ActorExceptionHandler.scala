/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.commons

import akka.actor.{ActorNotFound, Actor}
import com.bigeyedata.mort.commons.exceptions.BadRequestException
import com.bigeyedata.mort.commons.messages.Messages
import Messages.ExecutionFailed


trait ActorExceptionHandler {
  self: Actor =>

  override def receive: Receive = {
    case any: Any =>
      try {
        mortReceive(any)
      } catch {
        case notFound: ActorNotFound =>
          sender ! ExecutionFailed(BadRequestException("invalid parameters", notFound), "invalid parameters")
        case e: Exception =>
          sender ! ExecutionFailed(e, e.getMessage)
      }
  }

  def mortReceive: Receive

}
