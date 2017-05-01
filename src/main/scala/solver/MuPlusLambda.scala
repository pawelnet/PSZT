package solver

import logging.Logger
import operator.Operators
import util.Types.Population

class MuPlusLambda(operators: Operators,
                   mu: Int,
                   lambda: Int,
                   logger: Option[Logger] = None) extends GenericEvolutionaryStrategy(operators, mu, lambda, logger) {

  override protected def reproduce(population: Population, iteration: Int): Population =
    operators.reproduceOp(population, lambda, withReplacement = true)

  override protected def success(parentPopulation: Population, offspringPopulation: Population): Population =
    operators.successOp(parentPopulation ::: offspringPopulation, mu)
}
