package exceltoinsight.pipeline

import exceltoinsight.models.{Entry, SalesOfficer}

class StatsCalculator(profitsData: List[Entry], salesOfficersData: List[SalesOfficer]) {

  private def calculateAverageProfitDouble(profitMap: Map[String, Double]): Double = profitMap.values.sum / profitMap.size
  private def calculateAverageSalesInt(salesMap: Map[String, Int]): Double = salesMap.values.sum / salesMap.size
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
  def calculateAverageProfitPerDay: Map[String, Double] = {
    profitsData.groupBy(_.date).map{
      case (date, entries) => date -> entries.map(_.profit).sum
    }
  }
  def calculateAverageSalesPerDay: Double = {
    val salesDayMap = DataTransformer.getDayWiseSalesFrequency(profitsData)
    if(salesDayMap.isEmpty) 0.0
    else calculateAverageSalesInt(salesDayMap)
  }

}
