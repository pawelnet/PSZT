package task

import solution.Solution
import util.Types.{Genotype, Population}

trait Task[Fenotype] {
  def initPopulation(size: Int): Population
  def solution(genotype: Genotype): Solution
  def decode(x: Solution): Fenotype
}
