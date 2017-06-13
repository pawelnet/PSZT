package logging

import solution.Solution
import util.Types.Population

trait Logger {
  def log(message: String)

  def newPopulation[T](population: Population, iteration: Int, best: T)

  def endOfTask[T](solutionGenotype: Solution, solution: T)

}
