package util

import scala.util.Random

object ProbabilityUtils {
  def sample[T](quantiles: List[(T, Double)], size: Int, withReplacement: Boolean = false): List[T] = {
    def sampleIt(quantiles: List[(T, Double)], sampled: Int = 0, result: List[T] = List()): List[T] = {
      def findBucket(probability: Double, buckets: List[(T, Double)] = quantiles): T =
        if (probability < buckets.head._2) buckets.head._1 else findBucket(probability - buckets.head._2, buckets.tail)

      if (sampled >= size) result
      else sampleIt(if (withReplacement) quantiles else quantiles.tail, sampled + 1, findBucket(Random.nextDouble) :: result)
    }

    sampleIt(quantiles sortWith(_._2 > _._2))
  }

  def sampleUnif[T](quantiles: List[T], size: Int, withReplacement: Boolean = false): List[T] = {
    val quantilesSize = quantiles.length
    sample(quantiles map((_, 1D/quantilesSize)), size, withReplacement)
  }
}
