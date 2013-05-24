package pl.edu.pw.sag.system

import akka.actor.{ActorSystem, ActorRef}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/24/13
 * Time: 4:50 PM
 */

/**
 * Wezel
 */
trait AgentSystem {
  /**Id wezla*/
  val nodeId: Int
  /**Kontener przechowujacy lokalnie aktorow (agentow)*/
  val system: ActorSystem
  /**Agent, zarzadca wezla (shopa albo stora)*/
  val systemAgent: ActorRef
}
