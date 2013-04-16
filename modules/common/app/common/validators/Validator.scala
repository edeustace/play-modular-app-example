package common.validators

trait Validator[Entity,Context] {
  def isValid(e:Entity, c:Context) : Boolean
  def makeValid(e:Entity, c:Context) : Entity
}
