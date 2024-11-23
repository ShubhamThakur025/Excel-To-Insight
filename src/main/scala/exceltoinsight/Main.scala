package exceltoinsight

import exceltoinsight.io.DataParser
import exceltoinsight.io.input.FileReader
import exceltoinsight.io.output.ChartBuilder
import exceltoinsight.pipeline.{DataTransformer, StatsCalculator}
import org.knowm.xchart.{CategoryChartBuilder, PieChartBuilder, SwingWrapper, XYChartBuilder}

import java.awt.Color
import java.text.SimpleDateFormat

object Main {
//  def main(args: Array[String]): Unit = {
//    val dataRepository = FileReader.readDataFromFile()
//    val profitsData = DataParser.getDataFromProfitsSheet(dataRepository)
//    val salesOfficerData = DataParser.getDataFromSalesOfficersSheet(dataRepository)
//    val statsCalculator = new StatsCalculator(profitsData = profitsData, salesOfficersData = salesOfficerData)
//
//    val netProfit = statsCalculator.getNetProfit
//    val averageProfitPerOrder = statsCalculator.calculateAverageProfit
//    val averageProfitPerProduct = statsCalculator.calculateAverageProfitPerProduct
//
//    val salesFrequencyPerDay = DataTransformer.getDayWiseSalesFrequency(profitsData)
//    val averageProfitPerDay = statsCalculator.calculateAverageProfitPerDay
//
//    val clientFrequencyPerDay = DataTransformer.getClientWiseSalesFrequency(profitsData)
//    val clientWiseProfits = DataTransformer.getClientWiseProfit(profitsData)
//
//    val productWiseProfits = DataTransformer.getProfitsProductsMap(profitsData)
//    val categoryWiseProfits = DataTransformer.getCategoriesProductsMap(profitsData)
//
//    val salesOfficerWiseProfits = DataTransformer.getSalesOfficerProfitMap(profitsData, salesOfficerData)
//    val salesOfficerWiseSales = DataTransformer.getSalesOfficerSalesFrequency(profitsData, salesOfficerData)
//    val regionWiseProfits = DataTransformer.getRegionProfitMap(profitsData, salesOfficerData)
//    val regionWiseSales = DataTransformer.getRegionSalesFrequency(profitsData, salesOfficerData)
//  }
  def main(Args: Array[String]): Unit = {
    val dataRepository = FileReader.readDataFromFile()
    val profitsData = DataParser.getDataFromProfitsSheet(dataRepository)
    val salesOfficerData = DataParser.getDataFromSalesOfficersSheet(dataRepository)

    val salesFrequencyPerDay = DataTransformer.getDayWiseSalesFrequency(profitsData)
    val clientFrequencyPerDay = DataTransformer.getClientWiseSalesFrequency(profitsData)
    val regionWiseProfits = DataTransformer.getRegionProfitMap(profitsData, salesOfficerData)
    new SwingWrapper(ChartBuilder.pieChartBuilder("Region Wise Profits", regionWiseProfits)).displayChart()
    new SwingWrapper(ChartBuilder.barChartBuilder("Client Wise Frequency", "Clients", "Frequency", clientFrequencyPerDay)).displayChart()
    new SwingWrapper(ChartBuilder.lineChartBuilder("Day Wise Sales", "Date", "Sales", salesFrequencyPerDay)).displayChart()
  }
}