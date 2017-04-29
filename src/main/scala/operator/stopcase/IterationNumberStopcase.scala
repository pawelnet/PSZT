package operator.stopcase

import solution.Solution

class IterationNumberStopcase(limit: Int) extends Stopcase {
  override def apply(population: List[Solution], iteration: Int): Boolean = iteration >= limit
}
