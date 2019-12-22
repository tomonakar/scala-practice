object RegexSearch extends App {
  val text = "カワカドカドカドドワンゴカドカドンゴドワドワンゴドワカワカドンゴドワ"
  val pattern = "ドワンゴ"
  val matchIndexes = pattern.r.findAllIn(text).matchData.map(_.start).toList
  println(s"出現場所: ${matchIndexes}")
}


// pattern.r	Regex 型に変換
// pattern.r.findAllIn(text)	文字列を検索
// pattern.r.findAllIn(text).matchData	マッチ情報を取得
// pattern.r.findAllIn(text).matchData.map(_.start)	すべてのマッチ情報から出現箇所のインデックスを取得
// pattern.r.findAllIn(text).matchData.map(_.start).toList	Iterator[Int] 型を List[Int] 型に変換

// 今まで map 関数には、外部から関数を渡していたと思いますが、このように _ アンダースコアを使うことで、
// map 関数を適用しようとしているコレクションの要素のメソッドも呼び出すことができます。
// この map 関数を適用することで Iterator[Match] 型が Iterator[Int] 型 に変換されます。

// Scala で文字列を表す String 型は、 Java の String 型が内部的に利用されています。
// Java 11 の API の String 
// https://docs.oracle.com/javase/jp/11/docs/api/java.base/java/lang/String.html

// 最初に見つかった文字列のインデックスを返すメソッドは、 indexOf(String str)
// 最後に見つかった文字列のインデックスを返すメソッドは、 lastIndexOf(String str) 