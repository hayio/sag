package pl.edu.pw.sag.store

import pl.edu.pw.sag.system.{Store, GenericSystemAgent}
import akka.actor.ActorRef
import pl.edu.pw.sag.shop.{ProductLack, ProductSold, ProductNeeded}
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

  private val productQuantities: java.util.Map[Int, Int] = new java.util.HashMap[Int, Int]()

  /**
   * Obsluga zdarzen przesylanych przez EventProducera lub agentow.
   * @param remoteActors
   * @return
   */
  def handleLogic(remoteActors: List[ActorRef]) = {
    case ProductNeeded(productId, quantity) =>
      println("[StoreSystemAgent] agent asks for product " + productId)
      if (productQuantities.containsKey(productId) && productQuantities.get(productId) >= quantity) {
        println("[StoreSystemAgent] we have product, so we sell it")
        productQuantities.put(productId, productQuantities.get(productId) - quantity)
        sender ! ProductSold(productId, quantity, BigDecimal.int2bigDecimal(1))
      }
      else {
        println("[StoreSystemAgent] Sorry, but we don't have product " + productId)
        sender ! ProductLack(productId, quantity)
      }
    case ProductDelivery(productId, quantity) =>
      println("Product delivery arrived, id: " + productId + " quantity: " + quantity)
      var updatedQuantity = quantity
      if (productQuantities.containsKey(productId)) {
        updatedQuantity += productQuantities.get(productId)
      }
      productQuantities.put(productId, updatedQuantity)
  }

 }
