package com.kosecki.weddingplanner

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.kosecki.weddingplanner.web.Service
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.util.{Failure, Success}


object WeddingPlannerApp extends App with Service with LazyLogging  {

  override implicit lazy val system = ActorSystem("WeddingPlannerApp")
  override implicit lazy val executor = system.dispatcher
  override implicit lazy val materializer = ActorMaterializer()
  override lazy val config = ConfigFactory.load()

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port")).onComplete {
    case Success(bind) => logger.info(s"Server successfully bound to ${bind.localAddress}")
    case Failure(error) => {
      logger.error(s"Couldn't bind the server: ${error}")
      system.terminate()
    }
  }

}
