package operator.stopcase

import util.Types.Population

class ComplexStopcase(stopcases: Stopcase*) extends Stopcase {
  override def apply(population: Population, iteration: Int): Boolean = stopcases exists (_(population, iteration))
}
