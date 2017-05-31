package logging

import solution.Solution
import util.Types.Population

trait Logger {
  def log(message: String)

  def newPopulation[T](population: Population, iteration: Int, bestFenotype: T)

  def endOfTask[T](solutionGenotype: Solution, solutionFenotype: T)

}
