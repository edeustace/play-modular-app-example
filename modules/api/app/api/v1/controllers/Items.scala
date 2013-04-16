package api.v1.controllers

import common.models.Item
import org.bson.types.ObjectId
import play.api.mvc.{Action, AnyContent, AnyContentAsJson, Controller}
import play.api.libs.json.Json

object Items extends Controller with Crudable[ObjectId, Action[AnyContent]] {

  def create() = Action {
    request => {
      request.body.asJson match {
        case Some(json) => {
          json.asOpt[Item] match {
            case Some(item) =>{
              Item.create(item)
              Ok(Json.toJson(item))
            }
            case _ => BadRequest("...")
          }
        }
        case _ => BadRequest(".a")
      }
    }
  }

  def read(id: ObjectId) = Action {
    request => {
      Item.findOneById(id) match {
        case Some(item) => Ok(Json.toJson(item))
        case _ => NotFound("not found")
      }
    }
  }

  def update(id: ObjectId) = Action {
    request => {
      request.body.asJson match {
        case Some(json) => {
          json.asOpt[Item] match {
            case Some(item) =>{
              Item.update(item.copy(id = id))
              Ok(Json.toJson(item))
            }
            case _ => BadRequest("...")
          }
        }
        case _ => BadRequest(".a")
      }
    }
  }

  def delete(id: ObjectId) = Action {
    request => {
      Item.findOneById(id) match {
        case Some(item) => {
          Item.delete(item)
          Ok("")
        }
        case _ => NotFound("not deleted")
      }
    }
  }

  def list() = Action {
    request => {
      val items = Item.list()
      Ok(Json.toJson(items))
    }
  }
}