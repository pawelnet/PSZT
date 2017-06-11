package pszt.eventBus

import operator.Operators
import operator.crossover.PMX
import operator.mutate.AdaptiveGaussianShuffle
import operator.select.ProportionalSelect
import operator.stopcase.IterationNumberStopcase
import solver.MuPlusLambda
import task.TravellingSalesmanTask
import webServer.model.SalesManProblemTaskRequest


object TaskBus {

  var inProgress = false


  def isBusy()= inProgress

  object TaskType extends Enumeration {
    type TaskType = Value
    val TravellingSalesmanTask = Value
  }

  private val solver = new MuPlusLambda(
    operators = Operators(
      new PMX,
      new AdaptiveGaussianShuffle,
      new ProportionalSelect,
      new ProportionalSelect,
      new IterationNumberStopcase(100)
    ),
    mu = 50,
    lambda = 100
  )

  EventBus.taskObservable.filter(_ => !inProgress).doOnNext {
    _ => inProgress = true
  }.subscribe(
    task =>
      task match {
        case (TaskType.TravellingSalesmanTask, SalesManProblemTaskRequest(matrix)) => solver.solve(new TravellingSalesmanTask(matrix,0, 1))
        case _ => throw new Error("Wrong task")
      })
}
