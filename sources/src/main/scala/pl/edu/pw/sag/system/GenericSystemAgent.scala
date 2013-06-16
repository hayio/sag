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

/**
 * Klasa bazowa dla zarzadcy stora lub shopa.
 * Odpowiedzialna za nawiazanie polaczenia z odleglymi wezlami,
 * przenoszenie agentow miedzy wezlami.
 * @param nodeConfNames
 * @param nodeId
 * @param nodeType
 */
abstract class GenericSystemAgent(nodeConfNames: List[String], nodeId: Int, nodeType: NodeType) extends Actor {
  protected var workerAgents: List[ActorRef] = Nil
  private val actorNodes = new ListBuffer[ActorRef]

  context.setReceiveTimeout(4.seconds)
  sendIdentifyRequest
  private var actualNodeIndex = 0

  def sendIdentifyRequest(): Unit =
    if (actorNodes.size < nodeConfNames.size) {
      println(nodeConfNames(actualNodeIndex) + " " + actualNodeIndex + " check path " + Conf.getPathForConf(nodeConfNames(actualNodeIndex)))
      context.actorSelection(Conf.getPathForConf(nodeConfNames(actualNodeIndex))) ! Identify(Conf.getPathForConf(nodeConfNames(actualNodeIndex)))
    }

  def receive = {
    case actorRef: ActorRef =>
      workerAgents = actorRef +: workerAgents
    case ActorIdentity(_, Some(actor)) ⇒
      println("ActorIdentity received " + actor)
      context.setReceiveTimeout(Duration.Undefined)
      actorNodes += actor
      actualNodeIndex += 1
      sendIdentifyRequest
      if (actorNodes.size == nodeConfNames.size) {
        context.become(active(actorNodes.result()))
      }
    case ActorIdentity(`nodeConfNames`, None) ⇒ println(s"Remote actor not availible: $nodeConfNames")
    case ReceiveTimeout              ⇒ sendIdentifyRequest
    case _                           ⇒ println("Not ready yet")
  }

  def active(remoteActors: List[ActorRef]): Actor.Receive = {
    handleLogic(remoteActors) orElse baseReact(remoteActors)
  }

  def baseReact(remoteActors: List[ActorRef]): Actor.Receive = {
    case actorRef: ActorRef => //TODO refactor
      workerAgents = actorRef +: workerAgents
    case MoveOutAgent(state, nodeId) =>
      println(nodeId + "[MoveOutAgent] procedure of moving agent start " + workerAgents.size)
      var tmpAssert = workerAgents.size
      workerAgents = workerAgents diff List(sender)
      if (tmpAssert != workerAgents.size + 1) {
        println("SYSTEM NIESPOJNY, after remove " + workerAgents.size)
      }
      sender ! ShutdownAgent
      remoteActors(nodeId) ! MoveInAgent(state)
    case MoveInAgent(state) =>
      println("[MoveInAgent] Received agent, move in procedure start.")
      sender ! ShutdownAgent
      state.currentNodeId = nodeId
      state.nodeType = nodeType
      val worker = context.actorOf(Props(classOf[WorkerAgent], state), "shopping-agent-demo")
      workerAgents = worker +: workerAgents
      worker ! MovedIn(prices)
  }

  //the state of current product prices in shop or store
  var prices: Array[BigDecimal] = randomPrices(Conf.MAX_PRODUCTS_NUMBER)

  def randomPrices(size: Int) : Array[BigDecimal] = {
    val pr = new Array[BigDecimal](size)
    for (a <- 0 until size) {
      pr.update(a, Math.random() * Conf.MAX_PRICE)
    }
    return pr
  }

  def handleLogic(remoteActors: List[ActorRef]): Receive
}
