package test.api

import fs2.text
import org.http4s
import org.http4s.dsl.uri
import org.http4s.{Method, Request}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import pszt.api.API
import pszt.eventBus.EventBus
import io.circe.syntax._
import fs2.Stream
import pszt.eventBus.EventBus.Iteration
import webServer.model.SalesManProblemTaskRequest

@RunWith(classOf[JUnitRunner])
class ApiTests extends FunSuite {

  test("GET /HeartBeat ping") {
    val getRoot = Request(Method.GET, uri("/hearbeat"))

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert("OK" === result.head)
  }


  test("POST /salesManProblem new task with no input") {
    EventBus.taskObserver.onCompleted()
    val getRoot = Request(Method.POST, uri("/salesManProblem"), null, null, http4s.EmptyBody)

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("{\"status\":\"REJECTED\"}") === result)
  }

  test("POST /salesManProblem new task with data") {
    EventBus.taskObserver.onCompleted()

    val json = SalesManProblemTaskRequest(List(List(1, 1), List(1, 1))).asJson.toString()


    var bytes = json.toCharArray.map(_.toByte)
    val bytes1 = Stream.emits(bytes)

    val getRoot = Request(Method.POST,
      uri("/salesManProblem"), null, null, bytes1)

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("{\"status\":\"OK\"}") === result)
  }


  test("GET /salesManProblem update empty data") {
    EventBus.taskObserver.onCompleted()
    val getRoot = Request(Method.GET,
      uri("/salesManProblem"))

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("[]") === result)
  }

  test("GET /salesManProblem update no empty data") {
    EventBus.taskObserver.onCompleted()
    EventBus.iterationObserver.onNext((Nil,1,1.0))
    val getRoot = Request(Method.GET,
      uri("/salesManProblem"))

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("[{\"population\":[],\"iter\":1.0,\"best\":\"1.0\"}]") === result)
  }


  test("POST /salesManProblem new task with data and update") {


    val json = SalesManProblemTaskRequest(List(List(1, 1), List(1, 1))).asJson.toString()


    var bytes = json.toCharArray.map(_.toByte)
    val bytes1 = Stream.emits(bytes)

    val getRoot = Request(Method.POST,
      uri("/salesManProblem"), null, null, bytes1)

    val response = API.service.run(getRoot).unsafeRun
    val result = response.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("{\"status\":\"OK\"}") === result)

    val getRootGET = Request(Method.GET,
      uri("/salesManProblem"))

    val responseGET = API.service.run(getRootGET).unsafeRun
    val resultGET = responseGET.toOption
      .get.body
      .through(text.utf8Decode)
      .runLog
      .unsafeRun()
    assert(Vector("[]") === resultGET)

  }
}
