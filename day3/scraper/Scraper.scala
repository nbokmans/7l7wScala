package day3.scraper

import java.io.IOException

import scala.io.Source

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object Scraper {
  def getPageSize(url: String) = {
    val page = getPage(url)
    if (url == null) -1 else page.mkString.length
  }

  def getPage(url: String) = {
    try {
      Source.fromURL(url)
    } catch {
      case ioe: IOException => null
    }
  }
}
