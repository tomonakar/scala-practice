// ------------ 最短経路問題 ----------------
// assets/02.png のような辺に重みがついた無向グラフがある
// 「頂点Aからスタートして頂点Gまで行く経路のうち、もっとも距離が短いものを計算する」 というのが、単一始点最短経路問題

// 総当たりの全探索を検討してみる <-- 頂点を順に並べ全てのパターンを考える（頂点数が増えると、頂点数の階乗以上の計算が必要
// ベルマンフォード法というアルゴリズムが便利
// 最短距離のパスに置ける各頂点の距離を、D(0)からD(k)とした時、i と j を任意の頂点とすると、以下の式が成り立つ

// D(i) = {D(J) + (iからJへの任意の距離)}の集合の最小値

// ↑ を言い換えると「最短経路に置けるその頂点iの最短経路は、その前の頂点jの最短距離と、ij間の距離を足して得られるものの中で最小の距離」

// 各頂点における最短距離候補が見つかったら更新していく、ということを繰り返し
// 更新するものがなくなれば、それがそれぞれの最短経路における頂点の最短経路が見つかっている状態 ということをプログラムで表現するのがベルマンフォード法

// 以下のアルゴリズムで表現できる
// 1. 全ての頂点の最短距離を仮に無限大にする
// 2. 経路の始点の最短距離を0とする
// 3. 辺のコレクションに存在している辺を全て、一度も更新がなくなるまでループし続ける
// 4. ループの中の処理において、
// 5. もし「辺の始点までの最短距離」が無限大では無い場合、かつ、
// 6. 「辺の終点までの最短距離」が「辺の支店までの最短距離」＋ 「辺の距離」より大きい場合、
// 7. 「辺の終点までの最短距離」を更新する

case class Edge(from: Char, to: Char, distance: Int)

object ShortestPath {
  // 頂点
  val vertexes = 'A' to 'G' // toメソッドを利用してレンジオブジェクトを作成できる

  // 辺
  val edges = Seq(
    Edge('A', 'B', 1),
    Edge('A', 'C', 8),
    Edge('B', 'A', 1),
    Edge('B', 'C', 6),
    Edge('B', 'D', 6),
    Edge('B', 'E', 6),
    Edge('C', 'A', 8),
    Edge('C', 'B', 6),
    Edge('C', 'D', 7),
    Edge('D', 'B', 6),
    Edge('D', 'C', 7),
    Edge('D', 'F', 2),
    Edge('E', 'B', 6),
    Edge('E', 'F', 6),
    Edge('E', 'G', 8),
    Edge('F', 'D', 2),
    Edge('F', 'E', 6),
    Edge('F', 'G', 5),
    Edge('G', 'E', 8),
    Edge('G', 'F', 5)
  )

  def solveByBellmanFord(start: Char, goal: Char): Unit = {
    // 各頂点までの距離の初期化
    var distances = vertexes.map(v => (v -> Int.MaxValue)).toMap
    distances = distances + (start -> 0)

    var isUpdated = true
    while (isUpdated) {
      isUpdated = false
      edges.foreach { e =>
        if (distances(e.from) != Int.MaxValue
            && distances(e.to) > distances(e.from) + e.distance) {
          distances = distances + (e.to -> (distances(e.from) + e.distance))
        }
      }
    }
    println(distances)
    println(distances(goal))
  }
}

// まとめ
// - グラフは対象同士の結びつきを表現するデータ構造
// - グラフには、有向グラフ・無向グラフがある
// - グラフなどの複雑な構造もオブジェクト思考プログラミングでわかりやすく表現可能
// - ベルマンフォード法は、単一始点最短経路問題を解くためのアルゴリズムの一つ
