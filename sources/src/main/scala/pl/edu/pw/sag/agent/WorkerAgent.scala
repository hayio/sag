package pl.edu.pw.sag.agent

import akka.actor.Actor
import pl.edu.pw.sag.shop.{ProductSold, ProductNeeded}
import pl.edu.pw.sag.mobility.{SecondStore, FirstStore, AgentState}
import pl.edu.pw.sag.system._
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

  def receive = {
    case ProductNeeded(productId, quantity) =>
      state.searchProductId = Some(productId)
      state.currentShopId = Some(state.currentNodeId) // ... i tak dalej
      moveOut(sender, state.cycleState.storeId) //to sa wciaz tylko testy
    case ProductSold(productId, quantity, price) =>
      moveOut(sender, state.currentShopId.get)
    case MovedIn(pr) =>
      println("[WorkerAgent] moved in " + state.currentNodeId)
      state.nodeType match {
        case Shop =>
          sender ! ProductSold(state.searchProductId.get, 1, BigDecimal.int2bigDecimal(1))
          state.prices.update(state.currentShopId.get, pr)    //updates the array with prices from shop
          cleanState()
        case Store =>
          state.cycleState match {
            case FirstStore => // we are in first store, if prices too high, then go to second store
              state.cycleState = SecondStore
              moveToAnotherStore(sender)
            case SecondStore => // we are in second store, buy and go to shop
              sender ! ProductNeeded(state.searchProductId.get, 1)
          }
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
