package logging

import pszt.eventBus.EventBus
import util.Types.Genotype

class EventLogger extends Logger {

  override def log(message: String) =
    EventBus.getMessageObserver().onNext(message)

  override def newPopulation(population: List[Genotype], iteration: Integer, rating: Double): Unit =
    EventBus.getIterationObserver().onNext((population, iteration, rating))

  override def endOfTask(bestPopulation: List[Genotype], iteration: Integer, rating: Double): Unit =
    EventBus.getIterationObserver().onNext((bestPopulation, iteration, rating))
}
