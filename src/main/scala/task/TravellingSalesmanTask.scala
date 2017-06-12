package task

import solution.Solution
import util.ChromosomeTypes
import util.Types.{Gene, Chromosome, Genotype, Population}

import scala.util.Random

class TravellingSalesmanTask(nodes: List[List[Int]], start: Int, end: Int) extends Task[List[Int]] {
  override def initPopulation(size: Int): Population = {
    val initialValues = nodes.indices filter(i => i != start && i != end) map(_.toDouble)

    val population = for(i <- 1 to size) yield solution(Map(
      ChromosomeTypes.Values -> Random.shuffle(initialValues).toList,
      ChromosomeTypes.StandardDeviation -> initialValues.map((_) => Random.nextGaussian).toList
    ))

    population.toList
  }

  override def solution(genotype: Genotype): Solution = {
    def fitnessIt(gene: Gene, chromosome: Chromosome, fitness: Double = 0): Double = {
      if (chromosome.isEmpty) fitness
      else fitnessIt(chromosome.head, chromosome.tail, fitness + nodes(gene.toInt)(chromosome.head.toInt))
    }

    val valueVector = genotype(ChromosomeTypes.Values)

    Solution(genotype, 1000/(fitnessIt(valueVector.head, valueVector) + nodes(start)(valueVector.head.toInt) + nodes(valueVector.last.toInt)(end)))
  }

  override def decode(solution: Solution): List[Int] = start :: (solution genotype ChromosomeTypes.Values map(_.toInt)) ::: List(end)
}
