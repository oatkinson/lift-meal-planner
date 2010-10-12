package code.api


/**
 * User: atkinsonos
 * Date: Oct 11, 2010
 * Time: 10:34:37 AM
 */

import net.liftweb.http.rest.XMLApiHelper
import net.liftweb.http._
import scala.xml.{Elem,Node,NodeSeq,Text}
import net.liftweb.mapper.By
import net.liftweb.common.{Box,Empty,Failure,Full,Logger}
import code.model._

object DispatchRestAPI extends XMLApiHelper {
  final val logger = Logger("com.pocketchangeapp.api.DispatchRestAPI")

  import RestFormatters._

  /**
   * This method provides the dispatch hooks for our REST API that
   * we'll hook into LiftRules.dispatch
   */
  def dispatch: LiftRules.DispatchPF = {
    // Define our getters first
    case Req(List("api", "dish", Dish(dish)), _, GetRequest) =>
      () => nodeSeqToResponse(toXML(dish)) // default to XML

    // Invalid API request - route to our error handler
    case Req("api" :: x :: Nil, "", _) =>
      () => BadResponse() // Everything else fails
  }

  def createTag (xml : NodeSeq) : Elem = <lmp_api>{xml}</lmp_api>

}

