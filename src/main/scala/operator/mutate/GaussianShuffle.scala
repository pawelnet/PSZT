package operator.mutate

import util.{ListUtils, ChromosomeTypes}
import util.Types.Genotype

import scala.util.Random

class GaussianShuffle(standardDeviation: Double = 1) extends Mutate {
  override def apply(genotype: Genotype): Genotype = {
    genotype map {
      case (ChromosomeTypes.Values, _) => (ChromosomeTypes.Values,
        ListUtils.shuffle(i => (i + standardDeviation * Random.nextGaussian).round.toInt)(genotype(ChromosomeTypes.Values)))
      case other => other
    }
  }
}
