package common.validators

import common.models.Item
import common.identity.User

object ItemValidator extends Validator[Item,User]{


  def isValid(item:Item,user:User) : Boolean = true

  def makeValid(item:Item,user:User) : Item = item

}
