/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.infrastructure.metadata.models

import org.joda.time.DateTime
import scalikejdbc._

case class ViewWithReport(
                           id: Int,
                           name: String,
                           description: String,
                           createdAt: DateTime,
                           createdBy: String,
                           reportId: Int,
                           reportName: String
                           )

object ViewWithReport {
  def apply(rs: WrappedResultSet) =
    new ViewWithReport(
      rs.int("id"),
      rs.string("name"),
      rs.string("description"),
      rs.jodaDateTime("created_at"),
      rs.string("created_by"),
      rs.int("report_id"),
      rs.string("report_name")
    )

  val autoSession = AutoSession

  def findAll()(implicit session: DBSession = autoSession): List[ViewWithReport] =
    sql"select t1.*, t2.name as report_name from bigeye_views t1 left join bigeye_reports t2 on t1.report_id = t2.id"
    .map(rs => ViewWithReport(rs))
    .list.apply()
}
