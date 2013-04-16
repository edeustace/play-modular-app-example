package api.v1.controllers

trait Crudable[IdType,ResultType]{

  def create() : ResultType
  def read(id:IdType) : ResultType
  def update(id:IdType) : ResultType
  def delete(id:IdType) : ResultType
  def list() : ResultType
}