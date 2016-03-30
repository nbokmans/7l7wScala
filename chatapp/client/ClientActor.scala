package chatapp.client

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorSystem, Kill}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString
import chatapp.client.ClientMessage.SendMessage

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
class ClientActor(address: InetSocketAddress, actorSystem: ActorSystem) extends Actor {

  IO(Tcp)(actorSystem) ! Connect(address)

  def receive = {
    case CommandFailed(command: Tcp.Command) =>
      println("Failed to connect to " + address.toString)
      self ! Kill
      actorSystem.terminate()
    case Connected(remote, local) =>
      println("Successfully connected to " + address)
      val connection = sender()
      connection ! Register(self)
      context become {
        case Received(data) =>
          println(data.decodeString("US-ASCII"))
        case SendMessage(message) =>
          connection ! Write(ByteString(message))
      }
  }
}
