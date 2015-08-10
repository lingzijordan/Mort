package com.bigeyedata.mort.routes

import akka.actor.Actor
import spray.http.MediaTypes.`text/html`

trait MortRouter
  extends MortExceptionHandler
  with DataSourceRouter
  with DataSetRouter
  with AnalysisResultRouter
  with ReportRouter
  with ViewRouter
  with DashboardRouter
  with HealthRouter {

  self: Actor =>

  def rootRoute = {
    dataSourceRoute ~ dataSetRoute ~ analysisResultRoute ~ reportRoute ~ viewRoute ~ dashboardRoute ~ healthRoute ~ pathSingleSlash {
      get {
        respondWithMediaType(`text/html`) {
          complete {
            """
              <!DOCTYPE html>
              <html>
                <head>
                  <title>mort</title>
                </head>
                <body>
                  <div id="app"></div>
                  <script src="/node/main.bundle.js"></script>
                </body>
              </html>
            """
          }
        }
      }
    }
  }
}
