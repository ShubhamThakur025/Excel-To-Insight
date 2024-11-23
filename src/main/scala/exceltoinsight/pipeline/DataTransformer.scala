package exceltoinsight.pipeline

import exceltoinsight.models.{Entry, SalesOfficer}
import java.util
import java.util.Date
import scala.collection.immutable
import scala.collection.immutable.TreeMap

object DataTransformer {

  private def getSalesOfficerMap(salesOfficerData: List[SalesOfficer]): Map[String, String] = {
    salesOfficerData.map(row => row.region -> row.salesOfficer).toMap
  }

  def getProfitsProductsMap(profitsData: List[Entry]): Map[String, Double] = {
    profitsData.groupBy(_.product).map{
      case (product, entries) => product -> entries.map(_.profit).sum
    }
  }
  def getCategoriesProductsMap(profitsData: List[Entry]): Map[String, Double] = {
    profitsData.groupBy(_.category).map {
      case (product, entries) => product -> entries.map(_.profit).sum
    }
  }
  def getProfitsDaysMap(profitsData: List[Entry]): Map[Date, Double] = {
    profitsData.groupBy(_.date).map{
      case (date, entries) => date -> entries.map(_.profit).sum
    }
  }
  def getNetProfit(profitsData: List[Entry]): Double = profitsData.map(_.profit).sum

  def getDayWiseSalesFrequency(profitsData: List[Entry]): TreeMap[Date, Int] = {
    val data = profitsData.groupBy(_.date).map{
      case (date, entries) => date -> entries.length
    }
    TreeMap(data.toSeq: _*)
  }
  def getClientWiseSalesFrequency(profitsData: List[Entry]): Map[String, Int] = {
    profitsData.groupBy(_.client).map {
      case (client, entries) => client -> entries.length
    }
  }
  def getClientWiseProfit(profitsData: List[Entry]): Map[String, Double] = {
    profitsData.groupBy(_.client).map {
      case (client, entries) => client -> entries.map(_.profit).sum
    }
  }
  def getSalesOfficerProfitMap(profitsData: List[Entry], salesOfficersData: List[SalesOfficer]): Map[String, Double] = {
    val salesOfficerMap = getSalesOfficerMap(salesOfficersData)
    profitsData.groupBy(_.region).map{
      case (region, entries) => salesOfficerMap.getOrElse(region, "") -> entries.map(_.profit).sum
    }
  }
  def getSalesOfficerSalesFrequency(profitsData: List[Entry], salesOfficerData: List[SalesOfficer]): Map[String, Int] = {
    val salesOfficerMap = getSalesOfficerMap(salesOfficerData)
    profitsData.groupBy(_.region).map{
      case (region, entries) => salesOfficerMap.getOrElse(region, "") -> entries.size
    }
  }
  def getRegionProfitMap(profitsData: List[Entry], salesOfficersData: List[SalesOfficer]): Map[String, Double] = {
    val salesOfficerMap = getSalesOfficerMap(salesOfficersData)
    profitsData.groupBy(_.region).map {
      case (region, entries) => region -> entries.map(_.profit).sum
    }
  }
  def getRegionSalesFrequency(profitsData: List[Entry], salesOfficerData: List[SalesOfficer]): Map[String, Int] = {
    val salesOfficerMap = getSalesOfficerMap(salesOfficerData)
    profitsData.groupBy(_.region).map {
      case (region, entries) => region -> entries.length
    }
  }
}
