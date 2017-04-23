package pszt

import scala.annotation.tailrec

object Simple {
  type Rating = Double
  type Population = List[Chromosome]
  type Distrubution = Double
  type PopulationDistributed = List[(Chromosome, Distrubution)]
  type Speciman = Chromosome


  private def evaluteWholePopulation(population: Population): List[(Chromosome, Rating)] =
    population map (chromosome => (chromosome, chromosome.eval)) sortWith (_._2 > _._2)


  private def preparePopulationWithDistributon(population: PopulationDistributed, sum: Double, result: PopulationDistributed): PopulationDistributed =
    population match {
      case Nil => result
      case last :: Nil => preparePopulationWithDistributon(Nil, 1, result :+ (last._1, 1))
      case _ =>
        val newSum = sum + population.head._2
        preparePopulationWithDistributon(Nil, newSum, result :+ (population.head._1, newSum))
    }

  private def crossoverPopulation(probabilityGenerator: (Any) => Double)(newGeneration: Population) = {
    val markedSpeciemanToReproduce = geussPairsToReproduceFrom(newGeneration)(probabilityGenerator)
    val notDesignetToReproduce = markedSpeciemanToReproduce filter (!_._2) map (_._1)
    val crossedOffspring = (markedSpeciemanToReproduce filter (_._2) map (_._1) grouped (2) map {
      case parentA :: parentB :: Nil => parentA crossover parentB
    }).foldLeft(List[Speciman]()) { (result, offspring) =>
      offspring match {
        case (childA, childB) => childA :: childB :: result
      }
    }
    crossedOffspring ::: notDesignetToReproduce

  }

  private def takeForPopulation(population: Population)(value: Double): Chromosome = {

    val ratedPopulation = evaluteWholePopulation(population)
    val sumOfRates = ratedPopulation map (_._2) reduce (_ + _)
    val populationWithRelativeRates = ratedPopulation map { case (chromosome, rating) => chromosome -> (rating / sumOfRates) } sorted
    val populationWithDistribution = preparePopulationWithDistributon(populationWithRelativeRates, 0, Nil)

    populationWithDistribution find (value > _._2) getOrElse (throw new Error("It is not possible, there is no character in population")) _1
  }


  private def drawNewPoolToReproduceFrom(population: Population)(generator: Any => Double): Population = {
    val getToReproduceFor = takeForPopulation(population)
    for (i <- 1 to population.length) yield {
      getToReproduceFor(generator())
    }
  }


  def geussPairsToReproduceFrom(newGeneration: Population)(probabilityGenerator: Any => Double): List[(Chromosome, Boolean)] =
    for (specimen <- newGeneration) yield (specimen, probabilityGenerator(specimen) == 1)


  private def iter(population: List[Chromosome], probabilityGenerator: Any => Double, caseStop: Population => Boolean): Population =
    if (caseStop(population)) population
    else {
      val newPool = drawNewPoolToReproduceFrom(population)(probabilityGenerator)
      val crossedPopulation = crossoverPopulation(probabilityGenerator)(newPool)
      val newGeneration = crossedPopulation map (specimen => specimen mutate)
      iter(newGeneration, probabilityGenerator, caseStop)
    }

  //return best population sorted by eval fn
  def run(solver: Solver): Population = iter(solver.generatePopulation, solver.probabilityGenerator, solver.stopCase)


}
