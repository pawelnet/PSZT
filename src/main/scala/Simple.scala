package pszt

import scala.annotation.tailrec

object Simple {
  type Rating = Double
  type Population = List[Chromosome]
  type Distrubution = Double
  type PopulationDistributed = List[(Chromosome, Distrubution)]

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


  def geussPairsToReproduceFrom(newGeneration: Population)(probabilityGenerator: Any => Double): (Population, Population) = {
    val populationWithMarkToReproduce = for (specimen <- newGeneration) yield (specimen, probabilityGenerator(specimen) == 1)
    val specimensToCross = populationWithMarkToReproduce filter (_._2) map (_._1)
    val specimensSplitted = specimensToCross splitAt (specimensToCross.length / 2)
    specimensSplitted._1 zip specimensSplitted._2 map {
      case (parentA: Chromosome, parentB: Chromosome) => parentA crossover parentB
      case _ => _
    }
        populationWithMarkToReproduce filter(!_._2)
  }

  private def iter(population: List[Chromosome], probabilityGenerator: Any => Double, caseStop: Population => Boolean): Population =
    if (caseStop(population)) population
    else {
      val newGeneration = drawNewPoolToReproduceFrom(population)(probabilityGenerator)
      geussPairsToReproduceFrom(newGeneration)(probabilityGenerator)


    }

  //return best population sorted by eval fn
  def run(solver: Solver): Population = iter(solver.generatePopulation, solver.probabilityGenerator, solver.stopCase)


}
