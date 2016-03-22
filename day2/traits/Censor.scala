package day2.traits

/**
  * Created by Niels Bokmans on 22-3-2016.
  * 131 146 137
  */
trait Censor {

  val badWords = Map("Shoot" -> "Pucky", "Darn" -> "Beans")

  def censor(input: String): String = {
    if (!badWords.contains(input)) input else badWords(input)
  }
}
