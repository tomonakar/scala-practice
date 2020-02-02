// ---------------------
// 自分型アノテーション
// (Dependency Injectionの利用)
// ---------------------

trait Greeter {
  def greet(): Unit
}

trait Robot {
  // ここが自分型アノテーション
  self: Greeter =>

  // Greeterを継承していないのに、内部実装でgreetメソッドを使える
  def start(): Unit = greet()
  override final def toString = "Robot"
}

// ロボットオブジェクトを作るには、greetメソッドを実装したトレイトが必要になる
// Robotトレイトは、Greeterトレイトがgreetメソッドの実装があるトレイトにミックスインされることで、
// 抽象メソッドだったgreetに実装が与えられ、インスタンス化できるようになる

// 以下をREPELで実行すると
trait HelloGreeter extends Greeter {
  def greet(): Unit = println("Hello!")
}

val r: Robot = new Robot with HelloGreeter
r.start()

// 以下の評価になる
// >defined trait HelloGreeter
// >r: Robot with HelloGreeter = Robot
// >Hello!

// 要するに、自分型を使う場合は、抽象トレイトを指定し、後から実装を追加する形式になる
// ------- このように後から（もしくは外から）利用するモジュールの実装を与えることを 「依存性の注入(Dependency Injection)」と呼ぶ ----------
// 自分型が使われている場合、この依存性の注入のパターンが使われていると考えてよい

// 以下で自分型によるトレイトの指定と直接継承する場合とで比較する
trait Speak {
  def speak(): Unit
}

trait Robot2 extends Speak {
  def start(): Unit = speak()
  override final def toString = "Robot2"
}

trait HelloGreeter2 extends Speak {
  def speak(): Unit = println("Hello!")
}

// 以下をREPELで実行すると
// >val r2 :Robot2 = new Robot2 with HelloGreeter2
// >r2.start()

// -----------------------------------------
// r2: Robot2 with HelloGreeter2 = Robot2
// Hello!
// -----------------------------------------
// と出力される

// 自分型を振り返ると、以下のように使用したいた。
// trait HelloGreeter extends Greeter {
//  def greet(): Unit = println("Hello!")
// }
//
// val r: Robot = new Robot with HelloGreeter
// r.start()

// つまり、継承はあくまで is-a の関係だが、
// 自分型アノテーションを利用することで、 その is-a の縛りを実際に利用するときの型に影響させないで、 振る舞いをインスタンスに与えることができる

// -----------------------------
// もう一つの自分型の特徴
// -----------------------------
// 循環参照を許す
trait Robot3 {
  self: Greeter =>

  def name: String

  def start(): Unit = greet()
}

// コンパイルできる
trait Greeter3 {
  self: Robot =>

  def greet(): Unit = println(s"My name is $name")
}

// 継承は循環参照を許さない
// 以下はコンパイルエラーとなる
// trait Robot4 extends Greeter3 {
//     def name: String
//
//    def start(): Unit = greet()
//   }

// なお、依存性の注入には以下の方法があるが、それぞれにメリットデメリットがあるので、実装時に検討して利用する必要がある
// - 継承を使う
// - 自分型を使う
// - コンストラクタを使って機能を持つオブジェクトを渡す
// - 依存性注入を行うライブラリを利用して XML などの設定や アノテーションという機能を利用して行う


// ----------------------------
// まとめ
// ----------------------------
// - ひとつのクラスに複数のトレイトをミックスインして利用することができる
// - Scala で発生する菱型継承問題は、線形化して対処することができる
// - トレイトの自分型アノテーションを使って依存性の注入を行うことができる
