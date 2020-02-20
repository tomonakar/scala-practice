// note:

// コピぺコードの弊害
// 特定の振る舞いを一つ修正するのに大量のコードを修正する必要がある
// コードの行数自体が増えて、コードを読むのに時間がかかるようになる

// マーチン・ファウラーというエンジニアによって書かれた「リファクタリング」 という著作の中では、
// リファクタリングは、外部から見たときの振る舞いを保ちつつ、
// 理解や修正が簡単になるようにソフトウェアの内部構造を変化させること、 と言われています。

// なぜリファクタリングをするのでしょうか。
// 同じ著作の中で、ケント・ベックというエンジニアは、
//
// - 読みにくいプログラムは変更しにくい
// - ロジックが重複しているプログラムは変更しにくい
// - 機能追加に伴い、既存のコード修正が必要になるプログラムは変更しにくい
// - 複雑な条件分岐の多いプログラムは変更しにくい

// 変更が行いやすいプログラムは、以上の問題が解消され、
// コードを理解しやすくなることでバグを見つけやすく、
// そしてバグも修正しやすく、機能追加も行いやすいため、 非常に品質の高いソフトウェアとなりる

// リファクタの手法
// - メソッドの抽出
// - クラスの抽出
// - データの再編成
// - メソッド名の変更

// Refactoring 1
// ボタンを作成する処理は、ほとんどの場合一緒でした。
// 異なる部分は、ボタンの画像のパスと EventHandler の処理だけです。
// 重複コードを createButton メソッドにまとめる

// Refactoring 2
// クラスへ抽出することで、ファイル構成やクラス構成を見ただけで、そこで何をしているのかが すぐに把握できるようになります。
// これまで実装してきた Main クラス、非常に多機能になっているのがわかるでしょうか。
//
// 本来、クラスは名前が表す通りのクラスでなくてはなりません。
// Main は、英語で「主な部分」ということになりますが、今のままでは Main だけで構成されているアプリケーションということになってしまいます。
// クラスの名前を決める際には、ちゃんと名称と中身が合っているべきです。
//
// この現状を踏まえた上でいくつかのコードを別なファイルに分離しましょう。
// 方針
// - 匿名内部クラスをクラスにする
// - ツールバーを作ることを責務に持つクラスを分離する

// Note
// オブジェクト指向プログラミングでは、 このように単一の責務をもったクラスを作ることが 基本的には推奨されています。
// この原則のことを単一責務の原則といいます。
// 英語では、 Single Responsibility Principle といい、 その頭文字を取って SRP と言うこともあります。
// 基本的に多くの責務を持ったクラスであればあるほど、 モジュール性が低く、他のものと交換したりすることがしにくい部品となってしまいます。
// そのため、単一のクラスが複数の責務を持つことで、 機能同士が関連しあってしまい、変更にたいして非常に脆いクラスになってしまいます。
// このようなことにならないためにも、クラスを設計する際には 単一責務となるように設計し、命名することが重要です。

package jp.tomonakar.nightcoreplayer

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control._
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Callback
import jp.tomonakar.nightcoreplayer.SizeConstants._

object Main extends App {
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {
    val mediaView = new MediaView()

    val timeLabel = new Label()
    timeLabel.setText("00:00:00/00:00:00")
    timeLabel.setTextFill(Color.WHITE)

    val tableView = new TableView[Movie]()
    tableView.setMinWidth(tableMinWidth)
    val movies = FXCollections.observableArrayList[Movie]()
    tableView.setItems(movies)
    tableView.setRowFactory(new Callback[TableView[Movie], TableRow[Movie]]() {
      override def call(param: TableView[Movie]): TableRow[Movie] = {
        val row = new TableRow[Movie]()
        row.setOnMouseClicked(new EventHandler[MouseEvent] {
          override def handle(event: MouseEvent): Unit = {
            if (event.getClickCount >= 1 && !row.isEmpty) {
              MoviePlayer.play(row.getItem, tableView, mediaView, timeLabel)

            }
          }
        })
        row
      }
    })

    val fileNameColumn = new TableColumn[Movie, String]("ファイル名")
    fileNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"))
    fileNameColumn.setPrefWidth(160)
    val timeColumn = new TableColumn[Movie, String]("時間")
    timeColumn.setCellValueFactory(new PropertyValueFactory("time"))
    timeColumn.setPrefWidth(80)
    val deleteActionColumn = new TableColumn[Movie, Long]("削除")
    deleteActionColumn.setCellValueFactory(new PropertyValueFactory("id"))
    deleteActionColumn.setPrefWidth(60)
    deleteActionColumn.setCellFactory(
      new Callback[TableColumn[Movie, Long], TableCell[Movie, Long]]() {
        override def call(
          param: TableColumn[Movie, Long]
        ): TableCell[Movie, Long] = {
          new DeleteCell(movies, mediaView, tableView)
        }
      }
    )

    tableView.getColumns.setAll(fileNameColumn, timeColumn, deleteActionColumn)

    val toolBar =
      ToolbarCreator.create(mediaView, tableView, timeLabel, primaryStage)

    val baseBorderPane = new BorderPane()
    baseBorderPane.setStyle("-fx-background-color: Black")
    baseBorderPane.setCenter(mediaView)
    baseBorderPane.setBottom(toolBar)
    baseBorderPane.setRight(tableView)

    val scene = new Scene(
      baseBorderPane,
      mediaViewFitWidth + tableMinWidth,
      mediaViewFitHeight + toolBarMinHeight
    )

    scene.setFill(Color.BLACK)
    mediaView
      .fitWidthProperty()
      .bind(scene.widthProperty().subtract(tableMinWidth))
    mediaView
      .fitHeightProperty()
      .bind(scene.heightProperty().subtract(toolBarMinHeight))

    scene.setOnDragOver(new MovieFileDragOverEventHandler(scene))
    scene.setOnDragDropped(new MovieFileDragDroppedEventHandler(movies))

    primaryStage.setTitle("mp4ファイルをドラッグ&ドロップしてください")

    primaryStage.setScene(scene)
    primaryStage.show()
  }
}
