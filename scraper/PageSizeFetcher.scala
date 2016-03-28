package scraper

import akka.actor.{Props, Actor}

/**
  * Created by Niels Bokmans on 22-3-2016.
  */


class PageSizeFetcher(url: String) extends Actor {

  override def preStart(): Unit = {
    val fetcher = context.actorOf(Props(new SizerActor()))
    fetcher ! SizerState.GetPageSize(url)
  }

  def receive = {
    case SizerState.Done(time : Long, amtOfLinks: Int) =>
      println("Fetching " + url + " took " + time / 1000000000.0 + " seconds. Had " + amtOfLinks + " links.")

  }

  object SizerState {

    case class GetPageSize(url : String)

    case class Done(time: Long, amtOfLinks: Int)

  }

  class SizerActor() extends Actor {
    def receive = {
      case SizerState.GetPageSize(url : String) =>
        val start = System.nanoTime
        val list = scala.collection.mutable.ListBuffer[String]()
        for (line <- Scraper.getPage(url).getLines()) {
          val pattern = "(?i)<a.+?href=\\\"(http.+?)\\\".*?>(.+?)</a>".r
          for (m <- pattern.findAllIn(line)) {
            list += m
          }
        }
        sender ! SizerState.Done(System.nanoTime - start, list.size)
    }
  }


}