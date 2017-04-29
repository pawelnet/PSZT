package solver

import logging.Logger
import operator.Operators
import util.Types.Genotype

abstract class GenericEvolutionaryStrategy(operators: Operators,
                                           mu: Int,
                                           lambda: Int,
                                           logger: Option[Logger] = None) extends GenericEvolutionarySolver(operators, mu, logger) {

  override protected def crossover(population: List[Genotype]): List[Genotype] = {
    def crossoverIt(x: Genotype, rest: List[Genotype], result: List[Genotype] = List()): List[Genotype] = {
      if (rest.isEmpty) if(result.isEmpty) List(x) else result
      else crossoverIt(rest.head, rest.tail, operators.crossoverOp(x, rest.head) :: result)
    }

    crossoverIt(population.head, population.tail)
  }

  override protected def mutate(genotype: Genotype): Genotype = operators.mutateOp(genotype)
}
