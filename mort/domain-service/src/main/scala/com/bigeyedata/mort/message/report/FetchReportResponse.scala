package com.bigeyedata.mort.message.report

case class FetchReportResponse(
                                id: Int,
                                name: String,
                                description: String,
                                createdBy: String,
                                createdAt: String
                                )
