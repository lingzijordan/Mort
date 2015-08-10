/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */

package com.bigeyedata.customerdata.domainservice.db.core

import com.bigeyedata.customerdata.domainservice.db.router._

trait AggregateServiceEntryRouter extends PreviewServiceRouter
                                  with FetchFieldsServiceRouter
                                  with ImportServiceRouter
                                  with CheckConnectionRouter
                                  with HealthRouter {
  def entryRoute = pathPrefix("cdata") {
    previewRoute ~ fieldsRoute ~ importRoute ~ checkRoute ~ healthRoute
  }
}
