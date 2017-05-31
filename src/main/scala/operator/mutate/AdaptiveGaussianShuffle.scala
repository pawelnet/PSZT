package operator.mutate

import util.Types._
import util.{ChromosomeTypes, ListUtils}

import scala.util.Random

class AdaptiveGaussianShuffle extends Mutate {
  override def apply(genotype: Genotype): Genotype = {
    val valueVector = genotype(ChromosomeTypes.Values)
    val alpha = 1/Math.pow(2 * valueVector.length, 1/2)
    val beta = 1/Math.pow(2 * Math.pow(valueVector.length, 1/2), 1/2)
    val gaussianSd = Random.nextGaussian
    val sdVector = genotype(ChromosomeTypes.StandardDeviation) map(_ * Math.exp(gaussianSd * alpha + Random.nextGaussian * beta))

    genotype map {
      case (ChromosomeTypes.Values, _) => (ChromosomeTypes.Values,
        ListUtils.shuffle(i => (i + Random.nextGaussian * sdVector(i)).round.toInt)(valueVector))
      case (ChromosomeTypes.StandardDeviation, _) => (ChromosomeTypes.StandardDeviation, sdVector)
      case other => other
    }
  }
}
