package exceltoinsight

import exceltoinsight.io.DataParser
import exceltoinsight.io.input.FileReader
import exceltoinsight.io.output.{ChartBuilder, SaveChart}
import exceltoinsight.models.{Entry, SalesOfficer}
import exceltoinsight.pipeline.{DataTransformer, StatsCalculator}
import org.knowm.xchart.{CategoryChart, PieChart, XYChart}
import java.util.Date
import scala.collection.immutable.TreeMap

object Orchestrator {
  /**
   * Fetching raw data from the Excel file.
   */
  val dataRepository: Map[String, List[Array[String]]] = FileReader.readDataFromFile()

  /**
   * Extracting the profits and sales data from the raw data repository
   */
  val profitsData: List[Entry] = DataParser.getDataFromProfitsSheet(dataRepository)
  val salesOfficerData: List[SalesOfficer] = DataParser.getDataFromSalesOfficersSheet(dataRepository)

  /**
   * Extraction of insightful data through the transforming pipeline. See README.md for potential data insights list
   */
  private val statsCalculator: StatsCalculator = new StatsCalculator(profitsData, salesOfficerData)
  private val netProfit: Double = statsCalculator.getNetProfit
  private val averageProfitPerOrder: Double = statsCalculator.calculateAverageProfit
  private val averageProfitPerProduct: Double = statsCalculator.calculateAverageProfitPerProduct

  //Temporal Trends
  private val salesFrequencyPerDay: TreeMap[Date, Int] = DataTransformer.getDayWiseSalesFrequency(profitsData)
  private val averageProfitPerDay: TreeMap[Date, Double] = statsCalculator.calculateAverageProfitPerDay

  //Client Insights
  private val clientWiseSalesFrequency: Map[String, Int] = DataTransformer.getClientWiseSalesFrequency(profitsData)
  private val profitsGeneratedPerClient: Map[String, Double] = DataTransformer.getClientWiseProfit(profitsData)

  //Product and category insights
  private val productsWiseProfits: Map[String, Double] = DataTransformer.getProfitsProductsMap(profitsData)
  private val categoryWiseProfits: Map[String, Double] = DataTransformer.getProfitsCategoriesMap(profitsData)

  //Regional and Sales Officers Performance
  private val salesOfficerWiseProfits: Map[String, Double] = DataTransformer.getSalesOfficerProfitMap(profitsData, salesOfficerData)
  private val regionWiseProfits: Map[String, Double] = DataTransformer.getRegionProfitMap(profitsData, salesOfficerData)

  //creating charts
  private val salesFrequencyPerDayChart: XYChart = ChartBuilder.lineChartBuilder("Date-Wise Sales", "Date", "Sales", salesFrequencyPerDay)
  private val averageProfitPerDayChart: XYChart = ChartBuilder.lineChartBuilderDoubleYAxis("Date-Wise Average Profits", "Date", "Profits", averageProfitPerDay)
  private val clientWiseSalesFrequencyChart: CategoryChart = ChartBuilder.barChartBuilder("Client-Wise Sales", "Clients", "Sales", clientWiseSalesFrequency)
  private val profitsGeneratedPerClientChart: CategoryChart = ChartBuilder.barChartBuilderDouble("Profits Generated Per Client", "Clients", "Profits", profitsGeneratedPerClient)
  private val productWiseProfitsChart: CategoryChart = ChartBuilder.barChartBuilderDouble("Product-Wise Profits", "Products", "Profits", productsWiseProfits)
  private val categoryWiseProfitsChart: CategoryChart = ChartBuilder.barChartBuilderDouble("Category-Wise Profits", "Categories", "Profits", categoryWiseProfits)
  private val salesOfficerWiseProfitsChart: PieChart = ChartBuilder.pieChartBuilder("Sales-Officer Wise Profits", salesOfficerWiseProfits)
  private val regionWiseProfitsChart: PieChart = ChartBuilder.pieChartBuilder("Region Wise Profits", regionWiseProfits)

  //saving charts
  def createCharts(): Unit = {
    SaveChart.saveChartAsPng(salesFrequencyPerDayChart, salesFrequencyPerDayChart.getTitle)
    SaveChart.saveChartAsPng(averageProfitPerDayChart, averageProfitPerDayChart.getTitle)
    SaveChart.saveChartAsPng(clientWiseSalesFrequencyChart, clientWiseSalesFrequencyChart.getTitle)
    SaveChart.saveChartAsPng(profitsGeneratedPerClientChart, profitsGeneratedPerClientChart.getTitle)
    SaveChart.saveChartAsPng(productWiseProfitsChart, productWiseProfitsChart.getTitle)
    SaveChart.saveChartAsPng(categoryWiseProfitsChart, categoryWiseProfitsChart.getTitle)
    SaveChart.saveChartAsPng(salesOfficerWiseProfitsChart, salesOfficerWiseProfitsChart.getTitle)
    SaveChart.saveChartAsPng(regionWiseProfitsChart, regionWiseProfitsChart.getTitle)
  }

}
