package solver

import logging.Logger
import operator.Operators
import solution.Solution
import task.Task
import util.Types._

abstract class GenericEvolutionarySolver(operators: Operators,
                                         populationSize: Int,
                                         logger: Option[Logger] = None) extends Solver {

  protected abstract def crossover(population: List[Genotype]): List[Genotype]
  protected abstract def mutate(genotype: Genotype): Genotype
  protected abstract def reproduce(population: List[Solution], iteration: Int): List[Solution]
  protected abstract def success(parentPopulation: List[Solution], offspringPopulation: List[Solution]): List[Solution]

  override def solve[F](task: Task[F]): F = {
    def it(population: List[Solution], itNumber: Int = 0): List[Solution] = {
      if (operators.stopcaseOp(population, itNumber)) population
      else {
        val parentPopulation = reproduce(population, itNumber)
        val offspringChromosomes = crossover(parentPopulation map(_.genotype)) map mutate

        if (logger.isDefined) logger.get.log()

        it(success(parentPopulation, offspringChromosomes.map(task.solution)), itNumber + 1)
      }
    }

    task decode it(task.initPopulation(populationSize)).maxBy(_.fitness)
  }
}
