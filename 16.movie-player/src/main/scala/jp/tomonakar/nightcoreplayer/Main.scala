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

package jp.tomonakar.nightcoreplayer

import java.io.File
import javafx.application.Application
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.collections.FXCollections
import javafx.event.{ActionEvent, EventHandler}
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control._
import javafx.scene.image.{Image, ImageView}
import javafx.scene.input.{DragEvent, MouseEvent, TransferMode}
import javafx.scene.layout.{BorderPane, HBox}
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.{Callback, Duration}

object Main extends App {
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {
  private[this] val mediaViewFitWidth = 800
  private[this] val mediaViewFitHeight = 450
  private[this] val toolBarMinHeight = 50
  private[this] val tableMinWidth = 300

  override def start(primaryStage: Stage): Unit = {
    val mediaView = new MediaView()

    val timeLabel = new Label()
    timeLabel.setText("00:00:00/00:00:00")
    timeLabel.setTextFill(Color.WHITE)

    val toolBar = new HBox()
    toolBar.setMinHeight(toolBarMinHeight)
    toolBar.setAlignment(Pos.CENTER)
    toolBar.setStyle("-fx-background-color: Black")

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
              playMovie(row.getItem, tableView, mediaView, timeLabel)
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

    // first button
    val firstButton = createButton(
      "first.png",
      new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit =
          if (mediaView.getMediaPlayer != null) {
            playPre(tableView, mediaView, timeLabel)
          }
      }
    )

    // back button
    val backButton = createButton(
      "back.png",
      new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit =
          if (mediaView.getMediaPlayer != null) {
            mediaView.getMediaPlayer.seek(
              mediaView.getMediaPlayer.getCurrentTime
                .subtract(new Duration(10000))
            )
          }
      }
    )

    // play button
    val playButton = createButton(
      "play.png",
      new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit = {
          val selectionModel = tableView.getSelectionModel
          if (mediaView.getMediaPlayer != null && !selectionModel.isEmpty) {
            mediaView.getMediaPlayer.play()
          }
        }
      }
    )

    // pause button
    val pauseButton =
      createButton("pause.png", new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit = {
          if (mediaView.getMediaPlayer != null) mediaView.getMediaPlayer.pause()
        }
      })

    // forward button
    val forwardButton = createButton(
      "forward.png",
      new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit =
          if (mediaView.getMediaPlayer != null) {
            mediaView.getMediaPlayer.seek(
              mediaView.getMediaPlayer.getCurrentTime.add(new Duration(10000))
            )
          }
      }
    )

    // last button
    val lastButton = createButton(
      "last.png",
      new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit =
          if (mediaView.getMediaPlayer != null) {
            playNext(tableView, mediaView, timeLabel)
          }
      }
    )

    // fullscreen button
    val fullscreenButton =
      createButton("fullscreen.png", new EventHandler[ActionEvent]() {
        override def handle(event: ActionEvent): Unit =
          primaryStage.setFullScreen(true)
      })

    toolBar.getChildren.addAll(
      firstButton,
      backButton,
      playButton,
      pauseButton,
      forwardButton,
      lastButton,
      fullscreenButton,
      timeLabel
    )

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

    scene.setOnDragOver(new EventHandler[DragEvent] {
      override def handle(event: DragEvent): Unit = {
        if (event.getGestureSource != scene &&
            event.getDragboard.hasFiles) {
          event.acceptTransferModes(TransferMode.COPY_OR_MOVE: _*)
        }
        event.consume()
      }
    })

    scene.setOnDragDropped(new EventHandler[DragEvent] {
      override def handle(event: DragEvent): Unit = {
        val db = event.getDragboard
        if (db.hasFiles) {
          db.getFiles.toArray(Array[File]()).toSeq.foreach {
            f =>
              val filePath = f.getAbsolutePath
              val fileName = f.getName
              val media = new Media(f.toURI.toString)
              val player = new MediaPlayer(media)
              player.setOnReady(new Runnable {
                override def run(): Unit = {
                  val time = formatTime(media.getDuration)
                  val movie = Movie(
                    System.currentTimeMillis(),
                    fileName,
                    time,
                    filePath,
                    media
                  )
                  while (movies.contains(movie)) {
                    movie.setId(movie.getId + 1L)
                  }
                  movies.add(movie)
                  player.dispose()
                }
              })
          }
        }
        event.consume()
      }
    })

    primaryStage.setTitle("mp4ファイルをドラッグ&ドロップしてください")

    primaryStage.setScene(scene)
    primaryStage.show()
  }

  private[this] def createButton(
    imagePath: String,
    eventHandler: EventHandler[ActionEvent]
  ): Button = {
    val buttonImage = new Image(getClass.getResourceAsStream(imagePath))
    val button = new Button()
    button.setGraphic(new ImageView(buttonImage))
    button.setStyle("-fx-background-color: Black")
    button.setOnAction(eventHandler)
    button.addEventHandler(
      MouseEvent.MOUSE_ENTERED,
      new EventHandler[MouseEvent]() {
        override def handle(event: MouseEvent): Unit = {
          button.setStyle("-fx-body-color: Black")
        }
      }
    )
    button.addEventHandler(
      MouseEvent.MOUSE_EXITED,
      new EventHandler[MouseEvent]() {
        override def handle(event: MouseEvent): Unit = {
          button.setStyle("-fx-background-color: Black")
        }
      }
    )
    button
  }

  private[this] def playMovie(movie: Movie,
                              tableView: TableView[Movie],
                              mediaView: MediaView,
                              timeLabel: Label): Unit = {
    if (mediaView.getMediaPlayer != null) {
      val oldPlayer = mediaView.getMediaPlayer
      oldPlayer.stop()
      oldPlayer.dispose()
    }

    val mediaPlayer = new MediaPlayer(movie.media)
    mediaPlayer
      .currentTimeProperty()
      .addListener(new ChangeListener[Duration] {
        override def changed(observable: ObservableValue[_ <: Duration],
                             oldValue: Duration,
                             newValue: Duration): Unit =
          timeLabel.setText(
            formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration)
          )
      })
    mediaPlayer.setOnReady(new Runnable {
      override def run(): Unit =
        timeLabel.setText(
          formatTime(mediaPlayer.getCurrentTime, mediaPlayer.getTotalDuration)
        )
    })

    mediaPlayer.setOnEndOfMedia(new Runnable {
      override def run(): Unit = playNext(tableView, mediaView, timeLabel)
    })

    mediaView.setMediaPlayer(mediaPlayer)
    mediaPlayer.setRate(10.0)
    mediaPlayer.play()
  }

  sealed trait Track
  object Pre extends Track
  object Next extends Track

  private[this] def playAt(track: Track,
                           tableView: TableView[Movie],
                           mediaView: MediaView,
                           timeLabel: Label): Unit = {
    val selectionModel = tableView.getSelectionModel
    if (selectionModel.isEmpty) return
    val index = selectionModel.getSelectedIndex
    val changedIndex = track match {
      case Pre =>
        (tableView.getItems.size() + index - 1) % tableView.getItems.size()
      case Next => (index + 1) % tableView.getItems.size()
    }
    selectionModel.select(changedIndex)
    val movie = selectionModel.getSelectedItem
    playMovie(movie, tableView, mediaView, timeLabel)
  }

  private[this] def playPre(tableView: TableView[Movie],
                            mediaView: MediaView,
                            timeLabel: Label): Unit =
    playAt(Pre, tableView, mediaView, timeLabel)

  private[this] def playNext(tableView: TableView[Movie],
                             mediaView: MediaView,
                             timeLabel: Label): Unit =
    playAt(Next, tableView, mediaView, timeLabel)

  private[this] def formatTime(elapsed: Duration): String = {
    "%02d:%02d:%02d".format(
      elapsed.toHours.toInt,
      elapsed.toMinutes.toInt % 60,
      elapsed.toSeconds.toInt % 60,
    )
  }

  private[this] def formatTime(elapsed: Duration, duration: Duration): String =
    s"${formatTime(elapsed)}/${formatTime(duration)}"
}
