package exceltoinsight.io.output

import scala.jdk.CollectionConverters.*
import org.knowm.xchart.{CategoryChart, CategoryChartBuilder, PieChart, PieChartBuilder, XYChart, XYChartBuilder}
import java.util
import java.util.Date
import scala.collection.immutable.TreeMap

object ChartBuilder {
  def pieChartBuilder(title: String, seriesMap: Map[String, Double]): PieChart =  {
    val pieChart = new PieChartBuilder().title(title).build()
    seriesMap.foreach {
      case (region, profit) =>
        pieChart.addSeries(region, profit)
    }
    pieChart
  }
  def barChartBuilder(title: String, xAxisTitle: String, yAxisTitle: String, seriesMap: Map[String, Int]): CategoryChart = {
    val barChart = new CategoryChartBuilder().title(title).width(1500).height(600).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build()
    val xData = seriesMap.keys.toList.asJava
    val yData = seriesMap.values.map(Integer.valueOf).toList.asJava
    barChart.addSeries(title, xData, yData)
    barChart
  }

  def lineChartBuilder(title: String, xAxisTitle: String, yAxisTitle: String, seriesMap: TreeMap[Date, Int]): XYChart = {
    val lineChart = new XYChartBuilder().title(title).xAxisTitle(xAxisTitle).yAxisTitle(yAxisTitle).build()
    val xData = seriesMap.keys.toList.asJava
    val yData = seriesMap.values.map(Integer.valueOf).toList.asJava
    lineChart.addSeries(title, xData, yData)
    lineChart
  }
}
