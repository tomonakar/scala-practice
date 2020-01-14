// sbt が ビルドを行う際の設定ファイル
scalaVersion := "2.12.7"

// -deprecation: 今後廃止予定のAPIを利用している時に警告がでる
// -feature: 実験的なAPI利用時の警告
// -unchecked: 型を利用したパターンマッチ機能が正しく動かない時に警告
// -Xlint： 書き方が望ましく無い時に警告
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

val osName: SettingKey[String] = SettingKey[String]("osName")

osName := (System.getProperty("os.name") match {
    case name if name.startsWith("Linux") => "linux"
    case name if name.startsWith("Mac") => "mac"
    case name if name.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
})

libraryDependencies += "org.openjfx" % "javafx-base" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-controls" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-fxml" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-graphics" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-web" % "11-ea+25" classifier osName.value

// jarファイルとして配布できる形式するための追記
assemblyMergeStrategy in assembly := {
    case PathList("module-info.class") => MergeStrategy.first
    case x => (assemblyMergeStrategy in assembly).value(x)
}
