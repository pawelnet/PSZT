package operator.crossover

import util.Types.Genotype

trait Crossover {
  def apply(x: Genotype, y: Genotype): List[Genotype]
}
