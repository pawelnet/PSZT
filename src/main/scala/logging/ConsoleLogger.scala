package logging

import solution.Solution
import util.Types.Population

class ConsoleLogger extends Logger {
  override def log(message: String): Unit = println(message)

  override def newPopulation[T](population: Population, iteration: Int, bestFenotype: T): Unit =
    print((population maxBy (_.fitness)).fitness.toString + ',')

  override def endOfTask[T](solutionGenotype: Solution, solutionFenotype: T): Unit = log("END")
}
