object RPG_abstract_override_Polymorphism {}

// 抽象クラスの宣言
// abstractキーワードで抽象クラス化する
// 他のクラスから継承して利用されることが求められるクラス
// このクラス自体はインスタンス化できないので、new でインスタンス化するとエラーになる
abstract class Creature_a(var hitPoint: Int, var attackDamage: Int) {
  def isAlive(): Boolean = this.hitPoint > 0
}

// HeroクラスはCreatureクラスでもある
// 上記を is-a の関係と言い、継承はこの関係を表現するために利用できる性質を持ってる

class Hero_a(_hitPoint: Int, _attackDamage: Int)
    extends Creature(_hitPoint, _attackDamage) {
  // ??? と記述することで、未実装でもメソッドをコンパイルすることができる
  def attack(monster: Monster): Unit = ???

  def escape(monster: Monster): Boolean = ???

  // メソッドのオーバーライド(継承元のメソッドの挙動を上書きする）
  // toStringメソッドはどの親クラスのメソッドか？
  // => Scalaの全てのクラスは、AnyRefという型で参照される Java の Objectというクラスを暗黙的に継承してる
  // => IDEでShiftを二回押して、Objectと入力すると、JavaのObjectクラスの宣言を見ることができる

  // Java の Object クラスにある toString メソッドは、 println 関数などでコンソールに出力した際に、文字列として表示するための関数
  // デフォルトでは、コンソールで println 関数などで呼び出すと、Cat@17d919b6 のように、　{クラス名}@{IDとなるハッシュコード} で表される
  // それを、Hero(体力:100, 攻撃力:50) と出力されるようにオーバーライドした
  override def toString = s"Hero(体力:$hitPoint, 攻撃力: $attackDamage)"
}

// このように、継承やオーバーライドを利用することで、ゲームなどで表現するモデルを コードとして表現してやることができる
// Creature という生き物を表すクラスを継承して、 生き物ではあるが挙動の異なるヒーローやモンスターという派生したクラスを 作成できるような性質のことを「多態性」と呼ぶ
class Monster_a(_hitPoint: Int, _attackDamage: Int, var isAwayFromHero: Boolean)
    extends Creature(_hitPoint, _attackDamage) {

  override def toString =
    s"Monster(体力: $hitPoint, 攻撃力:$attackDamage, ヒーローから離れている:$isAwayFromHero)"
}
