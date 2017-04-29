package task

import solution.Solution
import util.Types.Genotype

trait Task[Fenotype] {
  def initPopulation(size: Int): List[Solution]
  def solution(genotype: Genotype): Solution
  def decode(x: Solution): Fenotype
}
