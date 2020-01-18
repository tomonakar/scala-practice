// GUI作成に必要なクラスのインポート
// javafxのリファレンス：https://openjfx.io/javadoc/11/
import java.util.function.Consumer
import javafx.animation.{KeyFrame, KeyValue, Timeline}
import javafx.application.Application
import javafx.scene.{Group, Node, Scene}
import javafx.scene.paint.Color
import javafx.scene.shape.{Circle, StrokeType}
import javafx.stage.Stage
import javafx.util.Duration
import java.lang.Math.random

object Main extends App {
  // JavaFXアプリケーションを起動
  // 以下で定義しているMainクラスと, Appトレイトが定義しているargs文字列の配列を
  // 可変長で引き渡す (:_*)
  Application.launch(classOf[Main], args: _*)
}

class Main extends Application {

  override def start(primaryStage: Stage): Unit = {
    // 背景黒、幅800px、高さ600pxのエリア作成
    val root = new Group()
    val scene = new Scene(root, 800, 600, Color.BLACK)
    primaryStage.setScene(scene)

    // 円を管理するcirclesというGroupクラスのオブジェクトを作成
    val circles = new Group()
    // 縁と中身が白で透明度を設定した円の個数をこマインドライン引数から取得する
    // assemblyコマンドでjarファイルをビルド後に以下を実施
    // /Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin/java -jar target/scala-2.12/scala-animation-assembly-0.1.0-SNAPSHOT.jar --num=100
      val circleNum = getParameters.getNamed.getOrDefault("num", "30").toInt
      val color = getParameters.getNamed.getOrDefault("color", "white")
      for (i <- 1 to circleNum) {
      val circle = new Circle(150, Color.web("white", 0.05))
      circle.setStrokeType(StrokeType.OUTSIDE)
      circle.setStroke(Color.web(color, 0.16))
      circle.setStrokeWidth(4)
      circles.getChildren().add(circle)
    }
    // 上で作成した円のオブジェクトを一番基底となるrootグループに追加
    root.getChildren().add(circles)

    // Timelineクラスでアニメーションを定義
    val timeline = new Timeline()
    circles.getChildren().forEach(new Consumer[Node] {

      override def accept(circle: Node): Unit = {
        timeline.getKeyFrames().addAll(
          // アニメーション開始0秒時点でランダムな位置にポジショニング
          new KeyFrame(Duration.ZERO,
            new KeyValue(circle.translateXProperty(), random() * 800: Number),
            new KeyValue(circle.translateYProperty(), random() * 600: Number)
          ),
          // 40秒経過後に別のランダムな位置に移動が完了
          new KeyFrame(new Duration(40000),
            new KeyValue(circle.translateXProperty(), random() * 800: Number),
            new KeyValue(circle.translateYProperty(), random() * 600: Number)
          )
        )
      }

    })
    timeline.play()
    primaryStage.show()
  }

}