package api.v1.controllers.auth

import play.api.mvc.{Action, Result, Request, BodyParser}

trait AuthActionBuilder[A,Context] {

  /**
   * Creates an action that executes a block that takes a Context as well as a Request
   * @param p
   * @param action
   * @param fn - The action body that uses the Context along with the Request
   * @return
   */
  def ActionWithContext(p: BodyParser[A])(action: String, fn: (Context, Request[A]) => Result): Action[A]
}
