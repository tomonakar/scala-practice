// sbt-assembly という sbt のプラグインの設定ファイル
// https://github.com/sbt/sbt-assembly

// sbt-assembly は、 コンパイルをすることで、 Scala を含めた依存関係のあるライブラリを
// 全て単一の .jar という拡張子で始まるファイルにまとめてビルドをしてくれる
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.8")
