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
import pl.edu.pw.sag.store.{StoreSystemAgent, StoreEventProducer}

/**
 * System sotra, laczy wszytko w kupe.
 * @param nodeId
 */
class StoreAgentSystem(val nodeId: Int) extends Bootable with AgentSystem {

  val system =
    ActorSystem(Conf.SYSTEM_NAME, ConfigFactory.load.getConfig(Conf.STORE_CONF_NAMES(nodeId)))
  val systemAgent = system.actorOf(Props(classOf[StoreSystemAgent], Conf.SHOP_CONF_NAMES ++ Conf.STORE_CONF_NAMES, nodeId), "systemagent")
  val eventProducer = new StoreEventProducer(systemAgent)

  def startup() {}

  def shutdown() {
    system.shutdown()
  }
}