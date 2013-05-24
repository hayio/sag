package pl.edu.pw.sag

import com.typesafe.config.ConfigFactory

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:00 AM
 */
object Conf {
  var IS_APP_RUNNING = true

  /// dane sieciowe o wezlach
  // nazwy konfiguracji wezlow
  val STORE_CONF_NAMES = List(
    "storeconf0",
    "storeconf1"
  )
  val SHOP_CONF_NAMES = List(
    "shopconf0"
  )
  val CONF_LOOKUP_NAME = "lookupconf"

  val SYSTEM_NAME = "sag"

  // konfiguracja symulatorow swiata (producenci zdarzen) [ms]
  val MIN_TIME_BETWEEN_PRODUCT_NEEDED = 10000
  val MAX_TIME_BETWEEN_PRODUCT_NEEDED = 30000
  val MIN_TIME_BETWEEN_PRODUCT_DELIVERY = 10000
  val MAX_TIME_BETWEEN_PRODUCT_DELIVERY = 30000

  def getPathForConf(confName: String) = {
    val config = ConfigFactory.load().getConfig(confName)
    "akka.tcp://" + SYSTEM_NAME + "@" + config.getString("akka.remote.netty.tcp.hostname") + ":" + config.getString("akka.remote.netty.tcp.port") + "/user/systemagent"
  }
}
