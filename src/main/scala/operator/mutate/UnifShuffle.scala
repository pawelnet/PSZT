package operator.mutate

import util.{ListUtils, ChromosomeTypes}
import util.Types._

import scala.util.Random

class UnifShuffle extends Mutate {
  override def apply(genotype: Genotype): Genotype = {
    val valueVector = genotype(ChromosomeTypes.Values)
    val valueVectorLength = valueVector.length

    genotype map {
      case (ChromosomeTypes.Values, _) => (ChromosomeTypes.Values, ListUtils.shuffle(_ => Random.nextInt(valueVectorLength))(valueVector))
      case other => other
    }
  }
}
