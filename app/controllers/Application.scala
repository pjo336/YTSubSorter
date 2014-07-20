package controllers

import play.api.mvc._
import models.Account
import scala.collection.JavaConversions._

// TODO this needs a better location
case class UserSubscription(id:String, title:String)

object Application extends Controller {

  def index = Action {
    val account = new Account
    val subscriptionInfo = account.getSubscriptionInformationTuple(account.getReadAndWriteAccount)
      .map(sub => UserSubscription(sub._1, sub._2))
    val userSubscriptions = subscriptionInfo.toList
    // Etho video id
    val key = "EaYjtR105XQ"
    // original Google dev vieo id
    val originalKey = "M7lc1UVf-VE"
    Ok(views.html.index(key, userSubscriptions))
  }

}