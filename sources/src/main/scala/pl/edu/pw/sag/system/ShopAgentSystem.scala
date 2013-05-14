package pl.edu.pw.sag.system

import akka.kernel.Bootable
import akka.actor.{Props, ActorSystem}
import pl.edu.pw.sag.Conf
import com.typesafe.config.ConfigFactory
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.agent.ShoppingAgent
import pl.edu.pw.sag.shop.{ShopEventProducer, ShopSalesmanAgent, ProductNeeded}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:11 PM
 */
class ShopAgentSystem extends Bootable {
  val system =
    ActorSystem(Conf.SYSTEM_SHOP_NAME, ConfigFactory.load.getConfig(Conf.CONF_SHOP_NAME))

  val shopper = initShopper()
  val remotePath =
    "akka.tcp://" + Conf.SYSTEM_STORE_NAME + "@" + Conf.URI_STORE_1 + ":" + Conf.PORT_STORE_1 + "/user/storeman"
  val salesman = system.actorOf(Props(classOf[ShopSalesmanAgent], remotePath), "salesman")
  val eventProducer = new ShopEventProducer(salesman)

  def startup() {}

  def shutdown() {
    system.shutdown()
  }

  def initWithShoppers() = {
    salesman ! shopper
    new Thread(eventProducer).run()
  }

  def doTest() = {
    //test
    salesman ! ProductNeeded(1, 2)
  }

  private def initShopper() = {
    val state = new AgentState(69)
    state.currentShopId = Some(69)
    system.actorOf(Props(classOf[ShoppingAgent], state), "shopping-agent-demo")
  }
}