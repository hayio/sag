package pl.edu.pw.sag.system

import akka.actor.{ActorSystem, ActorRef}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/24/13
 * Time: 4:50 PM
 */
trait AgentSystem {
  val nodeId: Int
  val system: ActorSystem
  val systemAgent: ActorRef
}
