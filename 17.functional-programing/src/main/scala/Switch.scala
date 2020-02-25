case class Switch(isOn: Boolean) {
  def toggle(switch: Switch): Unit = {
    if (switch.isOn) Switch(false) else Switch(true)
  }
}
