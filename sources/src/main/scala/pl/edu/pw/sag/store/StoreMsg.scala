package pl.edu.pw.sag.store

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/11/13
 * Time: 11:07 PM
 */
trait StoreMsg

case class ProductDelivery(productId: Int, quantity: Int) extends StoreMsg