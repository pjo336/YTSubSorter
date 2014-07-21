package models

import com.google.api.services.youtube.YouTube
import scala.collection.JavaConversions._

trait SubscriptionChannel {
  def getChannelOfSubscriptionByTitle(ytService: YouTube, title: String) = {
    ytService.channels().list("snippet,contentDetails").setForUsername(title)
      .execute().getItems.toList(0)
  }

  // TODO this retrieves the authorized users channel for some reason
  def getChannelOfSubscriptionById(ytService: YouTube, id: String) = {
    ytService.channels().list("contentDetails").setId(id)
      .execute().getItems.toList(0)
  }
}

object Channel extends SubscriptionChannel{

}
