package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.Http
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

object Main {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route = cors() {
      get {
        concat(
          path("user") {
            parameters(Symbol("user_name").as[String]) {
              userName => complete(AnimeUsers.addAnimeUser(userName))
            }
          },
          path("episodes") {
            complete(AnimeUsers.getThisWeeksEpisodes);
          }
        )
      }
    }

    Http().newServerAt("0.0.0.0", 8080).bind(route)
  }
}