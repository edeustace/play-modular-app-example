package api.v1.controllers

import org.bson.types.ObjectId
import play.api.mvc.{Request, Controller, AnyContent, Action}
import play.api.libs.json._
import common.models.{Dao, Item}
import scala.Some
import play.api.libs.json.JsSuccess


class BaseController[Entity](dao: Dao[Entity], format: Format[Entity]) extends Controller with Crudable[ObjectId, Action[AnyContent]] {

  private def getEntity(request: Request[AnyContent]): Option[Entity] = request.body.asJson match {
    case Some(json) => {
      format.reads(json) match {
        case JsSuccess(entity, _) => Some(entity)
        case _ => None
      }
    }
    case _ => None
  }

  def create() = Action {
    request => {
      getEntity(request) match {
        case Some(entity) => {
          dao.create(entity)
          Ok(format.writes(entity))
        }
        case _ => BadRequest("..")
      }
    }
  }

  def read(id: ObjectId) = Action {
    request => {
      dao.findOneById(id) match {
        case Some(entity) => Ok(format.writes(entity))
        case _ => NotFound("not found")
      }
    }
  }

  def update(id: ObjectId) = Action {
    request => {
      getEntity(request) match {
        case Some(entity) => {
          dao.update(entity)
          Ok(format.writes(entity))
        }
        case _ => BadRequest("..")
      }
    }
  }

  def delete(id: ObjectId) = Action {
    request => {
      dao.findOneById(id) match {
        case Some(entity) => {
          dao.delete(entity)
          Ok("")
        }
        case _ => NotFound("not deleted")
      }
    }
  }

  def list() = Action {
    request => {
      val entities = dao.list()
      Ok(Json.toJson(entities.map(format.writes)))
    }
  }

}
