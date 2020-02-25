import scala.annotation.tailrec

object Main {

  @tailrec
  def series(n: Int, acc: Int): Int = {
    if (n == 0) {
      acc
    } else {
      series(n - 1, acc + n)
    }
  }

  @tailrec
  def fact(n: Int, acc: Int): Int = {
    if (n <= 1) {
      acc
    } else {
      fact(n - 1, acc * n)
    }
  }

  def twice(f: Int => Int): Int => Int = f.compose(f)
  // def twice(f: Int => Int): Int => Int = (x) => f(f(x)) も正しい

  //  なお compose メソッドと andThen メソッドは、 引数に対して適用する順番がそれぞれ異なる
  //  関数 f と 関数 g があった場合、 (x) => f(g(x)) が f.compose(g) に相当し、 (x) => g(f(x)) が f.andThen(g) に相当する
  //  このように 2 つの関数を組み合わせて作った関数を合成関数という
}
