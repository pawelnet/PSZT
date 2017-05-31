package solver

import logging.Logger
import operator.Operators
import task.Task
import util.Types._

abstract class GenericEvolutionarySolver(operators: Operators,
                                         populationSize: Int,
                                         logger: Option[Logger] = None) extends Solver {

  protected def crossover(population: List[Genotype]): List[Genotype]
  protected def mutate(genotype: Genotype): Genotype
  protected def reproduce(population: Population, iteration: Int): Population
  protected def success(parentPopulation: Population, offspringPopulation: Population): Population

  override def solve[T](task: Task[T]): T = {
    def best(population: Population) = population maxBy(_.fitness)

    def it(population: Population, itNumber: Int = 0): Population = {
      if (logger.isDefined) logger.get.newPopulation(population, itNumber, task decode best(population))

      if (operators.stopcaseOp(population, itNumber)) population
      else {
        val parentPopulation = reproduce(population, itNumber)
        val offspringChromosomes = crossover(parentPopulation map(_.genotype)) map mutate

        it(success(parentPopulation, offspringChromosomes.map(task.solution)), itNumber + 1)
      }
    }

    val resultGenotype = best(it(task.initPopulation(populationSize)))
    val resultFenotype = task decode resultGenotype

    if (logger.isDefined) logger.get.endOfTask(resultGenotype, resultFenotype)
    resultFenotype
  }
}
