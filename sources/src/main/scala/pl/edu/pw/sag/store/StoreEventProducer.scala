package pl.edu.pw.sag.store

import pl.edu.pw.sag.shop.{ProductNeeded}
import scala.util.Random
import java.util.Date
import pl.edu.pw.sag.Conf
import akka.actor.ActorRef


/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:46 AM
 */
class StoreEventProducer(salesman: ActorRef) extends Runnable {
  val random = new Random(new Date().getTime)

  def run() {
    while (Conf.IS_APP_RUNNING) {
      Thread.sleep(Conf.MIN_TIME_BETWEEN_PRODUCT_DELIVERY + random.nextInt(Conf.MAX_TIME_BETWEEN_PRODUCT_DELIVERY - Conf.MIN_TIME_BETWEEN_PRODUCT_DELIVERY))
      println("PRODUCT NEEDED!")
      salesman ! ProductDelivery(1 + random.nextInt(10), 1 + random.nextInt(1000))
    }
  }
}