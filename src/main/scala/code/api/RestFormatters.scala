package code {
package api {

import java.text.SimpleDateFormat

import scala.xml.{Elem, NodeSeq, Text}

import net.liftweb.common.{Box,Empty,Failure,Full}
import net.liftweb.mapper.{By,MaxRows}
import net.liftweb.json.JsonAST.{JObject,JValue}
import net.liftweb.http.js.JsExp

import model._

/**
 * This object provides some conversion and formatting specific to our
 * REST API.
 */
object RestFormatters {
  /* The REST timestamp format. Not threadsafe, so we create
   * a new one each time. */
  def timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

  // A simple helper to generate the REST ID of an Dish
  def restId (d : Dish) =
    "http://www.liftmealplanner.com/api/dish/" + d.id

  /**
   * Generates the XML REST representation of an Dish
   */
  def toXML (d : Dish) : Elem =
    <dish>
      <id>{restId(d)}</id>
      <name>{d.name}</name>
      <dish-type>{d.dishType}</dish-type>
      <default-meal-time>{d.defaultMealTime}</default-meal-time>
    </dish>

}
  
// Close package statements
}}