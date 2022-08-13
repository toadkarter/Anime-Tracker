package com.example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model.{HttpEntity, HttpRequest}
import akka.http.scaladsl.model.headers.RawHeader

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.concurrent.duration.DurationInt

object Api {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "my-system")
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  def getAnimeUser(user: String): AnimeUser = {
    val apiResponse = makeApiCall(makeUserApiUrl(user))

    val allCurrentlyWatching = apiResponse.map(entity => getCurrentlyWatchingIds(entity))
      .map(listString => listString.map(animeId => getAnimeEntryFromApi(animeId)))

    val outcome: ArrayBuffer[String] = Await.result(allCurrentlyWatching, 10.seconds)
    val animeEntries: ArrayBuffer[AnimeEntry] = getCurrentlyAiringAnimeEntries(outcome)

    new AnimeUser(user, animeEntries)
  }

  private def makeUserApiUrl(user: String): String = {
    s"https://api.myanimelist.net/v2/users/$user/animelist?fields=list_status&limit=1000"
  }

  private def makeAnimeInfoApiUrl(id: String): String = {
    s"https://api.myanimelist.net/v2/anime/$id?fields=id,title,main_picture,start_date,status,num_episodes,broadcast"
  }

  private def makeApiCall(url: String): Future[HttpEntity.Strict] = {
    val request = HttpRequest(GET, url)
      .withHeaders(RawHeader("X-MAL-CLIENT-ID", sys.env("MAL_CLIENT_ID")))

    Http().singleRequest(request)
      .flatMap(response => response.entity.toStrict(10.seconds))
  }

  private def getAnimeEntryFromApi(animeId: String): String = {
    val request: Future[HttpEntity.Strict] = makeApiCall(makeAnimeInfoApiUrl(animeId))
    val response = request.map(entity => entity.data.utf8String)

    Await.result(response, 10.seconds)
  }

  private def getCurrentlyWatchingIds(entity: HttpEntity.Strict) = {
    ujson.read(entity.data.utf8String)("data").arr
      .filter(animeObject => animeObject("list_status")("status").value == "watching")
      .map(animeObject => animeObject("node")("id").toString())
  }

  private def getCurrentlyAiringAnimeEntries(outcome: ArrayBuffer[String]) = {
    val animeEntries = ArrayBuffer[AnimeEntry]()

    outcome.filter(animeEntry => ujson.read(animeEntry)("status").value == "currently_airing")
      .map(validAnimeEntry => generateAnimeEntries(animeEntries, validAnimeEntry))

    animeEntries
  }

  private def generateAnimeEntries(animeEntries: ArrayBuffer[AnimeEntry], validAnimeEntry: String) = {
    val parsedAnimeEntry = ujson.read(validAnimeEntry)

    val id = parsedAnimeEntry("id").num.toInt.toString
    val title = parsedAnimeEntry("title").value.toString
    val imageUrl = parsedAnimeEntry("main_picture")("medium").value.toString
    val startDate = parsedAnimeEntry("start_date").value.toString
    val numEpisodes = parsedAnimeEntry("num_episodes").num

    val animeEntry = AnimeEntry(id, title, imageUrl, startDate, numEpisodes.toInt)
    animeEntries += animeEntry
  }
}
