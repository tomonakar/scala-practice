import scala.math.BigInt

object Factorial extends App {
  // 末尾最適化されていないので1000を超える処理でスタックオーバーフローが発生する
  // def factorial(i: BigInt): BigInt = if (i == 0) 1 else i * factorial(i - 1)

  // 末尾最適化を行った関数
  def factorial(i: BigInt, acc: BigInt): BigInt = if (i == 0) acc else i * factorial(i -1, i * acc)

  println(factorial(10000, 1))
}

// 末尾最適化の記事
// https://qiita.com/pebblip/items/cf8d3230969b2f6b3132