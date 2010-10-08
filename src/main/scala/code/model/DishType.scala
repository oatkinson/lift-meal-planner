package code.model

import net.liftweb.mapper.{LongKeyedMetaMapper, IdPK, MappedString, LongKeyedMapper}

/**
 * A Dish Type such as beverage, dessert, entree, salad, etc.
 * @author pabstec
 */

class DishType extends LongKeyedMapper[DishType] with IdPK {
  def getSingleton = DishType
  
  object name extends MappedString(this, 25)
}

object DishType extends DishType with LongKeyedMetaMapper[DishType]