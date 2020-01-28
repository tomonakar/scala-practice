// ケースクラスとパターンマッチを使うと、データが持てるという性質を使って、 四則演算を表す構文木を作ることもできます。
// 構文木の全てのノードは Exp を継承し、 二項演算を表すノードはそれぞれの子として lhs（左辺）、rhs（右辺）を持つような データ構造を考えます。
// 葉ノードとして四則演算、 Add を加算、Sub を減算、Mul を乗算、Div を除算とし、 整数リテラルは Lit で表現します。

sealed abstract class Exp
case class Add(lhs: Exp, rhs: Exp) extends Exp
case class Sub(lhs: Exp, rhs: Exp) extends Exp
case class Mul(lhs: Exp, rhs: Exp) extends Exp
case class Div(lhs: Exp, rhs: Exp) extends Exp
case class Lit(value: Int) extends Exp

object Exp {

  def eval(exp: Exp): Int = exp match {
    case Add(l, r) => eval(l) + eval(r)
    case Sub(l, r) => eval(l) - eval(r)
    case Mul(l, r) => eval(l) * eval(r)
    case Div(l, r) => eval(l) / eval(r)
    case Lit(v)    => v
  }

}

// パターンマッチで再帰的に構文木を解釈していけるわけです。
// それぞれの Exp ノードでは対応する Scala の算術演算子を適用しつつ、 左右の葉ノードを eval メソッドで再帰的に評価しています。
// Lit 型になった時だけは、中身の値を直接返します。

// val example = Add(Lit(1), Div(Mul(Lit(2), Lit(3)), Lit(2)))
// Exp.eval(example)

// まとめ
// ケースクラスはデータ構造を表すのに非常に便利なクラス
// ケースクラスの同値性は全てのフィールドを比較して確かめられる
// 同値性の挙動を変更する場合には、hashCode と equals メソッドをオーバーライドする
//  ケースクラスは列挙するものにも扱える
// ケースクラスのパターンマッチを使って、木構造を表現することもできる
