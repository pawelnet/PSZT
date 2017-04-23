package pszt


abstract class Solver() {
  def generatePopulation: Vector[Chromosome]

  def probabilityGenerator: Any=>Double

  def stopCase(population: Vector[Chromosome]): Boolean
}

