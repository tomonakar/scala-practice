package jp.co.tomonakar.marubatsu

// インポート時に別名の型にすることができる
import jp.co.tomonakar.marubatsu.board.{
  CellState,
  Empty,
  Maru => MaruState,
  Batsu => BatsuState
}

// パッケージ下の全ての型をインポートすることも出来る
// import jp.co.tomonakar.marubatsu.board._

// このパッケージオブジェクト内にメソッドを実装すると、同じパッケージ内ではimport文を使わないで
// 定義したメソッドを利用することが出来る
package object game {

  def toWinner(cellState: CellState): Winner = cellState match {
    case MaruState  => game.Maru
    case BatsuState => game.Batsu
    case Empty      => game.NoWinner
  }

}
