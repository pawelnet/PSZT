package pszt


abstract class Solver() {
  def generatePopulation: List[Chromosome]

  def probabilityGenerator: Any => Double

  def stopCase: List[Chromosome] => Boolean
}

