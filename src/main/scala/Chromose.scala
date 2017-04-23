package pszt

abstract class Chromosome(genes: Vector[Genotype]) {
  def reproduce(mutate: Any) = ???

  def mutate(fn: Vector[Genotype] => Vector[Genotype]): Chromosome

  def crossover(chromosome: Chromosome): Chromosome

  def eval: Double
}