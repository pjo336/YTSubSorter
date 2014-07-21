package models

import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.youtube.YouTube
import java.util
import scala.collection.JavaConversions._

class Account {

  val READ_ONLY_SCOPE = "https://www.googleapis.com/auth/youtube.readonly"
  val READ_AND_WRITE_SCOPE= "https://www.googleapis.com/auth/youtube"

  private val Auth: Auth = new Auth()

  /**
   * @return A connection to a youtube service with a read only scope
   */
  def getReadOnlyAccount = {
    val scopes: util.Collection[String] = new util.ArrayList[String]()
    scopes.add(READ_ONLY_SCOPE)
    val credential: Credential = Auth.authorize(scopes, "myuploads")
    new YouTube(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
  }

  /**
   * @return A connection to a youtube service with a read and write scope
   */
  def getReadAndWriteAccount = {
    val scopes: util.Collection[String] = new util.ArrayList[String]()
    scopes.add(READ_AND_WRITE_SCOPE)
    val credential: Credential = Auth.authorize(scopes, "myuploads")
    new YouTube(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
  }

  /**
   * @return A connection to a youtube service with the provided scope
   */
  def getManualScopeAccount(scope:String) = {
    val scopes: util.Collection[String] = new util.ArrayList[String]()
    scopes.add(scope)
    val credential: Credential = Auth.authorize(scopes, "myuploads")
    new YouTube(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
  }

}