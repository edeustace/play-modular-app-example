package api.v1.controllers

import common.models.Item

object Items extends BaseController[Item](Item, Item.Format)

