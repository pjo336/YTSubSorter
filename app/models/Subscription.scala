package models

import com.google.api.services.youtube.YouTube
import scala.collection.JavaConversions._

case class UserSubscription(id: String, title: String)

class SubscriptionFinder() {

  /**
   * Forms a tuple compiled of id and title for all youtube accounts the currently authorized user is subscribed to.
   *
   * @param youTubeAccount A Youtube service object with a connected scope
   * @return Tuple formatted (Account_id, Account_Title)
   */
  def getSubscriptionInformationTuple(youTubeAccount:YouTube) = {
    // TODO should this be moved to a different class?
    youTubeAccount.subscriptions.list("snippet").setMine(true).setMaxResults(50).execute.getItems.map(sub =>
      (sub.getSnippet.getChannelId, sub.getSnippet.getTitle))
  }
}
