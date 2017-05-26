package webServer.model

import io.circe.generic.JsonCodec
import webServer.model.ResponseStatus.Status


object ResponseStatus extends Enumeration {
  type Status = Value
  val OK, REJECTED = Value
}


@JsonCodec case class NewTaskResponse(status: Status)