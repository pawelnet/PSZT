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

  override def solve[F](task: Task[F]): F = {
    def it(population: Population, itNumber: Int = 0): Population = {
      if (logger.isDefined) logger.get.log()

      if (operators.stopcaseOp(population, itNumber)) population
      else {
        val parentPopulation = reproduce(population, itNumber)
        val offspringChromosomes = crossover(parentPopulation map(_.genotype)) map mutate

        it(success(parentPopulation, offspringChromosomes.map(task.solution)), itNumber + 1)
      }
    }

    task decode it(task.initPopulation(populationSize)).maxBy(_.fitness)
  }
}
