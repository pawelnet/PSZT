package operator.mutate

import util.Types.Genotype

trait Mutate {
  def apply(genotype: Genotype): Genotype
}
