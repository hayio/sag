package pl.edu.pw.sag.system

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 11:07 AM
 */
object ShopApp {
  def main(args: Array[String]) {
    try {
      val nodeId = args(0).toInt
      val system = new ShopAgentSystem(nodeId)
      println("Started Shop Application - waiting for messages")
    } catch {
      case e: Exception =>
        e.printStackTrace()
        println("Please, write a number.")
    }
  }
}