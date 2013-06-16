package pl.edu.pw.sag.mobility

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 6/16/13
 * Time: 11:45 PM
 */
trait ShoppingCycleState {
  val storeId: Int

  def toggle() : ShoppingCycleState
}

case object FirstStore extends ShoppingCycleState {
  val storeId = 0

  def toggle() = SecondStore
}
case object SecondStore extends ShoppingCycleState {
  val storeId = 1

  def toggle() = FirstStore
}