/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.dashboard

import com.bigeyedata.mort.commons.Verifiable
import com.bigeyedata.mort.dashboard.DashboardMessages.CreateDashboard
import skinny.validator._

case class CreateDashboardRequest(
                                   name: String,
                                   description: Option[String] = None,
                                   views: List[Int]
                                   ) extends Verifiable {
  override def validationRules = Validator(
    param("name" -> name) is notEmpty,
    param("views" -> views) is minLength(1)
  )

  def toCreateDashboard() = CreateDashboard(name, description, views)
}


case class DashboardRecord(id: Int,
                           name: String,
                           description: String,
                           created_at: String,
                           updated_at: String,
                           created_by: String,
                           updated_by: String)

case class ViewDetail(id: Int,
                      name: String,
                      desc: String,
                      viewType: String,
                      sql: String,
                      createdAt: String,
                      updatedAt: String,
                      createdBy: String,
                      updatedBy: String)

case class DashboardDetailResponse(
                                    id: Int,
                                    name: String,
                                    description: String,
                                    createdAt: String,
                                    updatedAt: String,
                                    createdBy: String,
                                    updatedBy: String,
                                    views: List[ViewDetail]
                                    )
