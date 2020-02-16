trait Listener {
  def changed(newValue: Int): Unit
}

object Observable {
  private var num = 0
  private var listeners = Seq[Listener]()

  def increment(): Unit = {
    num = num + 1
    listeners.foreach(l => l.changed(num))
  }

  def addListener(listener: Listener) = listeners = listeners :+ listener
}

//
//Observable.addListener(new Listener {
//  override def changed(newValue: Int): Unit = println(s"${newValue} に変わったよ")
//})

//Observable.increment()
//Observable.increment()
//Observable.increment()
