package day2.traits

/**
  * Created by Niels Bokmans on 22-3-2016.
  * Relevant pages in book: 131 (Traits), 146 (Assignment), 138 (Maps)
  */
trait Censor {

  val badWords = Map("shoot" -> "pucky", "darn" -> "beans")

  def censor(input: String): String = {
    if (!badWords.contains(input.toLowerCase())) input else badWords(input.toLowerCase())
  }

  def censorFromInputFile(fileName: String, input: String): String = {
    val map = scala.collection.mutable.Map[String, String]()
    for (line <- io.Source.fromFile("src/day2/traits/badwords.txt").getLines()) {
      val split = line.split(",")
      map += split(0) -> split(1)
    }
    if (!map.contains(input.toLowerCase())) input else map(input.toLowerCase())
  }
}
