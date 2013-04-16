package common.validators

/**
 * Validator - decide whether the Entity is valid in this Context.
 * Provide the ability to make an Entity valid
 *
 * @tparam Entity
 * @tparam Context
 */
trait Validator[Entity,Context] {
  def isValid(e:Entity, c:Context) : Boolean
  def makeValid(e:Entity, c:Context) : Entity
}
