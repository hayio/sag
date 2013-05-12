package pl.edu.pw.sag.system

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 11:07 AM
 */
object ShopApp {
  def main(args: Array[String]) {
    val system = new ShopAgentSystem
    println("Started Shop Application - waiting for messages")
    Thread.sleep(5000)
    println("Shop Application - init shoppers")
    system.initWithShoppers()
    Thread.sleep(1000)
    println("Shop Application - product needed")
    system.doTest()
  }
}