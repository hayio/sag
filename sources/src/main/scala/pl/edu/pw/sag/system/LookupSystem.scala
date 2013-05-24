package pl.edu.pw.sag.system

import akka.kernel.Bootable
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import pl.edu.pw.sag.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:47 AM
 */

/**
 * Na razie nie uzywane //TODO wywalic albo uzyc
 */
class LookupSystem extends Bootable {
  val system =
    ActorSystem(Conf.SYSTEM_NAME, ConfigFactory.load.getConfig(Conf.CONF_LOOKUP_NAME))

  def shutdown() {}

  def startup() {
    system.shutdown()
  }
}
