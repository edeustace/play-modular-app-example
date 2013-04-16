package common.models

import org.bson.types.ObjectId
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import play.api.libs.json._
import play.api.libs.json.JsObject
import play.api.libs.json.JsSuccess
import play.api.Play.current
import common.context._

case class Item(title: String, id: ObjectId = new ObjectId())

object Item {

  private object Dao extends ModelCompanion[Item, ObjectId] {
    val collection = se.radley.plugin.salat.mongoCollection("items")
    val dao = new SalatDAO[Item, ObjectId](collection = collection) {}
  }

  implicit object Format extends play.api.libs.json.Format[Item] {
    def reads(json: JsValue): JsResult[Item] = JsSuccess {
      val baseItem = Item(
        (json \ "title").as[String]
      )

      (json \ "id").asOpt[String] match {
        case Some(id) => baseItem.copy(id = new ObjectId(id))
        case _ => baseItem
      }
    }

    def writes(i: Item): JsObject = JsObject {
      Seq(
        "id" -> JsString(i.id.toString),
        "title" -> JsString(i.title)
      )
    }
  }

  def create(i: Item): Item = {
    Dao.save(i)
    i
  }

  def update(i: Item): Item = {
    Dao.save(i)
    i
  }

  def delete(i: Item): Item = {
    Dao.remove(i)
    i
  }

  def list(): List[Item] = Dao.findAll().toList

  def findOneById(id: ObjectId): Option[Item] = Dao.findOneById(id)
}


