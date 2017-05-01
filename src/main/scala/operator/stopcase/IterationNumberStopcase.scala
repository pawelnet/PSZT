package operator.stopcase

import util.Types.Population

class IterationNumberStopcase(limit: Int) extends Stopcase {
  override def apply(population: Population, iteration: Int): Boolean = iteration >= limit
}
