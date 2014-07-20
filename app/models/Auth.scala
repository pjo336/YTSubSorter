package models

import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.client.util.store.DataStore
import com.google.api.client.auth.oauth2.StoredCredential
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp

import java.io._

class Auth {

  /**
   * Define a global instance of the HTTP transport.
   */
  val HTTP_TRANSPORT: HttpTransport = new NetHttpTransport()

  /**
   * Define a global instance of the JSON factory.
   */
  val JSON_FACTORY: JsonFactory = new JacksonFactory()

  /**
   * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
   */
  val CREDENTIALS_DIRECTORY = ".oauth-credentials"

  /**
   * Authorizes the installed application to access user's protected data.
   *
   * @param scopes              list of scopes needed to run youtube upload.
   * @param credentialDatastore name of the credential datastore to cache OAuth tokens
   */
  def authorize(scopes: java.util.Collection[String], credentialDatastore: String): Credential = {
    // TODO load client secrets from website rather than hard coded below

    val is: InputStream = new ByteArrayInputStream(SecretInfo.GOOGLE_AUTH_API_JSON.getBytes)

    val clientSecretReader: Reader = new InputStreamReader(is)
    val clientSecrets: GoogleClientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader)

    // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
    val fileDataStoreFactory: FileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY))
    val datastore: DataStore[StoredCredential] = fileDataStoreFactory.getDataStore(credentialDatastore)

    val flow: GoogleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
      HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
      .build()

    // Build the local server and bind it to port 8080
    val localReceiver: LocalServerReceiver = new LocalServerReceiver.Builder().setPort(8082).build()

    // Authorize.
    new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user")
  }
}