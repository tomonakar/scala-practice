lazy val commonSettings = Seq(
  version := "1.0.0",
  organization := "jp.tomonakar",
  scalaVersion := "2.12.7",
  test in assembly := {}
)

lazy val app = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    mainClass in assembly := Some("jp.tomonakar.nightcoreplayer.Main"),
    assemblyJarName in assembly := "nightcoreplayer.jar"
  )

val osName: SettingKey[String] = SettingKey[String]("osName")

osName := (System.getProperty("os.name") match {
  case name if name.startsWith("Linux")   => "linux"
  case name if name.startsWith("Mac")     => "mac"
  case name if name.startsWith("Windows") => "win"
  case _                                  => throw new Exception("Unknown platform!")
})

libraryDependencies += "org.openjfx" % "javafx-base" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-controls" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-fxml" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-graphics" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-web" % "11-ea+25" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-media" % "11-ea+25" classifier osName.value

assemblyMergeStrategy in assembly := {
  case PathList("module-info.class") => MergeStrategy.first
  case x                             => (assemblyMergeStrategy in assembly).value(x)
}

//バージョンの最後についていた -SNAPSHOT を除去して、 バージョン 1.0.0 としましょう。
//
//ちなみにこのような . をつないで 3 つもしくは 2 つの数値が 連なった文字列のバージョンを付ける方法を「セマンティックバージョニング」といいます。
//
//一番左の数値を「メジャーバージョン」 左から二番目の数字を「マイナーバージョン」 左から三番目の数字を「パッチバージョン」 といいます。
//
//これらのバージョンは、
//
//API の変更に互換性のない場合にはメジャーバージョンを、
//後方互換性があり機能性を追加した場合はマイナーバージョンを、
//後方互換性を伴うバグ修正をした場合はパッチバージョンを上げます。
//セマンティックバージョンは、多くのソフトウェアで守られている バージョニングの方法です。
//遵守して、他の利用者がソフトウェアの依存性についてわかりやすいようにしておきましょう。
//
//セマンティックバージョンニングは、 バージョンを見るだけでソフトウェアの変更がどのような ものであるかを表すことができる非常に便利なものです。
//
//特にライブラリの開発と利用に関しては、 このセマンティックバージョンの考え方が重要になってきます。
//詳しくは、セマンティック バージョニング 2.0.0 のサイトを読んでみましょう。
//https://semver.org/lang/ja/
