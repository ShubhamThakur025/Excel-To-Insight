package exceltoinsight.pipeline

import exceltoinsight.models.{Entry, SalesOfficer}

import java.util.Date
import scala.collection.immutable.TreeMap

class StatsCalculator(profitsData: List[Entry], salesOfficersData: List[SalesOfficer]) {

  private def calculateAverageProfitDouble(profitMap: Map[?, Double]): Double = profitMap.values.sum / profitMap.size
  private def calculateAverageSalesInt(salesMap: Map[Date, Int]): Double = salesMap.values.sum.toDouble / salesMap.size
  def getNetProfit: Double = DataTransformer.getNetProfit(profitsData)
  def calculateAverageProfitPerProduct: Double = {
    val profitsProductsMap = DataTransformer.getProfitsProductsMap(profitsData)
    if(profitsProductsMap.isEmpty) 0.0
    else calculateAverageProfitDouble(profitsProductsMap)
  }
  def calculateAverageProfit: Double = {
    val profitsDaysMap = DataTransformer.getProfitsDaysMap(profitsData)
    if(profitsDaysMap.isEmpty) 0.0
    else calculateAverageProfitDouble(profitsDaysMap)
  }
  def calculateAverageProfitPerDay: TreeMap[Date, Double] = {
    val data = profitsData.groupBy(_.date).map{
      case (date, entries) => date -> entries.map(_.profit).sum
    }
    TreeMap(data.toSeq: _*)
  }
  def calculateAverageSalesPerDay: Double = {
    val salesDayMap = DataTransformer.getDayWiseSalesFrequency(profitsData)
    if(salesDayMap.isEmpty) 0.0
    else calculateAverageSalesInt(salesDayMap)
  }

}
