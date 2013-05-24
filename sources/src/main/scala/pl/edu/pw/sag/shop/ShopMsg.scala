package pl.edu.pw.sag.shop

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:07 PM
 */

/**Wiadomosci przesylane miedzy sklepem a agenetami a magazynami*/
trait ShopMsg

case class ProductNeeded(productId: Int, quantity: Int) extends ShopMsg

case class ProductSold(productId: Int, quantity: Int, price: BigDecimal) extends ShopMsg