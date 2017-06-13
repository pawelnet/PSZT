package pszt.server
import java.util.concurrent.{ExecutorService, Executors}

import fs2.{Stream, Task}
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.middleware.{CORS, CORSConfig}
import org.http4s.util.StreamApp
import pszt.api.API
import scala.concurrent.duration._
import scala.util.Properties.envOrNone


object BlazeExample extends StreamApp {


  val port : Int              = envOrNone("HTTP_PORT") map (_.toInt) getOrElse 8080
  val ip   : String           = "0.0.0.0"
  val pool : ExecutorService  = Executors.newCachedThreadPool()
  val methodConfig = CORSConfig(
    anyOrigin = true,
    anyMethod = true,
    allowCredentials = true,
    maxAge = 1.day.toSeconds)
  override def stream(args: List[String]): Stream[Task, Nothing] =
    BlazeBuilder
      .bindHttp(port, ip)
      .mountService(CORS(API.service,methodConfig))
      .withServiceExecutor(pool)
      .serve
}
