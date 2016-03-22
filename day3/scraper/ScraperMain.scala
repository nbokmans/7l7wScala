package day3.scraper


/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object ScraperMain {

  val urls = List("http://www.amazon.com", "http://www.google.com", "http://www.cnn.com", "http://www.twitter.com")

  def main(args : Array[String]) = {

  }

  def timeMethod(method: () => Unit) = {
    val start = System.nanoTime
    method()
    val end = System.nanoTime
    println("Method took " + (end - start) / 1000000000.0 + " seconds.")
  }

  def fetchPageSizesSequentially() = {
    for (url <- urls) {
      println("Size for " + url + ": " + Scraper.getPageSize(url));
    }
  }

 /* def fetchPageSizesConcurrently() = {
    val caller = self
    for (url <- urls) {
      actor { caller ! (url, Scraper.getPageSize(url))}
    }

    for (i <- 1 to urls.size) {
      receive {
        case (url, size) =>
          println("Size for " + url + ": " + size)
      }
    }
  }*/
}
