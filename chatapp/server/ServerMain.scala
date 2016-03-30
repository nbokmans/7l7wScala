package chatapp.server

import akka.actor.{ActorSystem, Props}

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
object ServerMain extends App {

  val system = ActorSystem("ServerMain")
  val server = system.actorOf(Props(new ServerActor(system)))
}
