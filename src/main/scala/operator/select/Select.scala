package operator.select

import solution.Solution

trait Select {
  def apply(population: List[Solution], size: Int, withReplacement: Boolean = false): List[Solution]
}
