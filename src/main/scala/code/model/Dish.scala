package code.model

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.common._

/**
 * A Dish as part of a Meal.
 */
class Dish extends LongKeyedMapper[Dish] with IdPK {
  def getSingleton = Dish

  object name extends MappedString(this, 20)
  object dishType extends MappedLongForeignKey(this, DishType)
  object defaultMealTime extends MappedEnum(this, MealTime)
}

object MealTime extends scala.Enumeration {
  val Breakfast, Lunch, Dinner, Snack = Value
}

/**
 * The singleton that has methods for accessing the database
 */
object Dish extends Dish with LongKeyedMetaMapper[Dish]