package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import play.api._
import play.api.libs.iteratee.Enumerator
import play.api.libs.iteratee.Iteratee
import play.api.mvc._
import scala.concurrent.duration._
import play.api.libs.concurrent.Promise

object WebSockets extends Controller {

  def statusFeed = WebSocket.using[String] { implicit request =>
    val in = Iteratee.ignore[String]
    val out = Enumerator.repeatM {
      Promise.timeout(getLoadAverage, 3 seconds)
    }
    (in, out)
  }
  
  def getLoadAverage: String = {
    Random.nextInt(100) + "%"
  }
}