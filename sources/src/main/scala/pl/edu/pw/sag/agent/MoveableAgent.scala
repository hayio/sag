package pl.edu.pw.sag.agent

import akka.actor.{ActorRef, Actor}
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.system.MoveOutAgent

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 1:41 PM
 */
trait MoveableAgent extends Actor {
  val state: AgentState

  def moveOut(coordinator: ActorRef, nodeIndex: Int) = {
    coordinator ! MoveOutAgent(state, nodeIndex)
  }
}
