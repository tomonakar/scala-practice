// Syntax(構文): プログラミング言語内でプログラムが構造を持つためのルール
// Expression(式): プログラムを構成する部分のうち、評価することで値になるもの
// Statement(文): プログラムを構成する部分のうち、評価しても値にならないもの

object ControlSyntax {
  // {}: brace
  // { exp1; exp2; exp3; ... expN; }
  // Scalaでは、最後の式が評価されて最後の値が返る

  // これは、"A" "B" 3 が返る
  { println("A"); println("B"); 1 + 2 }

  // これは "bar" が返る
  def bar(): String = {
    "foo"
    "bar"
  }

  // if式
  // if (条件式) A [else B]
  // () は Unit型. 関数が意味のある値を返さないという事を意味する型（voidと同じ）
  def printOver18(age: Int): Unit = {
    val message = if (age < 18) {
      "18未満です"
    } else {
      "18以上です"
    }
    println(message)
  }

  // while式
  // while (条件式) A
  def printWhileResult(): Unit = {
    var i = 0
    val result: Unit = while (i < 10) i = i + 1
    println(result)
  }

  // for式
  // for (ジェネレータ1;  ジェネレータ2; ... ジェネレータn) A
  // ジェネレータ1 = a1 <- exp1; ジェネレータ2 = a2 <- exp2; ... ジェネレータn = an <- expn
  def doubleLoop(): Unit = {
    for (x <- 1 to 5; y <- 1 until 5) {
      println(s"x = $x y = $y")
    }
  }

  // forに続く()の中でif句を書いて、そのループで実行する条件を指定することもできる
  // 以下はでは, x と y の値が等しくなる場合のループ処理を取り除くフィルタリング処理をしている
  def doubleLoop2(): Unit = {
    for (x <- 1 to 5; y <- 1 until 5 if x != y) {
      println(s"x = $x y = $y")
    }
  }

  // for式は、コレクション要素を1つ1つ辿って処理を行うこともできる
  def collectionLoop(): Unit = for (e <- Seq("A", "B", "C", "D", "E")) print(e)

  // すでに存在しているコレクションを加工して新たなコレクションを作る
  // yieldキーワードを利用する
  def generateCollection(): Seq[String] =
    for (e <- Seq("A", "B", "C", "D", "E")) yield "Pre" + e

  def printIsBaby(age: Int, isSchoolStarted: Boolean): Unit = {
    if (1 <= age && age <= 6 && !isSchoolStarted) {
      println("幼児です")
    } else {
      println("幼児ではありません")
    }
  }

  def loopFrom0To9(): Unit = {
    var i = 0
    do {
      println(i)
      i = i + 1
    } while (i < 10)
  }

  def printRightTriangles(): Unit =
    for (a <- 1 to 1000; b <- 1 to 1000; c <- 1 to 1000
         if a * a == b * b + c * c)
      println((a, b, c))
}
