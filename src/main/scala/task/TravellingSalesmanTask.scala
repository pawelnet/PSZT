package task

import solution.Solution
import util.Types.Genotype

class TravellingSalesmanTask(nodes: List[List[Int]]) extends Task[List[Int]] {
  override def initPopulation(size: Int): List[Solution] = ???

  override def solution(genotype: Genotype): Solution = ???

  override def decode(x: Solution): List[Int] = ???
}
