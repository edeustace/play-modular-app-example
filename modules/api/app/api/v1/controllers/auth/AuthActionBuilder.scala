package api.v1.controllers.auth

import play.api.mvc.{Action, Result, Request, BodyParser}

trait AuthActionBuilder[A,Context] {

  def ActionWithContext(p: BodyParser[A])(action: String, fn: (Context, Request[A]) => Result): Action[A]
}
