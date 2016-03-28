package day3.scraper

/**
  * Created by Niels Bokmans on 22-3-2016.
  */

import akka.actor.{ActorSystem, Props}

object Main {
  val urls = List("http://www.amazon.com", "https://www.google.com", "http://www.cnn.com", "http://www.twitter.com")

  def main(args: Array[String]): Unit = {
    fetchSizesSequentially()
    fetchSizesConcurrently()
  }

  def fetchSizesSequentially() = {
    val start = System.nanoTime
    for (url <- urls) {
      println("Size for " + url + ": " + Scraper.getPageSize(url))
    }
    val end = System.nanoTime
    println("Fetching sequentially took " + (end - start) / 1000000000.0 + " seconds.")
  }

  def fetchSizesConcurrently() = {
    val system = ActorSystem("Main")
    for (url <- urls) {
      val ac = system.actorOf(Props(new PageSizeFetcher(url)))
    }
  }
}

