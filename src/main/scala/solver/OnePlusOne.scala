package solver

import logging.Logger
import operator.Operators
import util.ChromosomeType
import util.Types.{Genotype, Population}

class OnePlusOne(operators: Operators,
                 mutationDecreaseRatio: Double,
                 mutationIncreaseRatio: Double,
                 mutationEvaluationInterval: Int,
                 logger: Option[Logger] = None) extends GenericEvolutionaryStrategy(operators, 1, 1, logger) {

  private var successfulIterations = 0
  private var mutationEvaluationCounter = 0
  private var successRate = 0D

  override protected def reproduce(population: Population, iteration: Int): Population = {
    successRate = successfulIterations/iteration
    population
  }

  override protected def success(parentPopulation: Population, offspringPopulation: Population): Population = {
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
        genotype map {
          case (ChromosomeType.STANDARD_DEVIATION.toString, chromosome) => (ChromosomeType.STANDARD_DEVIATION.toString, chromosome map(_ * scale))
          case other => other
        }
      } else genotype
    }
  }
}
