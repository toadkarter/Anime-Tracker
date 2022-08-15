package com.example
import org.json4s.DefaultFormats
import org.json4s.native.Json

import java.time._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer


object AnimeUsers {
  val animeUsers = new mutable.HashMap[String, AnimeUser]
  var selectedUser: Option[String] = None

  def addAnimeUser(user: String): String = {
    if (animeUsers.contains(user)) {
      Json(DefaultFormats).write(animeUsers(selectedUser.get).userAnime)
    } else {
      val newUser = Api.getAnimeUser(user)
      animeUsers(newUser.userName) = newUser
      selectedUser = Some(newUser.userName)
      Json(DefaultFormats).write(newUser.userAnime)
    }
  }

  def getThisWeeksEpisodes: String = {
    val currentUser = animeUsers(selectedUser.get)
    val currentDate = LocalDate.now

    val thisWeeksEpisodes = currentUser.getEpisodesByWeek(currentDate)
    println(thisWeeksEpisodes.toString())
    Json(DefaultFormats).write(thisWeeksEpisodes)
  }

  def getSelectedWeeksEpisodes(selectedDate: LocalDate): mutable.Map[String, ArrayBuffer[String]] = {
    val currentUser = animeUsers(selectedUser.get)
    currentUser.getEpisodesByWeek(selectedDate)
  }
}
