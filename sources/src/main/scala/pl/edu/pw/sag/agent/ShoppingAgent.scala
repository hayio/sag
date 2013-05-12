package pl.edu.pw.sag.agent

import akka.actor.Actor
import pl.edu.pw.sag.shop.ProductNeeded
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.system.{MoveOutAgent, ShutdownAgent}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:02 PM
 */
class ShoppingAgent(val state: AgentState) extends MoveableAgent {
  println("[ShoppingAgent] Jestem w systemie " + context.system.name)

  def receive = {
    case ProductNeeded(productId, quantity) =>
      println("ProductNeeded %s + %s".format(productId, quantity))
      println("[ProductNeeded] Jestem w systemie " + context.system.name)
      moveOut(sender)
    case ShutdownAgent =>
      println("[ShutdownAgent] Jestem w systemie " + context.system.name)
      sys.exit()
  }
}
