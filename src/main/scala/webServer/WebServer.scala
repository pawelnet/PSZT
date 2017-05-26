package pszt.api

import fs2.{Pipe, Scheduler, Strategy, Task, async, pipe, text}
import io.circe.parser.decode
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server.websocket._
import org.http4s.websocket.WebsocketBits._
import pszt.eventBus.{EventBus, TaskBus}
import pszt.eventBus.TaskBus.TaskType
import webServer.model.{NewTaskResponse, ResponseStatus, SalesManProblemTaskRequest}

object API {

  implicit val scheduler = Scheduler.fromFixedDaemonPool(2)
  implicit val strategy = Strategy.fromFixedDaemonPool(8, threadName = "worker")

  val service = HttpService {

    case req@POST -> Root / "salesManProblem" =>
      val config = req
        .body
        .through(text.utf8Decode)
        .runLog
        .unsafeRun()
        .head
      val data = decode[SalesManProblemTaskRequest](config).getOrElse(None).asInstanceOf[SalesManProblemTaskRequest]
      val isBusy = TaskBus.isBusy();
      EventBus.getTaskObserver().onNext((TaskType.TravellingSalesmanTask, data))
      Ok(NewTaskResponse(if (isBusy) ResponseStatus.OK else ResponseStatus.REJECTED).asJson)


    case GET -> Root / "hearbeat" => Ok(s"OK")

    case GET -> Root / "wsecho" =>
      val queue = async.unboundedQueue[Task, WebSocketFrame]
      val echoReply: Pipe[Task, WebSocketFrame, WebSocketFrame] = pipe.collect {
        case Text(msg, _) => Text("You sent the server: " + msg)
        case _ => Text("Something new")
      }

      queue.flatMap { q =>
        val d = q.dequeue.through(echoReply)
        val e = q.enqueue
        WS(d, e)
      }


  }
}

