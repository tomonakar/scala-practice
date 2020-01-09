// javafxを使ってGUIアプリを作成
// リファレンス: https://openjfx.io/javadoc/11/

import javafx.application.Application
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

object Main extends App {
  // _* 可変長引数（引数のリストをいくらでも渡すことができる関数のインターフェース）
  Application.launch(classOf[Main], args: _*)
}

// Scalaではオブジェクトと同じ名前のクラスを作ることができる
class Main extends Application {
  override def start(primaryStage: Stage): Unit = {
    val btn = new Button()
    btn.setText("押してね")

    btn.setOnAction(new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = {
        println("こんにちは")
      }
    })

    val root = new StackPane()
    root.getChildren.add(btn)

    val scene = new Scene(root, 300, 250)
    primaryStage.setTitle("コンソールにこんにちはを出力")
    primaryStage.setScene(scene)
    primaryStage.show()
  }
}