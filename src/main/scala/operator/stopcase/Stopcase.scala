package operator.stopcase

import solution.Solution

trait Stopcase {
  def apply(population: List[Solution], iteration: Int): Boolean
}
