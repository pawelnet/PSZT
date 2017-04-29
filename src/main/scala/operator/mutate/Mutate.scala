package operator.mutate

import util.Types.Genotype

trait Mutate {
  def apply(x: Genotype): Genotype
}
