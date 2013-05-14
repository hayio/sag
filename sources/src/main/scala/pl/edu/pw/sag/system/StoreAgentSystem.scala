package pl.edu.pw.sag.system

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:12 PM
 */

import akka.kernel.Bootable
import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import pl.edu.pw.sag.Conf
import pl.edu.pw.sag.shop.ShopSalesmanAgent
import pl.edu.pw.sag.store.StoreEventProducer

class StoreAgentSystem extends Bootable {
  val system =
    ActorSystem(Conf.SYSTEM_STORE_NAME, ConfigFactory.load.getConfig(Conf.CONF_STORE_NAME))
val remotePath =
  "akka.tcp://" + Conf.SYSTEM_SHOP_NAME + "@" + Conf.URI_SHOP_1 + ":" + Conf.PORT_SHOP_1 + "/user/salesman"
  //TODO na razie shopagent robi to samo co storeagent
  val storeman = system.actorOf(Props(classOf[ShopSalesmanAgent], remotePath), "storeman")
  val eventProducer = new StoreEventProducer(storeman)

  def startup() {}

  def shutdown() {
    system.shutdown()
  }
}
