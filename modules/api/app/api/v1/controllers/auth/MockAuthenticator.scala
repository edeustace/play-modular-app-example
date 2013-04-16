package api.v1.controllers.auth

import play.api.mvc._
import scala.Some
import common.identity.User

object MockAuthenticator extends AuthActionBuilder[AnyContent, User] {

  def ActionWithContext(p: BodyParser[AnyContent])(action: String, fn: (User, Request[AnyContent]) => Result) = Action(p) {
    r =>

      def getUser(r: Request[AnyContent]): Option[User] = {
        Some(User("ed"))
      }

      def isAllowed(u: User, action: String): Boolean = {
        true
      }

      val result = for {
        user <- getUser(r)
        if (isAllowed(user, action))}
      yield fn(user, r)

      result match {
        case Some(executedResult) => executedResult
        case _ => Results.Unauthorized("Unauthorized")
      }
  }

}
