package task

import solution.Solution
import util.ChromosomeTypes
import util.Types.{Chromosome, Genotype, Population}

import scala.util.Random

class TravellingSalesmanTask(nodes: List[List[Int]]) extends Task[List[Int]] {
  override def initPopulation(size: Int): Population = {
    val initialValues = 1 to nodes.length map(_.toDouble)

    val population = for(i <- 1 to size) yield solution(Map(
      ChromosomeTypes.Values -> Random.shuffle(initialValues).toList,
      ChromosomeTypes.StandardDeviation -> initialValues.map((_) => Random.nextGaussian).toList
    ))

    population.toList
  }

  override def solution(genotype: Genotype): Solution = {
    def fitnessIt(gene: Double, chromosome: Chromosome, fitness: Double = 0): Double = {
      if (chromosome.isEmpty) fitness
      else fitnessIt(chromosome.head, chromosome.tail, fitness + nodes(gene.toInt)(chromosome.head.toInt))
    }

    val valueVector = genotype(ChromosomeTypes.Values)

    Solution(genotype, 1/fitnessIt(valueVector.head, valueVector))
  }

  override def decode(solution: Solution): List[Int] = solution genotype ChromosomeTypes.Values map(_.toInt)
}
