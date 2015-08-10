/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.view

import com.bigeyedata.mort.infrastructure.metadata.models.View
import com.bigeyedata.mort.message.analysisdata.AnalysisResultMessage.DataAnalysisResponse

case class FetchViewWithDataResponse(view: View, data: DataAnalysisResponse)
