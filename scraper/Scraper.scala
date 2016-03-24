package scraper

import scala.io.Source

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object Scraper {
  def getPageSize(url: String) = getPage(url).mkString.length

  def getPage(url: String) = Source.fromURL(url)
}
