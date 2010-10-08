package code {
package model {

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.openid._
import _root_.org.openid4java.discovery.DiscoveryInformation
import _root_.org.openid4java.message.AuthRequest

object MyVendor extends SimpleOpenIDVendor  {
  def ext(di: DiscoveryInformation, authReq: AuthRequest): Unit = {
    import WellKnownAttributes._
    WellKnownEndpoints.findEndpoint(di) map {ep =>
      ep.makeAttributeExtension(List(Email, FullName, FirstName, LastName)) foreach {ex =>
        authReq.addExtension(ex)
      }
    }
  }
  override def createAConsumer = new OpenIDConsumer[UserType] {
    beforeAuth = Full(ext _)
  }
}

/**
 * The singleton that has methods for accessing the database
 */
object User extends User with MetaOpenIDProtoUser[User] {
  override def dbTableName = "users" // define the DB table name
  override def screenWrap = Full(<lift:surround with="default" at="content">
			       <lift:bind /></lift:surround>)
  // define the order fields will appear in forms and output
  override def fieldOrder = List(id, firstName, lastName, email,
  locale, timezone, password, textArea)
  def openIDVendor = MyVendor

  // comment this line out to require email validations
  override def skipEmailValidation = true
}

/**
 * An O-R mapped "User" class that includes first name, last name, password and we add a "Personal Essay" to it
 */
class User extends OpenIDProtoUser[User] {
  def getSingleton = User // what's the "meta" server

  // define an additional field for a personal essay
  object textArea extends MappedTextarea(this, 2048) {
    override def textareaRows  = 10
    override def textareaCols = 50
    override def displayName = "Personal Essay"
  }
}

}
}
