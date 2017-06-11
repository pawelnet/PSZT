package pszt.api

import fs2.{Pipe, Scheduler, Strategy, Task, async, pipe, text}
import io.circe.generic.auto._
import io.circe.parser.decode
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server.websocket._
import org.http4s.websocket.WebsocketBits._
import pszt.eventBus.EventBus.Iteration
import pszt.eventBus.TaskBus.TaskType
import pszt.eventBus.{EventBus, TaskBus}
import solution.Solution
import webServer.model.{IterationResponse, NewTaskResponse, SalesManProblemTaskRequest, SolutionResponse}

object API {

  implicit val scheduler = Scheduler.fromFixedDaemonPool(2)
  implicit val strategy = Strategy.fromFixedDaemonPool(4, threadName = "worker")

  var list: List[Iteration] = List()
  var listSolution: List[(Solution, Any)] = List()
  EventBus.iterationObservable subscribe { o => list = o :: list }
  EventBus.solutionObservable subscribe { o => listSolution = o :: listSolution }


  val service = HttpService {

    case req@POST -> Root / "salesManProblem" =>
      val config = req
        .body
        .through(text.utf8Decode)
        .runLog
        .unsafeRun()
      if (config.length > 0 && !TaskBus.isBusy()) {
        val data = decode[SalesManProblemTaskRequest](config.head).getOrElse(None).asInstanceOf[SalesManProblemTaskRequest]
        EventBus.taskObserver.onNext((TaskType.TravellingSalesmanTask, data))
        Ok((NewTaskResponse("OK").asJson))
      }
      else {
        Ok(NewTaskResponse("REJECTED").asJson)
      }

    case req@GET -> Root / "salesManProblem" =>
      val nw = list.toArray
      list = List()
      Ok(nw.map {
        case (population: List[Solution], iter:Int, best) => IterationResponse(population.map{
          p=>(p.genotype.map(_._2).toList,p.fitness)
        },iter.doubleValue(),best.toString)
//        case (solution: Solution, winner) => SolutionResponse(solution, winner)
      }.asJson)

    case req@GET -> Root / "salesManProblem/result" =>
      val nw = listSolution.toArray
      listSolution = List()
      Ok(nw.map {
        case (solution: Solution, winner) => SolutionResponse(
          (solution.genotype.map(_._2).toList,solution.fitness), winner.toString)
      }.asJson)


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

