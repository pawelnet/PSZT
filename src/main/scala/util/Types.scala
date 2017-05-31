package util

import solution.Solution

trait ChromosomeType
object ChromosomeTypes {
  case object Values extends ChromosomeType
  case object StandardDeviation extends ChromosomeType
}

object Types {
  type Gene = Double
  type Chromosome = List[Gene]
  type Genotype = Map[ChromosomeType, Chromosome]
  type Population = List[Solution]
}
