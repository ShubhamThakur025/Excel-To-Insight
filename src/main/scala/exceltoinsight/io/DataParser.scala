package exceltoinsight.io

import exceltoinsight.models.{Entry, SalesOfficer}

import java.text.SimpleDateFormat

object DataParser {
  private val format = new SimpleDateFormat("MM/dd/yy")
  def getDataFromProfitsSheet(dataRepository: Map[String, List[Array[String]]] ): List[Entry] = {
    dataRepository.get(System.getenv("PROFIT_SHEET")) match
      case Some(rows) =>
        rows.map(cell => Entry(cell(0).toInt, format.parse(cell(1)), cell(2), cell(3), cell(4), cell(5).toDouble, cell(6)))
      case None => List.empty
  }

  def getDataFromSalesOfficersSheet(dataRepository: Map[String, List[Array[String]]]): List[SalesOfficer] = {
    dataRepository.get(System.getenv("SALES_OFFICERS_SHEET")) match
      case Some(rows) =>
        rows.map(cell => SalesOfficer(cell(0), cell(1)))
      case None => List.empty
  }
}