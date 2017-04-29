package solver

import logging.Logger
import operator.Operators
import solution.Solution
import util.Types.Genotype

class OnePlusOne(operators: Operators,
                 mutationDecreaseRatio: Double,
                 mutationIncreaseRatio: Double,
                 mutationEvaluationInterval: Int,
                 logger: Option[Logger] = None) extends GenericEvolutionaryStrategy(operators, 1, 1, logger) {

  private var successfulIterations = 0
  private var mutationEvaluationCounter = 0
  private var successRate = 0D

  override protected def reproduce(population: List[Solution], iteration: Int): List[Solution] = {
    successRate = successfulIterations/iteration
    population
  }

  override protected def success(parentPopulation: List[Solution], offspringPopulation: List[Solution]): List[Solution] = {
    mutationEvaluationCounter += 1

    if (parentPopulation.head.fitness > offspringPopulation.head.fitness) {
      successfulIterations += 1
      parentPopulation
    } else offspringPopulation
  }

  override protected def mutate(genotype: Genotype): Genotype = {
    operators.mutateOp {
      if (mutationEvaluationCounter == mutationEvaluationInterval) {
        val scale = if (successRate > 0.2) mutationDecreaseRatio else if (successRate < 0.2) mutationIncreaseRatio else 1
        List(genotype.head, genotype(1) map(_ * scale))
      } else genotype
    }
  }
}
