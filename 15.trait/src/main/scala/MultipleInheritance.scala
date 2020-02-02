// Memo02--------------------
// 多重継承で起こる菱型継承問題
// --------------------------
// 以下を実行すると、

trait TraitA {
  def greet(): Unit
}

trait TraitB extends TraitA {
  def greet(): Unit = println("Good morning!")
}

trait TraitC extends TraitA {
  def greet(): Unit = println("Good evening!")
}

//class Class1 extends TraitB with TraitC

// 下のようにコンパイルエラーとなる
// > class Class1 extends TraitB with TraitC
//  <console>:18: error: class Class1 inherits conflicting members:
//  method greet in trait TraitB of type ()Unit  and
//  method greet in trait TraitC of type ()Unit
//  (Note: this can be resolved by declaring an override in class Class1.)
//  class Class1 extends TraitB with TraitC

// この場合の1つの解法は、コンパイルエラーに 「Note: this can be resolved by declaring an override in class Class1.」 とあるように Class1 で greet を override すること

class Class2 extends TraitB with TraitC {
  override def greet(): Unit = println("How are you?")
}

// super に型を指定してメソッドを呼びだすことで、 TraitB や TraitC のメソッドを指定して使うこともできる

class Class3 extends TraitB with TraitC {
  override def greet(): Unit = super[TraitB].greet()
}

// TraitB と TraitC の両方のメソッドを呼び出したい場合は,
// 1つの方法は上記と同じように TraitB と TraitC の両方のクラスを明示して呼びだす
class Class4 extends TraitB with TraitC {
  override def greet(): Unit = {
    super[TraitB].greet()
    super[TraitC].greet()
  }
}

// しかし、継承関係が複雑になった場合にすべてを明示的に呼ぶのは大変(コンストラクタのように必ず呼び出されるメソッドもあるし.)
// Scala のトレイトにはこの問題を解決するために「線形化（linearization）」という機能がある

// ----------- 線形化 ---------------
// 以下はエラーにならない

trait TraitD {
  def greet(): Unit
}

trait TraitE extends TraitA {
  override def greet(): Unit = println("Good morning!")
}

trait TraitF extends TraitA {
  override def greet(): Unit = println("Good evening!")
}

class Class5 extends TraitE with TraitF

// (new Class1).greet() を評価すると、Good evening!と評価される

// ------ super を使うことで線形化された親トレイトを使うこともできる -----------
trait TraitG {
  def tweet(): Unit = println("Hello!")
}

trait TraitH extends TraitG {
  override def tweet(): Unit = {
    super.tweet()
    println("My name is hoge.")
  }
}

trait TraitI extends TraitG {
  override def tweet(): Unit = {
    super.tweet()
    println("I like fuga.")
  }
}

class Class6 extends TraitG with TraitH
class Class7 extends TraitH with TraitI

//scala> (new Class6).tweet()
//Hello!
//  My name is hoge.

// このような線形化によるトレイトの積み重ねの 処理を Scala の用語では積み重ね可能なトレイト（Stackable Trait）と呼ぶことがある
