package com.example

case class AnimeEpisode(date: String, number: Int, var watched: Boolean) {
  def toggleEpisodeWatched(): Unit = {
    watched = !watched
  }
}
