// match式

//マッチ対象の式 match {
//  case パターン1 [if ガード1] => 式1
//  case パターン2 [if ガード2] => 式2
//  case パターン3 [if ガード3] => 式3
//  case ...
//  case パターンN => 式N
//}

object Match {

  // _ はワイルドカード
  def intToName(num: Int): String = {
    num match {
      case 1 => "one"
      case 2 => "two"
      case _ => "other"
    }
  }

  // Scalaのパターンマッチはフォールスルーの動作をしない
  //　複数パターンをまとめたい時は、 | で case を繋ぐことができる
  def message(message: String): String = {
    message match {
      case "good" | " bad" => "game"
    }
  }

  // パターンマッチによる値の取り出し
  def patternMatch(): Unit = {
    val seq = Seq("A", "B", "C", "D", "E")
    seq match {
      // Seqの先頭が ”A"で、かつ要素数が５の場合
      // ”A"以降の要素は、変数b,c,d,eにそれぞれ束縛される
      // 束縛：引数や定数などの名前に対して、特定の値を固定すること
      case Seq("A", b, c, d, e) if b != "B" =>
        // 上記のように if b != "B" とガード句を付けることもできる
        println(s"b = $b")
        println(s"c = $c")
        println(s"d = $d")
        println(s"e = $e")
      case _ =>
        println("nothing")
    }
  }

  // パターンのネスト
  def patternMatchNest(): Unit = {
    // Seq("A") と Seq("B", "C", "D", "E") の 2要素からなるシーケンス
    val seq = Seq(Seq("A"), Seq("B", "C", "D", "E"))
    seq match {
      // 先頭が "A" のシーケンスにマッチ
      // @ の 後ろに続くパターンマッチする式を、@の前の変数に束縛する（as パターン）
      case Seq(a @ Seq("A"), x) =>
        println(a)
        println(x)
      case _ => println("nothing")
    }
  }

  // 型によるパターンマッチ
  def patternMatchType(): Unit = {
    // AnyRef は　Scalaにおいて全てのオブジェクトが含まれる型
    val obj: AnyRef = "String Literal"
    obj match {
      case v: java.lang.Integer => println(s"Integer! $v")
      case v: String            => println(s"String! length is ${v.length}")
    }
  }

  // パターンマッチの落とし穴
  // ジェネリクス型を使ってのパターンマッチはできない
  // ジェネリクス： Seq[Int]や、Seq[String] のようなコレクション型において、 [] で指定している、中に入る要素の型の指定のこと
  def patternMatchGenerics(): Unit = {
    val obj: AnyRef = Seq("a", "b", "c")

    // 以下のマッチ式では、 Seq[String] ケースにマッチして欲しいが、JVMの仕様により、型として利用される時は、Intのジェネリクス型の情報が消える
    // そのため、Seq[Int]のケースにマッチしてしまう。
    obj match {
      case v: Seq[Int]    => println("Seq[Int]")
      case v: Seq[String] => println("Seq[String]")
    }
  }

  // 最初と最後の文字が同じ英数字であるランダムな5文字のシーケンス1000個を表示
  def printRandomChars(): Unit = {
    for (i <- 1 to 1000) {
      val chars: Seq[Char] = new scala.util.Random(
        new java.security.SecureRandom()
      ).alphanumeric.take(5).toList
      val result = chars match {
        // 5文字目をワイルドカードとし、新たにシーケンスを構築する
        case Seq(a, b, c, d, _) => Seq(a, b, c, d, a)
      }
      println(result)

    }
  }

  // 再起とパターンマッチを利用したシーケンスの最後の値を取得するlastメソッドの実装
  def last(seq: Seq[Int]): Int = {
    seq match {
      // シーケンスの要素数が1つの場合にリターンする
      case Seq(x) => return x
      // x :: xs は シーケンスの最初の要素を除いたシーケンスを作成する
      case x :: xs => last(xs)
    }
  }

  // 再起とパターンマッチを利用したreverseメソッドの実装
  def reverse(seq: Seq[Int]): Seq[Int] = {
    seq match {
      case Seq(x)  => Seq(x)
      case x :: xs => reverse(xs) :+ x
    }
  }
}
