package com.kosecki.weddingplanner.web.DAO

import akka.actor.{Actor, ActorLogging}
import com.kosecki.weddingplanner.web.Service._



class GuestsDAOInMemory extends Actor with ActorLogging {
  def receive: Receive = receiveMessage(List())

  def receiveMessage(guests: List[Guest]): Receive = {
    case CreateGuest(guest) => {
      log.info(s"Creating guest: $guest")
      context.become(receiveMessage(guest::guests))
      sender ! GuestCreated("OK!")
    }
    case ListGuests => {
      log.info(s"Sending list of guests: $guests")
      sender ! GuestsList(guests)
    }
  }


}
