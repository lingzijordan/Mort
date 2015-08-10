/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.datawriter.service

class ProgressTracker {
  var trackingHistory:Map[String, (Long, String)] = Map()

  def update(datasetId: String, insertedRowCount: Long):Unit = {
    trackingHistory.get(datasetId) match {
      case None => trackingHistory += datasetId -> (insertedRowCount, "ongoing")
      case Some((count, status)) => {
        //todo
      }
    }
  }

}
