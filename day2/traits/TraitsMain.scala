package day2.traits

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object TraitsMain {

  def main(args: Array[String]) = {
    val filteredWord = new FilteredWord("Test")
    val filteredWord2 = new FilteredWord("Shoot")
    val filteredWord3 = new FilteredWord("Darn")
    println(filteredWord)
    println(filteredWord2)
    println(filteredWord3)
    println("Replace word with replacement loaded from file")
    println(filteredWord.censorFromFile())
    println(filteredWord2.censorFromFile())
    println(filteredWord3.censorFromFile())
  }
}
