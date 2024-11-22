package exceltoinsight.models

class Entry (
            val entryId: Integer,
            val date: String,
            val client: String,
            val product: String,
            val category: String,
            val profit: Double,
            val region: String
            )