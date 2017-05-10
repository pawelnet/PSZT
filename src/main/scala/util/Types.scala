package util

import solution.Solution
/**
object ChromosomeType extends Enumeration {
  val VALUES, STANDARD_DEVIATION = Value

  import scala.language.implicitConversions

  implicit def asString(value: Value): String = value.toString
}**/

object ChromosomeType {
  val VALUES = "VALUES"
  val STANDARD_DEVIATION = "STANDARD_DEVIATION"
}

object Types {
  type Chromosome = List[Double]
  type Genotype = Map[String, Chromosome]
  type Population = List[Solution]
}
