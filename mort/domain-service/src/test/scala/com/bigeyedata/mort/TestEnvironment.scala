/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort

import com.bigeyedata.mort.hooks.InitHook

object TestEnvironment extends ActorSystemProvider with InitHook {

  var actorInitialized = false

  def guranteeActorSystemAvailable: Unit = {
    if(!actorInitialized) {
      init(mortActorSystem)
      actorInitialized = true
    }
  }

}
