package jp.co.tomonakar.marubatsu.board

private[marubatsu] class Board(
  private[marubatsu] val cells: Map[(Int, Int), CellState],
  private[marubatsu] val next: CellState
) {

  private[marubatsu] def put(row: Int, column: Int): Board = {
    new Board(cells + ((row, column) -> next), getNext(next))
  }

  // private[this] は、このクラスのインスタンス内でしか呼び出せないようにするアクセス修飾子。
  // private だけの修飾子は必ずしも同一のインスタンスでなくてもアクセスできる
  private[this] def getNext(current: CellState): CellState = {
    current match {
      case Empty => Empty
      case Maru  => Batsu
      case Batsu => Maru
    }
  }

  def canPut(row: Int, column: Int): Boolean = cells((row, column)) == Empty

  override def toString: String = s"Board($cells, $next)"
}

object Board {
  def apply(): Board = {
    val keyValues = for (row <- 0 to 2; column <- 0 to 2)
      yield (row, column) -> Empty
    new Board(keyValues.toMap, Maru)
  }
}
