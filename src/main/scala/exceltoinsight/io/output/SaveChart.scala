package exceltoinsight.io.output

import org.knowm.xchart.BitmapEncoder.BitmapFormat
import org.knowm.xchart.internal.chartpart.Chart
import org.knowm.xchart.BitmapEncoder
import java.nio.file.{Files, Path, Paths}
import java.util.Date

object SaveChart {
  private val chartsDir = Paths.get(System.getenv("CHARTS_DIR"))

  def saveChartAsPng[T <: Chart[_,_]](chart: T, chartTitle: String): Unit = {
    if(!Files.exists(chartsDir)){
      Files.createDirectory(chartsDir)
    }
    val chartPath: Path = chartsDir.resolve(s"$chartTitle.png")
    BitmapEncoder.saveBitmap(chart, chartPath.toString, BitmapFormat.PNG)
    println(s"${Date(System.currentTimeMillis())}] $chartTitle saved as a png.")
  }
}
