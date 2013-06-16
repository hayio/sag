package pl.edu.pw.sag.mobility

import pl.edu.pw.sag.system.NodeType
import pl.edu.pw.sag.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 10:46 PM
 */

/**
 * Stan agenta, ktory jest przenoszony. Tutaj trzeba zawrzec wszystkie dane, ktore maja sie zachowac po przenosinach.
 * @param currentNodeId id miejsca (store lub shop), w ktorym znajduje sie agent
 */
case class AgentState(var agentId: Int, var currentNodeId: Int, var nodeType: NodeType) {
  /** Id sklepu, dla ktorego szukany jest towar */
  var currentShopId: Option[Int] = None
  /** Id produktu aktualnie szukanego */
  var searchProductId: Option[Int] = None

  //testowa logika, agent przechodzi na zmiane miedzy dwoma storami
  //i testowy stan agenta
  var nowVisitStore0 = true

  /** tablica przechowywująca aktualizowane przez agenta ceny, za jakie sprzedal produkt do sklepu
    * Macierz(id_sklepu, id_produktu)*/
  var prices = Array.ofDim[BigDecimal](Conf.MAX_SHOP_NUMBER, Conf.MAX_PRODUCTS_NUMBER);

  /*Fundusze dostępne dla agenta na zakupy w hurtowni*/
  var availableMoney: BigDecimal = BigDecimal.valueOf(0);
}
