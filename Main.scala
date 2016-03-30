import akka.util.ByteString

/**
  * Created by Niels Bokmans on 30-3-2016.
  */
object Main extends App {

  val byteString: ByteString = ByteString("Test message")
  println(byteString)
  println("Decoded: " + byteString.decodeString("US-ASCII"))

}
