// Scalaにおけるクラス定義
// class クラス名(コンストラクタ引数1: コンストラクタ引数1の型, コンストラクタ引数2: コンストラクタ引数2の型, ...) {
//  0個以上のフィールドの定義またはメソッド定義
//}
// コンストラクタは、new キーワードと共に呼び出されるインスタンスを作成するメソッドのようなもの
class Cat(name: String) {
  def greet(): Unit = println(s"僕、$name")
}

// コンストラクタをvalやvarで変数宣言することで、公開されたフィールドになる
class Cat2(val name: String) {
  def greet(): Unit = println(s"僕、$name")
}

// 外部に公開するメンバーを制限して内部の情報を隠蔽することをカプセル化と言う
// private 修飾子を付けると、クラス内でのアクセスに制限できる
class Cat3(name: String) {
  private def greet(): Unit = println(s"僕、$name")
}
