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
    val newUser = Api.getAnimeUser(user)
    if (animeUsers.contains(user)) {
      compareNewUserInfo(newUser)
    } else {
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

  def compareNewUserInfo(newUserInfo: AnimeUser): String = {
    val currentUserInfo = animeUsers(newUserInfo.userName)

    val currentEntries = currentUserInfo.userAnime.keySet
    val newEntries = currentUserInfo.userAnime.keySet

    val idsToAdd = newEntries.diff(currentEntries)
    val idsToRemove = currentEntries.diff(newEntries)

    s"The ids that we need to add are $idsToAdd, and the ones that we need to remove are $idsToRemove"
  }
}
