package com.kosecki.weddingplanner.web

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.kosecki.weddingplanner.web.DAO.{GuestsDAOInMemory, GuestsDAOPersistent}
import com.typesafe.config.ConfigFactory
import akka.pattern._
import akka.util.Timeout
import com.kosecki.weddingplanner.web.Service._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContextExecutor


object Service {
  case class Guest(firstName: String, lastName: String)
  case class CreateGuest(guest: Guest)
  case class GuestCreated(info: String)
  case object ListGuests
  case class GuestsList(guests: List[Guest])
}

trait Service extends Protocol {

  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  lazy val config = ConfigFactory.load()
  implicit val timeout: Timeout = 5 seconds

  val guestsActor: ActorRef = {
    if(config.getBoolean("app.inMemory")) {
      system.actorOf(Props[GuestsDAOInMemory])
    }
    else {
      system.actorOf(Props[GuestsDAOPersistent])
    }
  }


  val routes = {
    logRequestResult("wedding-planner-api") {
      pathPrefix("guests") {
        get {
          complete {
            (guestsActor ? ListGuests)
              .mapTo[GuestsList]
              .map(_.guests)
          }
        } ~
        (post & entity(as[Guest])) { guest =>
          complete {
            (guestsActor ? CreateGuest(guest))
              .mapTo[GuestCreated]
              .map(Created -> _)
          }
        }
      }
    }
  }

}
