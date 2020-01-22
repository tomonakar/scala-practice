// # note: Scalaのオブジェクト
// Scalaのオブジェクトは、objectキーワードを使って作られる「オブジェクト」と
// クラスのインスタンスなどを含めたオブジェクト全般を表す「オブジェクト」がある

// objectキーワードを使ったオブジェクトは以下のようなもの

// object MyApplication extends App {
//   println('hello')
// }

// 上記は、Appというアプリケーションの振る舞いをもつトレイトをミックスインしたオブジェクト、MyApplicationをobjectキーワードで作成したもの。
// objectキーワードで作成されるオブジェクトは、宣言されたオブジェクト名の型を持つ。上記は、JVM実行環境の中でyいいつのMyApplication型のオブジェクトとなる。
// このように、たった一つのインスタンスしか存在しないオブジェクトのことを、「シングルトンオブジェクト」と呼ぶ。
// objectキーワードで作成されたオブジェクトは、シングルトンオブジェクトを表している。

// Scalaのオブジェクトは以下などに利用される
// - グローバルな状態やユーティリティメソッドを表現するため
// - オブジェクトを作り出すためのメソッドを提供するため
// - アプリケーションなどのシングルトンオブジェクトを作るため

// javascriptだと、上記はグローバル変数で表現される
// javaだと、staticキーワードを用いる性的なメンバーを使って上記のような機能を実現する
// Slacaの場合は、staticキーワードはなく、代わりにobjectキーワードを使う
// ----------------------------------------------------------------------

// # objectの構文
// object オブジェクト名 extends クラス名 with トレイト名1 with トレイト名2 ... {
//   本体
// }

object BannerCreator {
  private val decor = ".｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★"

  def create(message: String) = {
    s"""${decor}
       |${message}
       |${decor}""".stripMargin
  }
}

// 実行すると以下のように出力される
// scala> BannerCreator.create("Hello")
//res0: String =
//.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★
//Hello
//.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★

// 以上のようにいつでも呼び出したいユーティリティメソッドを作成するのに、インスタンス化が不要なオブジェクトは便利に使える

object BannerCreator2 {
  private val decor = ".｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★.｡:･・.｡:*･★"
  // これはオブジェクトがグローバルに呼び出せる性質を利用した、グローバル変数となる
  // BannerCreator2.message = "fuga" と書き換えが可能
  // Scalaもこのようなグローバル変数を利用することは、筋の良くないコードとして推奨されていない （依存関係の複雑化、全体の状態把握の困難化を招く）
  var message = "Hello"

  // また、グローバル変数は処理後もいつまでもメモリ上に残る性質がある
  // 例えば、関数内で利用された変数であれば、関数が終わった途端にメモリ上からは消える状態になる
  // 不要になったオブジェクトや値をメモリ上から自動的に消してくれる機能をJVMの機能で、「ガーベジコレクション」という
  // オブジェクト内の状態はガーベジコレクション対象外のため、プログラムの動作中残り続ける。上時間動くプログラムでは、コンピュータのメモリリソースを使い切ってしまう

  // よって、オブジェクトは、あくまでグローバルである必要性のある定数や、メソッドに用いるのが適切

  def create(message: String) = {
    s"""${decor}
       |${message}
       |${decor}""".stripMargin
  }
}
