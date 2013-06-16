package pl.edu.pw.sag.store

import pl.edu.pw.sag.system.{Store, GenericSystemAgent}
import akka.actor.ActorRef
import pl.edu.pw.sag.shop.{ProductSold, ProductNeeded}
import pl.edu.pw.sag.Conf

/**
  * Created with IntelliJ IDEA.
  * User: Rafael Hazan
  * Date: 5/24/13
  * Time: 1:03 PM
  */

/**
 * Obsluga stora, jego wlasciciel/zarzadca.
 * @param shopConfNames
 * @param nodeId
 */
class StoreSystemAgent(shopConfNames: List[String], nodeId: Int) extends GenericSystemAgent(shopConfNames, nodeId, Store) {

  /**
   * Obsluga zdarzen przesylanych przez EventProducera lub agentow.
   * @param remoteActors
   * @return
   */
  def handleLogic(remoteActors: List[ActorRef]) = {
    case ProductNeeded(productId, quantity) =>
      println(nodeId + " [StoreSystemAgent] product needed " + productId)
      sender ! ProductSold(productId, quantity, BigDecimal.int2bigDecimal(1))
  }

 }
