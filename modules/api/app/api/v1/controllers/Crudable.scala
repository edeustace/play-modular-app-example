package api.v1.controllers

/** Crud abstraction
 * @tparam IdType - The type that allows for identification
 * @tparam ResultType - The type of the result of the operation - eg: String, Json ...
 */
trait Crudable[IdType,ResultType]{

  def create() : ResultType
  def read(id:IdType) : ResultType
  def update(id:IdType) : ResultType
  def delete(id:IdType) : ResultType
  def list() : ResultType
}