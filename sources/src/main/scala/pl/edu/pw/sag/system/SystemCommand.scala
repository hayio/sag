package pl.edu.pw.sag.system

import pl.edu.pw.sag.mobility.AgentState

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 10:53 AM
 */
trait SystemCommand

case class ShutdownAgent() extends SystemCommand
case class MoveOutAgent(state: AgentState) extends SystemCommand
case class MoveInAgent(state: AgentState) extends SystemCommand
