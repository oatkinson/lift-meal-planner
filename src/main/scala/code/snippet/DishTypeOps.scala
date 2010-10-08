package code.snippet

import code.model.DishType
import xml.{Text, NodeSeq}
import net.liftweb.util.Helpers._
import net.liftweb.http.S._
import net.liftweb.http.{RequestVar, SHtml}

/**
 * The Operations for managing a DishType
 * @author pabstec
 */

class DishTypeOps {
  object currentVar extends RequestVar(new DishType) 
  val currentDishType = currentVar.is

  def list(html: NodeSeq): NodeSeq = {
    DishType.findAll.flatMap(dishType =>
      bind("dishType", html,
        "name" -> dishType.name.asHtml,
        "edit" -> SHtml.link("add", () => currentVar(dishType), Text(?("Edit"))),
        "delete" -> SHtml.link("list", () => dishType.delete_!, Text(?("Delete")))))
  }

  def add(html: NodeSeq): NodeSeq = {
    def doAdd () = {
      if (currentDishType.name.length == 0) {
        error("emptyName", "The dish type's name cannot be blank")
      }
      else if (currentDishType.save) redirectTo("list.html")
    }
    bind("dishType", html, "name" -> currentDishType.name.toForm, "submit" -> SHtml.submit(?("Save"), doAdd))
  }
}