package pl.edu.pw.sag

import akka.serialization.{Serializer}
import akka.actor.{Props, ActorSystem, Actor}
import akka.actor.Actor._

object Main {
	def main(args : Array[String]) = {
		println("hello sag")

    val system = ActorSystem("HelloSystem")
//    val actorRef1 = system.actorOf(Props[MyActor], name = "helloactor")

//    val objectToSend=new CustomObject();
//    Socket s = new Socket("yourhostname", 1234);
//    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
//    out.writeObject(objectToSend);
//    out.flush();
	}
}