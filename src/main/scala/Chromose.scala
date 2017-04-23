package pszt

abstract class Chromosome(genes: Vector[Genotype]) {
  def reproduce(mutate: Any) = ???

  def mutate: Chromosome

  def crossover(chromosome: Chromosome): (Chromosome,Chromosome)

  def eval: Double
}