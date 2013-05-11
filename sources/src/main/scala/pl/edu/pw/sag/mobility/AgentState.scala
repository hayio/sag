package pl.edu.pw.sag.mobility

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 10:46 PM
 */

/**
 * @param currentPlaceId id miejsca (store lub shop), w ktorym znajduje sie agent
 */
case class AgentState(currentPlaceId: Int) {
  /**Id sklepu, dla ktorego szukany jest towar*/
  var currentShopId: Option[Int] = None
  /**Id produktu aktualnie szukanego*/
  var searchProductId: Option[Int] = None
}
