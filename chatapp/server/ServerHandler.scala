package chatapp.server

import akka.actor.Actor
import akka.io.Tcp.{PeerClosed, Write, Received}

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
class ServerHandler extends Actor {
  def receive = {
    case Received(data) => sender() ! Write(data)
    case PeerClosed => context stop self
  }
}
