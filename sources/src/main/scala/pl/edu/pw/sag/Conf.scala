package pl.edu.pw.sag

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:00 AM
 */
object Conf {
  var IS_APP_RUNNING = true

  /// dane sieciowe o wezlach
  // nazwy wezlow
  val SYSTEM_STORE_NAME = "storeSystem"
  val SYSTEM_SHOP_NAME = "shopSystem"
  val SYSTEM_LOOKUP_NAME = "lookupSystem"
  // nazwy konfiguracji wezlow
  val CONF_STORE_NAME = "storeconf"
  val CONF_SHOP_NAME = "shopconf"
  val CONF_LOOKUP_NAME = "lookupconf"
  // numery ip wezlow
  val URI_STORE_1 = "127.0.0.1"
  val PORT_STORE_1 = "2554"
  val URI_SHOP_1 = "127.0.0.1"
  val PORT_SHOP_1 = "2552"

  // konfiguracja symulatorow swiata (producenci zdarzen) [ms]
  val MIN_TIME_BETWEEN_PRODUCT_NEEDED = 10000
  val MAX_TIME_BETWEEN_PRODUCT_NEEDED = 30000
  val MIN_TIME_BETWEEN_PRODUCT_DELIVERY = 10000
  val MAX_TIME_BETWEEN_PRODUCT_DELIVERY = 30000
}
