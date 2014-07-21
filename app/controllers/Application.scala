package controllers

import play.api.mvc._
import models.{Channel, SubscriptionFinder, UserSubscription, Account}
import scala.collection.JavaConversions._


object Application extends Controller {

  def index = Action {
    val youtubeService = new Account().getReadAndWriteAccount
    val subscriptionFinder = new SubscriptionFinder
    val subscriptionInfo = subscriptionFinder.getSubscriptionInformationTuple(youtubeService)
      .map(sub => UserSubscription(sub._1, sub._2))
    // userSubscriptions is a list of tuples of all subscriptions as (id, title)
    val userSubscriptions = subscriptionInfo.toList

    val rhino = userSubscriptions(0)
    val channelSubscribedTo = Channel.getChannelOfSubscriptionByTitle(youtubeService, rhino.title)
    val channelDetails = channelSubscribedTo.getContentDetails
    val channelsUploadsPlaylistId = channelDetails.getRelatedPlaylists.getUploads
    val playlist = youtubeService.playlistItems().list("contentDetails").setPlaylistId(channelsUploadsPlaylistId).execute()
    val firstId = playlist.getItems.toList(0).getContentDetails.getVideoId
    // Etho video id
    val key = "EaYjtR105XQ"
    // original Google dev vieo id
    val originalKey = "M7lc1UVf-VE"
    Ok(views.html.index(firstId, userSubscriptions))
  }

}