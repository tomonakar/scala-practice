// memo#1
// パッケージ名は逆ドメイン方式で命名するのが一般的
// 同じパッケージ名、同じクラス名だと型名の衝突が起こるのでこの方式をとっている

// boardとgameというパッケージを作る
// game は board を知っているが、board は game を知らないという依存関係にする
// モジュールを複数設計する場合には、依存関係を一方向にすることが一般的に良いとされている
// 胃依存関係が循環してしまうと、変更による影響範囲の予測やモジュールの置き換えが困難になり、モジュール化するメリットが少なくなる

package jp.co.tomonakar.marubatsu.board

// sealed は Scalaで宣言されたファイルの中でしか利用できないクラスを表す（復習）
// abstract class CellState は、このファイルの中のみで使うことができるようになる（復習）
// sealedキーワードをつけておくと、パターンマッチの際に継承されたクラスから漏れがあると、警告を出してくれる（復習）
// private[marubatsu] を記述することで、 jp.co.tomonakar.marubats パッケージからのみアクセス可能となる
private[marubatsu] sealed abstract class CellState
private[marubatsu] case object Empty extends CellState
private[marubatsu] case object Maru extends CellState
private[marubatsu] case object Batsu extends CellState

// 最後に、ここまでで出てきたアクセス修飾子を足りないものも合わせてまとめておく
// またパッケージ名の代わりにクラス名も指定することができるためそれも合わせて記載する
//
// | アクセス修飾子                 |	意味
// | private                      |	自クラス、及び、コンパニオンオブジェクトからアクセスできる
// | private[this]                |	自クラスの同一のインスタンスからのみアクセスできる
// | private[パッケージ/クラス名]   | 指定されたパッケージ/クラスからアクセスできる
// | protected                    |	自クラスまたはサブクラスからアクセスできる
// | protected[this]              |	自クラスまたはサブクラスの同一インスタンスからアクセスできる
// | protected[パッケージ/クラス名]	| 指定したパッケージ/クラスであるサブクラスからアクセスできる

// まとめ
// package は、Scala でモジュール開発を行うための機能
// package 文を利用することでクラスをパッケージに所属させ、同名のクラスを別パッケージ内に存在させることができる
// import 文を使って別パッケージのクラスを読み込むことができ、それに対して別名をつけることもできる
// private[パッケージ名] というアクセス修飾子で、パッケージ名を指定したアクセス制御ができる
