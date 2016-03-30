package day3.xml

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object XmlMain {
  def main(args: Array[String]): Unit = {
    val movies = <movies>
      <movie genre="action">Pirates of the carribean</movie>
      <movie genre="fantasy">The dark knight rises</movie>
    </movies>
    println(movies.text)
  }
}
