package api.v1.controllers

import org.bson.types.ObjectId
import play.api.mvc._
import play.api.libs.json._
import common.models.Dao
import play.api.libs.json.JsSuccess
import scala.Some
import api.v1.controllers.auth.Authenticator
import common.validators.Validator
import common.identity.User

class BaseController[Entity](
                              dao: Dao[Entity],
                              format: Format[Entity],
                              authenticator: Authenticator[AnyContent, User],
                              validator: Validator[Entity, User]) extends Controller with Crudable[ObjectId, Action[AnyContent]] {

  private def parseEntity(request: Request[AnyContent]): Option[Entity] = request.body.asJson match {
    case Some(json) => {
      format.reads(json) match {
        case JsSuccess(entity, _) => Some(entity)
        case _ => None
      }
    }
    case _ => None
  }

  def create2() = authenticator.authenticate(parse.anyContent)("create2", {
    (user, request) => {
      for {
        entity <- parseEntity(request)
        if (validator.isValid(entity, user))
      } yield {
        dao.create(entity)
        Ok(format.writes(entity))
      }
    }.getOrElse(BadRequest(""))
  })

  def create() = Action {
    request => {
      parseEntity(request) match {
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
      parseEntity(request) match {
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
