package logging

import pszt.eventBus.EventBus
import pszt.eventBus.EventBus.Iteration
import solution.Solution
import util.Types.{Genotype, Population}

class EventLogger extends Logger {

  override def log(message: String) =
    EventBus.messageObserver onNext(message)

  override def newPopulation[T](population: Population, iteration: Int, bestFenotype: T): Unit =
    EventBus.iterationObserver onNext((population, iteration, bestFenotype))

  override def endOfTask[T](solutionGenotype: Solution, solutionFenotype: T): Unit =
    EventBus.solutionObserver onNext ((solutionGenotype, solutionFenotype))
}
