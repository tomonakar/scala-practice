// # trait memo 01
// トレイトを継承することをミックスインと呼ぶ.
// クラスの継承については、ミックスインと呼ばないので注意.
//
// ## 特徴
// - 複数のトレイトを 1 つのクラスやトレイトにミックスインできる
// - 直接インスタンス化できない
// - クラスパラメーター (コンストラクタ引数) を取ることができない
//
// abstract class キーワードで宣言できる抽象クラスに似てるが、 ひとつのクラスに複数ミックスインできるというところが異なる
// abstract class は、 is-a の関係で継承して利用るが、 トレイトは、クラスに対して振る舞いを追加するという意味合いで使われる
// trait で定義されるメソッドは、抽象クラスと同様に必ずしも実装が ある必要がない

trait Fillable {

  def fill(): Unit = println("Fill!")

}

trait Disposable {

  def dispose(): Unit = println("Dispose!")

}

class Paper

class PaperCup extends Paper with Fillable with Disposable
// トレイトはインスタンス化できないので、以下はエラーになる
// new Fillable()

// 匿名内部クラスをつかwばトレイトをミックスインしたクラスのインスタンス化ができる
// val f = new Fillable(){ override def fill(): Unit = println("Hello") }
// f.fill()
