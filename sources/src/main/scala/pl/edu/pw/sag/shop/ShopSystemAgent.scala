package pl.edu.pw.sag.shop

import pl.edu.pw.sag.system.{Shop, GenericSystemAgent}
import akka.actor.ActorRef

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/24/13
 * Time: 1:03 PM
 */
class ShopSystemAgent(storeConfNames: List[String], nodeId: Int) extends GenericSystemAgent(storeConfNames, nodeId, Shop) {
  def handleLogic(remoteActors: List[ActorRef]) = {
    case productNeeded: ProductNeeded =>
      println("[ShopSystemAgent] " + productNeeded)
      workerAgents.foreach( _ ! productNeeded )
    case ProductSold(productId, quantity, price) =>
      println("[ShopSystemAgent] success! shop bought new product " + productId + " " + quantity + " " + price)
  }
}
