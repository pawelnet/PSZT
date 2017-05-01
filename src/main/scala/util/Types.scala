package util

import solution.Solution

object ChromosomeType extends Enumeration {
  val VALUES, STANDARD_DEVIATION = Value

  import scala.language.implicitConversions

  implicit def asString(value: Value): String = value.toString
}

object Types {
  type Chromosome = List[Double]
  type Genotype = Map[String, Chromosome]
  type Population = List[Solution]
}
