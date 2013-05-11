package pl.edu.pw.sag.agent

import akka.actor.Actor
import pl.edu.pw.sag.shop.ProductNeeded
import pl.edu.pw.sag.mobility.AgentState

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:02 PM
 */
class ShoppingAgent(state: AgentState) extends Actor {
  def receive = {
    case ProductNeeded(productId, quantity) ⇒
      println("ProductNeeded %s + %s".format(productId, quantity))
  }
}
