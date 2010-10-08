package code.model

import net.liftweb.mapper._
import xml.Text
import org.scala_libs.lift.crud.CRUDOps
import net.liftweb.http.{SHtml, S}

/**
 * A Meal for hungry people to eat.
 * @author pabstec
 */
class Meal extends LongKeyedMapper[Meal] with IdPK {
  def getSingleton = Meal

  object mealTime extends MappedEnum(this, MealTime) {
    override def displayName = "Meal Time"
  }

  def mealDishes: List[MealDish] = MealDish.findAll(By(MealDish.meal, this))
  def dishes: List[Dish] = mealDishes.flatMap(_.dish.obj)
  def dishesHtml = Text(dishes.map(_.name).mkString(", "))

  def dishes(newDishes: List[Dish]): Meal = {
    this.save
    mealDishes.filter(mealDish => !newDishes.contains(mealDish.dish.obj.open_!)).foreach(_.delete_!)
    newDishes.filter(!mealDishes.map(_.dish.obj.open_!).contains(_)).foreach(dish => MealDish.create.meal(this).dish(dish).save)
    this
  }
}

object Meal extends Meal with LongKeyedMetaMapper[Meal] with CRUDOps[Long,Meal] {
  def instanceName = "Meal"

  override def calcFields = super.calcFields ++ Seq(
    Field(Text("Dishes"), editDisplay_$qmark = true, viewDisplay_$qmark = true,
      toHtml = (meal: Meal) => meal.dishesHtml,
      toForm = (meal: Meal) => meal.dishesHtml :: <br/> ::
              SHtml.multiSelect(Dish.findAll.map(dish => dish.id.is.toString -> dish.name.is),
                meal.dishes.map(_.id.toString), (dishIds) => meal.dishes(dishIds.map(_.toLong).map(Dish.find(_).open_!))) :: Nil
    )
  )
}

/**
 * A many-to-many relationship between Meal and Dish.
 */
class MealDish extends LongKeyedMapper[MealDish] with IdPK {
  def getSingleton = MealDish

  object meal extends LongMappedMapper(this, Meal)
  object dish extends LongMappedMapper(this, Dish)
}

object MealDish extends MealDish with LongKeyedMetaMapper[MealDish]
