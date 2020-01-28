// sealed キーワード（シールド）：Scalaで宣言されたファイルの中でしか利用できないクラスを示す
// 抽象クラス　abstract class DayOfWeek は、この DayOfWeek.scala ファイルの中でのみ使うことができる
// sealedキーワードをつけておくと、パターンマッチの際に継承されたクラスから漏れがあると警告をくれる。 (Javaのenumと同様の機能）

sealed abstract class DayOfWeek
case object Sunday extends DayOfWeek
case object Monday extends DayOfWeek
case object Tuesday extends DayOfWeek
case object Wednesday extends DayOfWeek
case object Thursday extends DayOfWeek
case object Friday extends DayOfWeek
case object Saturday extends DayOfWeek

object DayOfWeek {
  def toNum(day: DayOfWeek): Int = {
    day match {
      case Sunday    => 1
      case Monday    => 2
      case Tuesday   => 3
      case Wednesday => 4
      case Thursday  => 5
      case Friday    => 6
      case Saturday  => 7
    }
  }
}
