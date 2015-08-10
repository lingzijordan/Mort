/*                                                                      *\
**                                                                      **
**      __  __ _________ _____          ©Mort BI                        **
**     |  \/  / () | () |_   _|         (c) 2015                        **
**     |_|\/|_\____|_|\_\ |_|           http://www.bigeyedata.com       **
**                                                                      **
\*                                                                      */
package com.bigeyedata.mort.message.dataset

case class FetchFieldResponse(metrics: List[FieldResponse],
                              categories: List[FieldResponse],
                              timeCategories: List[FieldResponse])

case class FieldResponse(id: Int, codeName: String, aliasName: String, fieldType: String, dataClassName: String)
