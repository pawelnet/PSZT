package logging

import util.Types.Genotype

trait Logger {
  def log(message: String)

  def newPopulation(population: List[Genotype], iteration: Integer, rating: Double)

  def endOfTask(bestPopulation: List[Genotype], iteration: Integer, rating: Double)

}
