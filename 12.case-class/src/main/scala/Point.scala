class Point(val x: Int, val y: Int) {

  override def toString = s"Point($x, $y)"
}

object Point {
  def apply(x: Int, y: Int): Point = new Point(x, y)
}

// ケースクラスを作成すると、上記のPointクラスの内容を１行で記述できる
// ケースクラスは、
// - メンバーフィールドの公開
// - toString の実装
// - new の代わりのファクトリメソッド
//これらを自動的に用意してくれるクラスなのです。データの構造を表現するのに非常に便利な機能となっている

case class CPoint(x: Int, y: Int)

// ただし、このケースクラスは自動的に val としてフィールドを持ってしまうため、 基本的には var で宣言するような更新可能なフィールドを持たない
//そのため値を更新するときには、 copy メソッドを利用する

// 例
// val cp1 = CPoint(5,6)
// val cp2 = cp1.copy(7,8)
// val cp3 = cp2.copy(y = 9)

// --------------------------------- //
// 変数宣言におけるパターンマッチ
// --------------------------------- //
// ケースクラスは、タプルと同じように分割代入のようなあたいの取り出しが可能
// 変数宣言におけるパターンマッチという
//
// 以下のようにREPELで実行すると
// scala> val cp4 = CPoint(1,2)
// cp4: CPoint = CPoint(1,2)
//
// scala> val CPoint(a,b) = cp4
// a: Int = 1
// b: Int = 2

// cp4.x のように値を取り出すこともできるが、変数宣言位夜パターンマッチと束縛を利用することで実装をシンプルにすることができる
// --------------------------------- //
// 同値性の違いについて
// --------------------------------- //
// 同値性とは、そのオブジェクトの中身が同じものであるかどうかを判断する性質
//
// 例えば、以下のようにクラスを比較した場合には、インスタンスが別なのでfalseとなる
// val c1 = new Point(1, 2)
// val c2 = new Point(1, 2)
// println(c1 == c2)
// => false
//
// しかし、ケースクラスの場合は、インスタンスが異なる場合でも、全てのフィールドを比較し、その値が同じであればtrueという扱いになる。
// つまり、ケースクラスは、参照ではなく構造で比較される。
// val cc1 = new CPoint(1,2)
// val cc2 = new CPoint(1,2)
// println(cc1 == cc2)
// => true
// --------------------------------- //
// hashCode と equals
// --------------------------------- //
//
// hashCode : そのオブジェクトを識別するための Int 型の数値のハッシュコードを返す。
//            ハッシュアルゴリズムが利用されているコレクションなどで内部的に利用されるメソッドだが、 オブジェクトを等しいとする場合には、同じ値を返す必要がある。
// equals   : そのオブジェクト同士が等しいかどうかを判定するメソッド
class hPoint(val x: Int, val y: Int) {
  override def toString: String = s"Point($x, $y)"

  def canEqual(other: Any): Boolean = other.isInstanceOf[hPoint]

  override def equals(other: Any): Boolean = other match {
    case that: hPoint =>
      // this は、そのクラスのインスタンス自体を表すキーワード
      (that canEqual this) &&
        x == that.x &&
        y == that.y
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(x, y)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
