package day1.tictactoe

import day1.tictactoe.TicTacToeMarks.TicTacToeMarks

/**
  * Created by Niels Bokmans on 21-3-2016.
  */
class TicTacToeTile(position: Int) {
  val myPosition = position
  var mark: TicTacToeMarks = TicTacToeMarks.Empty

  override def toString: String = mark
}
