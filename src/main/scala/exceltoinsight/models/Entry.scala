package exceltoinsight.models

import java.util.Date

class Entry (
              val entryId: Integer,
              val date: Date,
              val client: String,
              val product: String,
              val category: String,
              val profit: Double,
              val region: String
            )