package pl.edu.pw.sag.shop

import pl.edu.pw.sag.system.{Shop, GenericSystemAgent}
import akka.actor.ActorRef
import pl.edu.pw.sag.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/24/13
 * Time: 1:03 PM
 */

/**
 * Agent, zarzadca/wlasciciel sklepu. Jest ucielesnieniem sklepu.
 * @param storeConfNames
 * @param nodeId
 */
class ShopSystemAgent(storeConfNames: List[String], nodeId: Int) extends GenericSystemAgent(storeConfNames, nodeId, Shop) {

  /**
   * Logika obslugi wiadomosci przesylanych z EventProducerow i Agentow
   * @param remoteActors
   * @return
   */
  def handleLogic(remoteActors: List[ActorRef]) = {
    case productNeeded: ProductNeeded =>
      println("[ShopSystemAgent] " + productNeeded)
      workerAgents.foreach( _ ! productNeeded )
    case ProductSold(productId, quantity, price) =>
      println("[ShopSystemAgent] success! shop bought new product " + productId + " " + quantity + " " + price)
  }

}
