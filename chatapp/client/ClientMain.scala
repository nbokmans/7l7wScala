package chatapp.client

import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props}
import chatapp.client.ClientMessage.SendMessage

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
object ClientMain extends App {

  val Port = 18573

  val system = ActorSystem("ClientMain")
  val clientConnection = system.actorOf(Props(new ClientActor(new InetSocketAddress("localhost", Port), system)))
  val bufferedReader = io.Source.stdin.bufferedReader()
  loop("")

  def loop(message: String): Boolean = message match {
    case "~quit" =>
      system.terminate()
      false
    case _ =>
      val msg = bufferedReader.readLine()
      clientConnection ! SendMessage(msg)
      loop(msg)
  }
}
