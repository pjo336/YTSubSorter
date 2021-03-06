package models

import com.google.api.services.youtube.YouTube
import scala.collection.JavaConversions._

trait SubscriptionChannel {
  def getChannelOfSubscriptionByTitle(ytService: YouTube, title: String) = {
    ytService.channels().list("snippet,contentDetails").setForUsername(title)
      .execute()
  }

  // TODO this retrieves the authorized users channel for some reason
  def getChannelOfSubscriptionById(ytService: YouTube, id: String) = {
    ytService.channels().list("contentDetails").setId(id)
      .execute()
  }
}

object Channel extends SubscriptionChannel{

}
