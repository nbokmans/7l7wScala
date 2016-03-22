package day1.tictactoe

/**
  * Created by Niels Bokmans on 21-3-2016.
  */
object TicTacToeMain {
  def main(args: Array[String]): Unit = {
    val bufferedReader = io.Source.stdin.bufferedReader()
    println("Hoeveel spelers spelen er? (1 of 2)")
    val players = bufferedReader.readLine().toInt
    if (players < 0 || players > 2) println("Onjuist aantal!") else println("Er spelen " + players + " spelers")
    val game = new TicTacToe(players == 1)
    game.print()
    println("Kies posities 1 (linksboven) - 9 (rechtsonder)")
    while (!game.gameDone) {
      val isPlayer1Turn: Boolean = game.getEmptyTiles % 2 != 0
      if (isPlayer1Turn) println("Beurt: speler 1") else println("Beurt: speler 2")
      val input = bufferedReader.readLine().toInt
      if (game.processInput(input, isPlayer1Turn)) {
        game.print()
      } else {
        println("Ongeldige zet!")
      }
    }
    println("Het spel is over!")
    game.print()
    println(game.winner)
  }
}
