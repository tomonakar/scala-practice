object BMSearch extends App {
  val text = "カワカドカドカドドワンゴカドカドンゴドワドワンゴドワカワカドンゴドワ".toSeq }
  val pattern = "ドワンゴ".toSeq
  val skipTable = pattern.map(s => (s -> (pattern.reverse.indexOf(s)))).toMap

  val matchIndexes = search(text, pattern)

  def search(text: Seq[Char], pattern: Seq[Char]: Seq[Char]) = {
    var matchIndexes = Seq[Int]()
    var i = 0

    while (i < text.length -1) {
      val partText = text.slice(i, i + pattern.length)
      val patternLastIndex = pattern.length - 1

      var isMatch = true
      var j = patternLastIndex
      var matchChar = '_' // マッチさせた際の文書の位置
      var matchPosition = 0 // マッチさせた際の位置（スキップテーブルから取得した値から差し引く値）

      while (j >= 0 && isMatch) {
        if (j > partText.length - 1) {
          isMatch = false
        } else {
          matchChar = partText(j)
          if (matchChar != pattern(j)) {
            isMatch = false
            matchPosition = (patternLastIndex - j)
          }
        }
        j = j -1
      }

      if (isMatch) matchIndexes = matchIndexes :+ 1

      var skipCount = skipTable.getOrElse(matchChar, pattern.length) - matchPosition
      if (skipCount <= 0) skipCount = 1
      println(s"skip couunt: ${skipCount}")
      i = i + skipCount
    }

  matchIndexes
  }

println(s"出現場所: ${matchIndexes}")
}