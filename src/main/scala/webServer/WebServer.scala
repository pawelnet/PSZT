package pszt.api

import fs2.{Pipe, Pure, Scheduler, Strategy, Stream, Task, async, pipe, text}
import io.circe.parser.decode
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server.websocket._
import org.http4s.websocket.WebsocketBits._
import pszt.eventBus.EventBus.Iteration
import pszt.eventBus.{EventBus, TaskBus}
import pszt.eventBus.TaskBus.TaskType
import webServer.model.{NewTaskResponse, ResponseStatus, SalesManProblemTaskRequest}
object API {

  implicit val scheduler = Scheduler.fromFixedDaemonPool(2)
  implicit val strategy = Strategy.fromFixedDaemonPool(8, threadName = "worker")

  private var stream = Stream.empty
    var  streams:  List[Stream[Pure,Iteration]] = List()
  EventBus.getIterationObservable().subscribe(s => {
    streams=  Stream.emit(s).pure :: streams
  })

  val service = HttpService {

    case req@POST -> Root / "salesManProblem" =>
      val config = req
        .body
        .through(text.utf8Decode)
        .runLog
        .unsafeRun()
      if (config.length > 0 && !TaskBus.isBusy()) {
        val data = decode[SalesManProblemTaskRequest](config.head).getOrElse(None).asInstanceOf[SalesManProblemTaskRequest]
        EventBus.getTaskObserver().onNext((TaskType.TravellingSalesmanTask, data))
        Ok((NewTaskResponse("OK").asJson))
      }
      else {
        Ok(NewTaskResponse("REJECTED").asJson)
      }
    //    case req@GET -> Root / "salesManProblem" =>
    //      Ok(queue.map(q => q.dequeue).unsafeRun())

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

