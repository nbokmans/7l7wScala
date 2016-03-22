package day2.foldleft

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object FoldLeftMain {

  def stringList = List[String]("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten")
  def intList = List[Int](1, 2, 3, 4)
  def list = List(1, 2, 3)
  def main(args: Array[String]) : Unit = {
    println("List[String] to string length with fold left: " + countStringArray(stringList))
    println("Sum of List[Int]: " + countIntArraySum(intList))
    println("Fold left sum of List[Int]: " + countIntArrayFoldLeft(intList))
    println("Append List[String]: " + appendStringArray(stringList))
  }

  def countIntArraySum(list : List[Int]) : Int = {
    list.sum
  }
  def countIntArrayFoldLeft(list : List[Int]) : Int = {
    list.foldLeft(0)((sum, value) => sum + value)
  }
  def countStringArray(list : List[String]) : Int = {
    list.foldLeft(0)((sum, str) => sum + str.length())
  }

  def appendStringArray(list : List[String]) : String = {
    list.reduce(_ + ", " + _)
  }

}
