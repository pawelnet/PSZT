package solver

import logging.Logger
import operator.Operators
import solution.Solution

class MuLambda(operators: Operators,
               mu: Int,
               lambda: Int,
               logger: Option[Logger] = None) extends GenericEvolutionaryStrategy(operators, mu, lambda, logger) {

  override protected def reproduce(population: List[Solution], iteration: Int): List[Solution] =
    operators.reproduceOp(population, lambda, withReplacement = true)

  override protected def success(parentPopulation: List[Solution], offspringPopulation: List[Solution]): List[Solution] =
    operators.successOp(offspringPopulation, mu)
}

