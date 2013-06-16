package pl.edu.pw.sag.system

import pl.edu.pw.sag.mobility.AgentState

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 10:53 AM
 */

/**Wiadomosci przesylane miedzy aktorami, zwiazane bardziej z dzialaniem systemu niz logika problemu*/
trait SystemCommand

case class ShutdownAgent() extends SystemCommand
case class MoveOutAgent(state: AgentState, nodeIndex: Int) extends SystemCommand
case class MoveInAgent(state: AgentState) extends SystemCommand

/**
 * Wiadomosc oznaczajaca, ze agent wlasnie sie przeniosl.
 * Sender przyslanej wiadomosci to nowy wezel agenta.
 */
case class MovedIn(prices: Array[BigDecimal]) extends SystemCommand
