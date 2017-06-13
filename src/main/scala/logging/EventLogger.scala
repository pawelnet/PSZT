package logging

import pszt.eventBus.EventBus
import pszt.eventBus.EventBus.Iteration
import solution.Solution
import util.Types.{Genotype, Population}

class EventLogger extends Logger {

  override def log(message: String) =
    EventBus.messageObserver onNext(message)

  override def newPopulation[T](population: Population, iteration: Int, best: T): Unit =
    EventBus.iterationObserver onNext((population, iteration, best))

  override def endOfTask[T](solutionGenotype: Solution, solution: T): Unit =
    EventBus.solutionObserver onNext ((solutionGenotype, solution))
}
