package jp.tomonakar.nightcoreplayer

import java.io.File
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.media.{Media, MediaPlayer, MediaView}
import javafx.scene.paint.Color
import javafx.stage.Stage

object Main extends App {
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {
    val path = "/Users/staff018/Desktop/video.mp4"
    val media = new Media(new File(path).toURI.toString)
    val mediaPlayer = new MediaPlayer(media)
    mediaPlayer.setRate(1.25)
    mediaPlayer.play()
    val mediaView = new MediaView(mediaPlayer)
    val baseBorderPane = new BorderPane()
    baseBorderPane.setStyle("-fx-background-color: Black")
    baseBorderPane.setCenter(mediaView)
    val scene = new Scene(baseBorderPane, 800, 500)
    scene.setFill(Color.BLACK)
    primaryStage.setScene(scene)
    primaryStage.show()
  }

}
