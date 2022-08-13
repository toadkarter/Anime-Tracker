package com.example

import java.time.LocalDate
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class AnimeUser(val userName: String, val animeEntries: ArrayBuffer[AnimeEntry]) {
  val userAnime = new mutable.HashMap[String, AnimeEntry]()
  animeEntries.foreach(animeEntry => addAnimeEntry(animeEntry))

  val userTimetable = new mutable.HashMap[String, ArrayBuffer[String]]()
  animeEntries.foreach(animeEntry => addEpisodesToTimetable(animeEntry))

  def getEpisodesByWeek(initialDate: LocalDate): mutable.Map[String, ArrayBuffer[String]] = {
    val thisWeeksEpisodes = new mutable.HashMap[String, ArrayBuffer[String]]()
    for (i <- 0 until 8) {
      val dayToCheck = initialDate.plusDays(i)
      if (userTimetable.contains(dayToCheck.toString)) {
        val entriesToAdd = userTimetable(dayToCheck.toString)
        updateThisWeeksEpisodes(thisWeeksEpisodes, dayToCheck, entriesToAdd)
      }
    }
    thisWeeksEpisodes
  }

  private def updateThisWeeksEpisodes(thisWeeksEpisodes: mutable.HashMap[String, ArrayBuffer[String]], dayToCheck: LocalDate, entriesToAdd: ArrayBuffer[String]) = {
    if (thisWeeksEpisodes.contains(dayToCheck.toString)) {
      thisWeeksEpisodes(dayToCheck.toString) ++ entriesToAdd
    } else {
      thisWeeksEpisodes(dayToCheck.toString) = entriesToAdd
    }
  }

  def toggleEpisodeWatched(animeId: String, episodeDate: LocalDate): Unit = {
    val animeEntry = userAnime(animeId)
    animeEntry.toggleEpisodeWatched(episodeDate.toString)
  }

  private def addAnimeEntry(animeEntry: AnimeEntry): Unit = {
    userAnime(animeEntry.id) = animeEntry
  }

  private def addEpisodesToTimetable(animeEntry: AnimeEntry): Unit = {
    animeEntry.episodesWatched.foreach(episode => {
      if (userTimetable.contains(episode._1)) {
        userTimetable(episode._1) += animeEntry.id
      } else {
        userTimetable(episode._1) = new ArrayBuffer[String]().addOne(animeEntry.id)
      }
    })
  }
}
