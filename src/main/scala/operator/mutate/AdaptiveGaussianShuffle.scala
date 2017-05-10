package operator.mutate

import util.{ListUtils, ChromosomeType}
import util.Types._

import scala.util.Random

class AdaptiveGaussianShuffle extends Mutate {
  override def apply(genotype: Genotype): Genotype = {
    val valueVector = genotype(ChromosomeType.VALUES)
    val alpha = 1/Math.pow(2 * valueVector.length, 1/2)
    val beta = 1/Math.pow(2 * Math.pow(valueVector.length, 1/2), 1/2)
    val gaussianSd = Random.nextGaussian
    val sdVector = genotype(ChromosomeType.STANDARD_DEVIATION) map(_ * Math.exp(gaussianSd * alpha + Random.nextGaussian * beta))

    def it(result: List[Double], index: Int = 0): List[Double] = {
      if (index >= result.length) result
      else it(ListUtils.swap(result, index, (index + Random.nextGaussian * sdVector(index)).round.toInt), index + 1)
    }

    genotype map {
      case (ChromosomeType.VALUES.toString, _) => (ChromosomeType.VALUES.toString, it(valueVector))
      case (ChromosomeType.STANDARD_DEVIATION.toString, _) => (ChromosomeType.STANDARD_DEVIATION.toString, sdVector)
      case (key, value) => (key, value)
    }
  }
}
