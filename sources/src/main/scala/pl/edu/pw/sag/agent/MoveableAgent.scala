package pl.edu.pw.sag.agent

import akka.actor.{ActorRef, Actor}
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.system.MoveOutAgent
import pl.edu.pw.sag.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 1:41 PM
 */

/**
 * Agent, ktory moze byc przenoszony miedzy roznymi wezlami.
 */
trait MoveableAgent extends Actor {
  /**Stan agenta, ktory bedzie przenoszony miedzy wezlami (a wiec nie caly agent, tylko jego stan).*/
  val state: AgentState

  /**
   * Przenosi agenta.
   * @param coordinator aktor shopu lub magazynu, ktory przeniesie agenta
   * @param nodeIndex id wezla (konfiguracji) docelowego
   */
  def moveOut(coordinator: ActorRef, nodeIndex: Int) = {
    coordinator ! MoveOutAgent(state, nodeIndex)
  }

  def moveToAnotherStore(storeCoordinator: ActorRef) = {
    // na liscie wezlow najpierw sa sklepy, potem storey
    storeCoordinator ! MoveOutAgent(state, Conf.SHOP_CONF_NAMES.size + state.cycleState.storeId)
  }
}
