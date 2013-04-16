package api.v1.controllers

import common.models.Item
import common.validators.ItemValidator
import api.v1.controllers.auth.MockAuthenticator

object Items extends BaseController[Item](Item, Item.Format, MockAuthenticator, ItemValidator)

