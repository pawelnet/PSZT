package task

import solution.Solution
import util.Types.{Genotype, Population}

trait Task[T] {
  def initPopulation(size: Int): Population
  def evaluate(genotype: Genotype): Solution
  def solution(x: Solution): T
}
