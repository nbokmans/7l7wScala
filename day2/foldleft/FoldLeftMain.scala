package day2.foldleft

/**
  * Created by Niels Bokmans on 22-3-2016.
  */
object FoldLeftMain {

  def stringList = List[String]("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten")

  def intList = List[Int](1, 2, 3, 4)

  def list = List(1, 2, 3)

  def main(args: Array[String]): Unit = {
    println("List[String] to string length with fold left: " + countStringList(stringList))
    println("Sum of List[Int]: " + countIntListSum(intList))
    println("Fold left sum of List[Int]: " + countIntListFoldLeft(intList))
    println("Append List[String]: " + appendStringList(stringList))
  }

  def countIntListSum(list: List[Int]): Int = {
    list.sum
  }

  def countIntListFoldLeft(list: List[Int]): Int = {
    list.foldLeft(0)((sum, value) => sum + value)
  }

  def countStringList(list: List[String]): Int = {
    list.foldLeft(0)((sum, str) => sum + str.length())
  }

  def appendStringList(list: List[String]): String = {
    list.reduce(_ + ", " + _)
  }

}
