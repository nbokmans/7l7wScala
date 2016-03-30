package chatapp.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
class ServerActor(actorSystem: ActorSystem) extends Actor {
  val Port = 18573
  val Server = "localhost"
  val CommandCharacter = "~"
  val ActiveClients = scala.collection.mutable.HashMap.empty[String, ActorRef]
  val ClientIdentities = scala.collection.mutable.HashMap.empty[String, String]

  IO(Tcp)(actorSystem) ! Bind(self, new InetSocketAddress(Server, Port))

  def receive: Receive = {

    case CommandFailed(_: Bind) =>
      println("Failed to start listening on " + Server + ":" + Port)
      context stop self
      actorSystem.terminate()

    case Bound(localAddress: InetSocketAddress) =>
      println("Started listening on " + localAddress)

    case Connected(remote, local) =>
      ActiveClients += (sender.path.name -> sender)
      sender ! Register(self)
      sendMessage(sender.path.name, "Please identify yourself using ~identify [name]!", serverMessage = true)
    case Received(data) =>
      val text = data.decodeString("US-ASCII")
      val clientActorName = sender.path.name
      if (isCommand(text)) {
        getCommand(text) match {
          case "quit" => quit(clientActorName)
          case "identify" => identify(clientActorName, text)
          case "online" => online(clientActorName)
          case _ => sendMessage(clientActorName, "Unknown command!", serverMessage = true)
        }
      } else {
        if (!ClientIdentities.contains(clientActorName)) {
          sendMessage(clientActorName, "Please identify yourself using ~identity [name]!", serverMessage = true)
        } else {
          sendToAll(ClientIdentities.get(clientActorName).get, text)
        }
      }
  }

  def online(clientActorName: String): Unit = {
    sendMessage(clientActorName, "Currently active users: " + activeUsers, serverMessage = true)
  }

  def sendMessage(clientActorName: String, message: String, serverMessage: Boolean = false) = {
    val actorRef = getActorRefByName(clientActorName)
    if (serverMessage) {
      actorRef ! Write(ByteString("[SERVER]: " + message))
    } else {
      actorRef ! Write(ByteString(message))
    }
  }

  def getActorRefByName(name: String): ActorRef = {
    ActiveClients.get(name).get
  }

  def activeUsers: String = {
    if (ClientIdentities.isEmpty) "nobody (0 users total)."
    else ClientIdentities.values.reduce(_ + ", " + _) +
      " (" + ClientIdentities.size + " users total)."
  }

  def identify(clientActorName: String, text: String) = {
    val split = text.split(" ")
    if (split.length == 1) {
      sendMessage(clientActorName, "Please enter what username you would like to identify with!",
        serverMessage = true)
    } else {
      val desiredName = split(1)
      if (ClientIdentities.values.exists(_ == desiredName)) {
        sendMessage(clientActorName,
          "There is already an user with this username!")
      } else {
        ClientIdentities += (clientActorName -> desiredName)
        sendMessage(clientActorName, "Successfully set your identity to " + desiredName,
          serverMessage = true)
      }
    }
  }

  def quit(clientActorName: String): Unit = {
    if (ActiveClients.contains(clientActorName)) {
      ActiveClients -= clientActorName
      sendToAll("", "<" + ClientIdentities.get(clientActorName).get + "> has left the chatroom.", serverMessage = true)
      ClientIdentities -= clientActorName
    }
  }

  def isCommand(message: String): Boolean = {
    message.startsWith(CommandCharacter)
  }

  def sendToAll(messageSender: String, message: String, serverMessage: Boolean = false) = {
    if (serverMessage) {
      ActiveClients.foreach(f => sendMessage(f._1, message, serverMessage))
    } else {
      ActiveClients.foreach(f => sendMessage(f._1, "<" + messageSender + ">: " + message, serverMessage))
    }
  }

  def getRest(message: String): String = {
    val command = getCommand(message)
    message.substring(command.length() + 1, message.length())
  }

  def getCommand(message: String): String = {
    val split = message.split(" ")
    val command = split(0)
    val actualCommand = command.substring(1, command.length())
    actualCommand
  }
}
