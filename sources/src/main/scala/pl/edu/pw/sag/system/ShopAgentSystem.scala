package pl.edu.pw.sag.system

import akka.kernel.Bootable
import akka.actor.{Props, ActorSystem}
import pl.edu.pw.sag.Conf
import com.typesafe.config.ConfigFactory
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.agent.WorkerAgent
import pl.edu.pw.sag.shop.{ShopSystemAgent, ShopEventProducer, ProductNeeded}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:11 PM
 */

/**
 * System sklepu. Laczy wszystko w kupie.
 * @param nodeId
 */
class ShopAgentSystem(val nodeId: Int) extends Bootable with AgentSystem {

  val system =
    ActorSystem(Conf.SYSTEM_NAME, ConfigFactory.load.getConfig(Conf.SHOP_CONF_NAMES(nodeId)))
  println("config " + ConfigFactory.load.getConfig(Conf.SHOP_CONF_NAMES(nodeId)).getValue("akka.remote.netty.tcp.hostname"))
  val worker = initWorker()
  val systemAgent = system.actorOf(Props(classOf[ShopSystemAgent], Conf.STORE_CONF_NAMES, nodeId), "systemagent")
  val eventProducer = new ShopEventProducer(systemAgent)

  def startup() {}

  def shutdown() {
    system.shutdown()
  }

  def init() = {
    systemAgent ! worker
    new Thread(eventProducer).run()
  }

  private def initWorker() = {
    val state = new AgentState(69, nodeId, Shop)
    state.currentShopId = Some(nodeId)
    system.actorOf(Props(classOf[WorkerAgent], state), "shopping-agent-demo")
  }
}