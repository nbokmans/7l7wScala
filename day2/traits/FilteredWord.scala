package day2.traits

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
class FilteredWord(override val word: String) extends Word(word) with Censor {

  override def toString: String = {
    censor(word)
  }
}
