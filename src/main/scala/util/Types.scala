package util

import solution.Solution

trait ChromosomeType
object ChromosomeTypes {
  case object Values extends ChromosomeType
  case object StandardDeviation extends ChromosomeType
}

object Types {
  type Chromosome = List[Double]
  type Genotype = Map[ChromosomeType, Chromosome]
  type Population = List[Solution]
}
