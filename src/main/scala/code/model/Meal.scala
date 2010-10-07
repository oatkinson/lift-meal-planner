package code.model

import net.liftweb.mapper._

/**
 * A Meal for hungry people to eat.
 * @author pabstec
 */
class Meal extends LongKeyedMapper[Meal] with IdPK {
  def getSingleton = Meal

  object mealTime extends MappedEnum(this, MealTime)

  def dishes: List[Dish] = MealDish.findAll(By(MealDish.meal, this)).flatMap(_.dish.obj)
}

object Meal extends Meal with LongKeyedMetaMapper[Meal]

/**
 * A many-to-many relationship between Meal and Dish.
 */
class MealDish extends LongKeyedMapper[MealDish] with IdPK {
  def getSingleton = MealDish

  object meal extends LongMappedMapper(this, Meal)
  object dish extends LongMappedMapper(this, Dish)
}

object MealDish extends MealDish with LongKeyedMetaMapper[MealDish]
