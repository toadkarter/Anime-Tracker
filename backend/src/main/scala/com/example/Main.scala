package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.{Http, model}
import akka.http.scaladsl.marshallers.sprayjson
import akka.http.scaladsl.model.StatusCodes.Success

import scala.util.matching.Regex
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.io.StdIn
import io.circe._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import io.circe.parser._
import io.circe.optics.JsonPath
import io.circe.optics.JsonPath.root
import upickle.default._

import scala.util.Try


object Main {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route = get {
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

    Http().newServerAt("localhost", 8080).bind(route)
  }
}