package pl.edu.pw.sag.shop

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
import pl.edu.pw.sag.agent.ShoppingAgent
import pl.edu.pw.sag.system.{MoveOutAgent, MoveInAgent, ShutdownAgent}

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:46 AM
 */
class ShopSalesmanAgent(path: String) extends Actor {
  private var shoppingAgents: List[ActorRef] = Nil

  context.setReceiveTimeout(4.seconds)
  sendIdentifyRequest()

  def sendIdentifyRequest(): Unit =
    context.actorSelection(path) ! Identify(path)

  def receive = {
    case ActorIdentity(`path`, Some(actor)) ⇒
      context.setReceiveTimeout(Duration.Undefined)
      context.become(active(actor))
    case ActorIdentity(`path`, None) ⇒ println(s"Remote actor not availible: $path")
    case ReceiveTimeout              ⇒ sendIdentifyRequest()
    case _                           ⇒ println("Not ready yet")
  }

  def active(remoteActor: ActorRef): Actor.Receive = {
    case actorRef: ActorRef =>
      shoppingAgents = actorRef +: shoppingAgents
    case MoveOutAgent(state) =>
      println("ShopSalesmanAgent: [MoveOutAgent]")
      remoteActor ! MoveInAgent(state)
    case MoveInAgent(state) =>
      println("ShopSalesmanAgent: [MoveInAgent]")
      sender ! ShutdownAgent
      val shopper = context.actorOf(Props(classOf[ShoppingAgent], state), "shopping-agent-demo")
      shoppingAgents = shopper +: shoppingAgents
    case productNeeded: ProductNeeded =>
      shoppingAgents.foreach( _ ! productNeeded )
  }
}
