package pl.edu.pw.sag.mobility

import pl.edu.pw.sag.system.NodeType

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 10:46 PM
 */

/**
 * @param currentNodeId id miejsca (store lub shop), w ktorym znajduje sie agent
 */
case class AgentState(var agentId: Int, var currentNodeId: Int, var nodeType: NodeType) {
  /**Id sklepu, dla ktorego szukany jest towar*/
  var currentShopId: Option[Int] = None
  /**Id produktu aktualnie szukanego*/
  var searchProductId: Option[Int] = None

  //testowa logika, agent przechodzi na zmiane miedzy dwoma storami
  //i testowy stan agenta
  var nowVisitStore0 = true
}
