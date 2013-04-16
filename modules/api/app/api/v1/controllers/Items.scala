package api.v1.controllers

import common.models.Item
import common.validators.Validator
import common.identity.User
import api.v1.controllers.auth.MockAuthenticator

object MockValidator extends Validator[Item, User] {

  def isValid(i: Item, u: User) = true

  def makeValid(i: Item, u: User) = i
}

object Items extends BaseController[Item](Item, Item.Format, MockAuthenticator, MockValidator)

