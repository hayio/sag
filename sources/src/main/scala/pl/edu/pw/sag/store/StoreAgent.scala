package pl.edu.pw.sag.store

import scala.concurrent.duration._
import akka.actor._
import scala.concurrent.duration.Duration
import scala.Some
import pl.edu.pw.sag.mobility.AgentState
import pl.edu.pw.sag.agent.ShoppingAgent
import pl.edu.pw.sag.system.ShutdownAgent

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/12/13
 * Time: 12:46 AM
 */
class StoreAgent(path: String) extends Actor {
  private var shoppingAgents: List[ActorRef] = Nil

  context.setReceiveTimeout(3.seconds)
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

  def active(actor: ActorRef): Actor.Receive = {
    case state: AgentState =>
      sender ! ShutdownAgent
      val shopper = context.actorOf(Props(classOf[ShoppingAgent], state), "shopping-agent-demo")
      shoppingAgents = shopper +: shoppingAgents
  }
}
