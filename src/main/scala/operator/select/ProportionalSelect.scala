package operator.select

import util.ProbabilityUtils
import util.Types.Population

class ProportionalSelect extends Select {
  override def apply(population: Population, size: Int, unique: Boolean = false): Population = {
    val quantiles = population map((individual) => (individual, individual.fitness))
    val fitnessSum = quantiles map(_._2) sum

    ProbabilityUtils.sample(quantiles.map((individual) => (individual._1, individual._2/fitnessSum)), size, unique)
  }
}
