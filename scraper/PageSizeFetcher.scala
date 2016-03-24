package scraper

import akka.actor.{Props, Actor}


/**
  * Created by Niels Bokmans on 22-3-2016.
  */


class PageSizeFetcher(url: String) extends Actor {

  val urls = List("http://www.amazon.com", "http://www.google.com", "http://www.cnn.com", "http://www.twitter.com")

  val start = System.nanoTime

  override def preStart(): Unit = {
    println("Fetching page size: " + url)
    val fetcher = context.actorOf(Props(new SizerState(url)), "sizer") //line 5
    fetcher ! SizerState.GetPageSize
  }

  //\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[A-Z0-9+&@#/%=~_|]
  def receive = {
    case SizerState.Done =>
      val end = System.nanoTime
      println("Fetching " + url + " took " + (end - start) / 1000000000.0 + " seconds.")

  }

  object SizerState {

    case object GetPageSize

    case object Done
  }

  class SizerState(url: String) extends Actor {
    def receive = {
      case SizerState.GetPageSize =>
        println("Page: " + url + ", size: " + Scraper.getPageSize(url))
        for (line <- Scraper.getPage(url).getLines()) {
          val pattern = "(?i)<a.+?href=\\\"(http.+?)\\\".*?>(.+?)</a>".r
          for (m <- pattern.findAllIn(line)) {
            //println(m)
          }
        }
        sender ! SizerState.Done
    }
  }


}