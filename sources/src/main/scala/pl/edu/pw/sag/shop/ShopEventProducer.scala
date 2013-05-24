package pl.edu.pw.sag.shop

import akka.actor.{ActorRef, Actor}
import pl.edu.pw.sag.Conf
import scala.util.Random
import java.util.Date

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:46 AM
 */

/**
 * Producent zdarzen zwiazanych ze sklepem (np. potrzeba produktu x)
 * @param salesman
 */
class ShopEventProducer(salesman: ActorRef) extends Runnable {
  val random = new Random(new Date().getTime)

  def run() {
    while (Conf.IS_APP_RUNNING) {
      Thread.sleep(Conf.MIN_TIME_BETWEEN_PRODUCT_NEEDED + random.nextInt(Conf.MAX_TIME_BETWEEN_PRODUCT_NEEDED - Conf.MIN_TIME_BETWEEN_PRODUCT_NEEDED))
      println("PRODUCT NEEDED!")
      salesman ! ProductNeeded(1 + random.nextInt(10), 1 + random.nextInt(1000))
    }
  }
}
