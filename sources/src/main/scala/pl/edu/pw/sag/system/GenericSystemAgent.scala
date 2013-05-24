package pl.edu.pw.sag.system

import scala.util.Random
import scala.concurrent.duration._
import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.actor.Identify
import akka.actor.ActorIdentity
import akka.kernel.Bootable
import akka.actor.ReceiveTimeout
import akka.actor._
import scala.concurrent.duration.Duration
import scala.Some
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.agent.WorkerAgent
import scala.collection.mutable.ListBuffer
import pl.edu.pw.sag.shop.ProductNeeded
import pl.edu.pw.sag.Conf

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:46 AM
 */
abstract class GenericSystemAgent(nodeConfNames: List[String], nodeId: Int, nodeType: NodeType) extends Actor {
  protected var workerAgents: List[ActorRef] = Nil
  private val actorNodes = new ListBuffer[ActorRef]

  context.setReceiveTimeout(4.seconds)
  sendIdentifyRequest()

  def sendIdentifyRequest(): Unit =
    nodeConfNames.foreach { confName =>
      println("check path " + Conf.getPathForConf(confName))
      context.actorSelection(Conf.getPathForConf(confName)) ! Identify(Conf.getPathForConf(confName))
    }

  def receive = {
    case ActorIdentity(_, Some(actor)) ⇒
      println("ActorIdentity received")
      context.setReceiveTimeout(Duration.Undefined)
      actorNodes += actor
      if (actorNodes.size == nodeConfNames.size) {
        context.become(active(actorNodes.result()))
      }
    case ActorIdentity(`nodeConfNames`, None) ⇒ println(s"Remote actor not availible: $nodeConfNames")
    case ReceiveTimeout              ⇒ sendIdentifyRequest()
    case _                           ⇒ println("Not ready yet")
  }

  def active(remoteActors: List[ActorRef]): Actor.Receive = {
    handleLogic(remoteActors) orElse baseReact(remoteActors)
  }

  def baseReact(remoteActors: List[ActorRef]): Actor.Receive = {
    case actorRef: ActorRef =>
      workerAgents = actorRef +: workerAgents
    case MoveOutAgent(state, nodeId) =>
      println(nodeId + "GenericSystemAgent: [MoveOutAgent] " + workerAgents.size)
      workerAgents = workerAgents diff List(sender)
      println("after remove " + workerAgents.size)
      sender ! ShutdownAgent
      remoteActors(nodeId) ! MoveInAgent(state)
    case MoveInAgent(state) =>
      println("GenericSystemAgent: [MoveInAgent]")
      sender ! ShutdownAgent
      state.currentNodeId = nodeId
      state.nodeType = nodeType
      val worker = context.actorOf(Props(classOf[WorkerAgent], state), "shopping-agent-demo")
      workerAgents = worker +: workerAgents
      worker ! MovedIn
  }

  def handleLogic(remoteActors: List[ActorRef]): Receive
}
