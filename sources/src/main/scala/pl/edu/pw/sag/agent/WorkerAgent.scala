package pl.edu.pw.sag.agent

import akka.actor.Actor
import pl.edu.pw.sag.shop.{ProductSold, ProductNeeded}
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.system._
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.system.MovedIn
import scala.Some
import pl.edu.pw.sag.system.ShutdownAgent

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:02 PM
 */

/**
 * Wlasciwy agent, ktory przenosi sie miedzy wezlami shopow i storeow.
 * @param state
 */
class WorkerAgent(val state: AgentState) extends MoveableAgent {
  println("[WorkerAgent] Jestem w systemie " + state.currentNodeId)

  def receive = {
    case ProductNeeded(productId, quantity) =>
      state.searchProductId = Some(productId)
      state.currentShopId = Some(state.currentNodeId) // ... i tak dalej
      moveOut(sender, if (state.nowVisitStore0) 0 else 1) //to sa wciaz tylko testy
      state.nowVisitStore0 = !state.nowVisitStore0
    case ProductSold(productId, quantity, price) =>
      moveOut(sender, state.currentShopId.get)
    case MovedIn(pr) =>
      state.nodeType match {
        case Shop =>
          sender ! ProductSold(state.searchProductId.get, 1, BigDecimal.int2bigDecimal(1))
          state.prices.update(state.currentShopId.get, pr)    //updates the array with prices from shop
          cleanState()
        case Store =>
          sender ! ProductNeeded(state.searchProductId.get, 1)
      }
    case ShutdownAgent =>
      println("[ShutdownAgent] Jestem w systemie " + context.system.name)
      context.stop(self)
  }

  private def cleanState() = {
    state.currentShopId = None
    state.searchProductId = None
  }
}
