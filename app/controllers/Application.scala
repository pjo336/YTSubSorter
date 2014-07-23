package controllers

import play.api.mvc._
import models.{Channel, SubscriptionFinder, UserSubscription, Account}
import scala.collection.JavaConversions._


object Application extends Controller {

  def index = Action {
    // TODO needs heavy refactoring
    val youtubeService = new Account().getReadAndWriteAccount
    val subscriptionFinder = new SubscriptionFinder
    val subscriptionInfo = subscriptionFinder.getSubscriptionInformationTuple(youtubeService)
      .map(sub => UserSubscription(sub._1, sub._2))
    // userSubsList is a list of tuples of all subscriptions as (id, title)
    val userSubsList = subscriptionInfo.toList

    var videoList:List[(String, String)] = List()
    userSubsList.map(sub => {
      println()
      println("Parsing User - " + sub.title)
      println()
      val channelResponse = Channel.getChannelOfSubscriptionByTitle(youtubeService, sub.title)
      val channelSubscribedTo = channelResponse.getItems.toList
      channelSubscribedTo.map(channel => {
        val channelDetails = channel.getContentDetails
        val channelsUploadsPlaylistId = channelDetails.getRelatedPlaylists.getUploads
        val playlist = youtubeService.playlistItems().list("contentDetails, " +
          "snippet").setPlaylistId(channelsUploadsPlaylistId)
          .execute().getItems.toList
        playlist.map(item => {
          val details = item.getContentDetails
          println(sub.title + ": " + details.getVideoId + " created on: " + item.getSnippet.getPublishedAt)
          videoList = (sub.title, details.getVideoId) :: videoList
        })
      })
    })

    println()
    println("Final List: ")
    println(videoList)


    // Etho video id
    val key = "EaYjtR105XQ"
    // original Google dev vieo id
    val originalKey = "M7lc1UVf-VE"
    Ok(views.html.index(key, userSubsList))
  }

}