package pl.edu.pw.sag.system

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

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 5/14/13
 * Time: 3:51 PM
 */
trait RemoteAgent extends Actor {
  val path: String // TODO kolekcja wezlow
  protected var shoppingAgents: List[ActorRef] = Nil

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

  def active(remoteActor: ActorRef): Actor.Receive
}
