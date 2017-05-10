package util

import scala.util.Random

object ProbabilityUtils {
  def sample[T](quantiles: List[(T, Double)], size: Int, withReplacement: Boolean = false): List[T] = {
    def sampleIt(quantiles: List[(T, Double)], sampled: Int = 0, result: List[T] = List()): List[T] = {
      def findBucket(probability: Double, buckets: List[(T, Double)] = quantiles): (T, Double) =
        if (probability < buckets.head._2) buckets.head else findBucket(probability - buckets.head._2, buckets.tail)

      if (sampled >= size) result
      else {
        val bucket = findBucket(Random.nextDouble - 1 + (quantiles map(_._2) sum))
        sampleIt(if (withReplacement) quantiles else quantiles filter(_._1 != bucket._1), sampled + 1, bucket._1 :: result)
      }
    }

    if (!withReplacement && quantiles.length < size) throw new IllegalArgumentException()

    sampleIt(quantiles sortWith(_._2 > _._2))
  }

  def sampleUnif[T](quantiles: List[T], size: Int, withReplacement: Boolean = false): List[T] = {
    val quantilesSize = quantiles.length
    sample(quantiles map((_, 1D/quantilesSize)), size, withReplacement)
  }
}
