package webServer.model

import io.circe.generic.JsonCodec


@JsonCodec case class SalesManProblemTaskRequest(matrix: List[List[Int]])