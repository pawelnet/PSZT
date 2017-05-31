package operator.stopcase

import util.Types.Population

class BestFitnessChangeStopcase(threshold: Double) extends Stopcase {
  private var currentBestFitness = 10e-10

  override def apply(population: Population, iteration: Int): Boolean = {
    val bestFitness = population map (_.fitness) max
    val lastBestFitness = currentBestFitness
    currentBestFitness = bestFitness
    (bestFitness - lastBestFitness)/lastBestFitness > threshold
  }
}
