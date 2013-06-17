package pl.edu.pw.sag.agent

import akka.actor.Actor
import pl.edu.pw.sag.shop.{ProductLack, ProductSold, ProductNeeded}
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
  println("[WorkerAgent] Jestem w systemie " + state.currentNodeId)

  def receive = {
    case ProductNeeded(productId, quantity) =>
      state.searchProductId = Some(productId)
      state.currentShopId = Some(state.currentNodeId) // ... i tak dalej
      moveOut(sender, state.cycleState.storeId) //to sa wciaz tylko testy
    case ProductSold(productId, quantity, price) =>
      state.isProductBought = true
      moveOut(sender, state.currentShopId.get)
    case ProductLack(productId, quantity) =>
      state.cycleState match {
        case FirstStore => // we are in first store, go to second store
            moveToAnotherStore(sender)
        case SecondStore => // we are in second store, go back to shop without product
          moveOut(sender, state.currentShopId.get)
      }
    case MovedIn(priceTable) =>
      state.nodeType match {
        case Shop =>
          priceTable.foreach(println)
          if (state.isProductBought) {
            sender ! ProductSold(state.searchProductId.get, 1, BigDecimal.int2bigDecimal(1))
            state.availableMoney += state.prices.apply(state.currentShopId.get).apply(state.searchProductId.get)
            println("Available money: " + state.availableMoney)
          }
          else {
            sender ! ProductLack(state.searchProductId.get, 1)
          }
          state.prices += (state.currentShopId.get -> priceTable)     //updates the array with prices from shop
          cleanState()
        case Store =>
          priceTable.foreach(println)
          state.cycleState match {
            case FirstStore => // we are in first store, if prices too high, then go to second store
              if (state.prices.apply(state.currentShopId.get).apply(state.searchProductId.get) > priceTable.apply(state.searchProductId.get)) {
                sender ! ProductNeeded(state.searchProductId.get, 1)
                state.availableMoney -= priceTable.apply(state.searchProductId.get)
                println("Available money: " + state.availableMoney)
              } else {
                moveToAnotherStore(sender)
              }
            case SecondStore => // we are in second store, buy and go to shop
              sender ! ProductNeeded(state.searchProductId.get, 1)
              state.availableMoney -= priceTable.apply(state.searchProductId.get)
              println("Available money: " + state.availableMoney)
          }
      }
    case ShutdownAgent =>
      println("[ShutdownAgent] agent usuniety z sytemu: " + context.system.name)
      context.stop(self)
  }

  private def cleanState() = {
    state.currentShopId = None
    state.searchProductId = None
    state.isProductBought = false
  }
}
