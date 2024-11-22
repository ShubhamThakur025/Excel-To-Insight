package exceltoinsight.io.input

import scala.jdk.CollectionConverters._
import org.apache.poi.ss.usermodel.{DataFormatter, Row, Workbook, WorkbookFactory}
import java.io.File

object FileReader {
  private val filePath = System.getenv("FILE_PATH")
  private val workbook: Workbook = WorkbookFactory.create(new File(filePath))
  private val formatter: DataFormatter = new DataFormatter()

  def readDataFromFile(): Map[String, List[Array[String]]] = {
    (0 until workbook.getNumberOfSheets).map { i =>
      val sheet = workbook.getSheetAt(i)
      val rows = sheet.iterator().asScala.drop(2).map(row => extractDataFromRow(row)).toList
      val sheetName = sheet.getSheetName
      sheetName -> rows
    }.toMap
  }

  private def extractDataFromRow(row: Row) = row.iterator().asScala.map(cell => formatter.formatCellValue(cell)).toArray
}


