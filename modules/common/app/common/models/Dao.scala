package common.models

import org.bson.types.ObjectId

trait Dao[Entity] {
  def create(i:Entity) : Entity
  def findOneById(id:ObjectId) : Option[Entity]
  def update(i:Entity) : Entity
  def delete(i:Entity) : Entity
  def list() : List[Entity]
}
