package com.example

import java.time._
import java.time.format.DateTimeFormatter
import scala.collection.mutable

case class AnimeEntry(id: String,
                      title: String,
                      imageUrl: String,
                      startDate: String,
                      numEpisodes: Int,
                      episodesWatched: mutable.HashMap[String, AnimeEpisode]
                      = new mutable.HashMap[String, AnimeEpisode]()
                     )
{
  generateAnimeEpisodes(startDate)

  def toggleEpisodeWatched(episodeDate: String): Unit = {
    episodesWatched(episodeDate).watched = !episodesWatched(episodeDate).watched
  }

  def watchedAllEpisodes: Boolean = {
    episodesWatched.values.forall(episode => episode.watched)
  }

  private def generateAnimeEpisodes(startDate: String): mutable.HashMap[String, AnimeEpisode] = {
    val episodesWatched: mutable.HashMap[String, AnimeEpisode] = new mutable.HashMap()

    val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val currentDate: LocalDate = LocalDate.parse(startDate, dateFormat)
    val today: LocalDate = LocalDate.now()

    for (episodeNumber <- 0 until numEpisodes) {
      val episodeDate = currentDate.plusDays(7 * episodeNumber)
      setDefaultWatchedState(today, episodeDate, episodeNumber + 1)
    }
    episodesWatched
  }

  private def setDefaultWatchedState(today: LocalDate, episodeDate: LocalDate, episodeNumber: Int): Unit = {
    if (today.isAfter(episodeDate)) {
      episodesWatched.put(episodeDate.toString, AnimeEpisode(episodeDate.toString, episodeNumber, watched = false))
    } else {
      episodesWatched.put(episodeDate.toString,  AnimeEpisode(episodeDate.toString, episodeNumber, watched = true))
    }
  }
}
