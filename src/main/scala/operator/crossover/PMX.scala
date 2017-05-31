package operator.crossover

import util.Types.{Gene, Chromosome, Genotype}
import util.{ChromosomeTypes, ListUtils}

import scala.util.Random

class PMX extends Crossover {
  override def apply(x: Genotype, y: Genotype): List[Genotype] = {
    def swapIt(values: Chromosome, swap: (Gene, Gene), swaps: List[(Gene, Gene)]): Chromosome =
      if (swaps.isEmpty) values else swapIt(ListUtils.swap(values, swap._1, swap._2), swaps.head, swaps.tail)

    val VALUES = ChromosomeTypes.Values

    val xValues = x(VALUES)
    val yValues = y(VALUES)

    val indexes = List(Random.nextInt(xValues.length), Random.nextInt(xValues.length))
    val start= indexes.min
    val end = indexes.max

    val xSub = xValues.slice(start, end)
    val ySub = yValues.slice(start, end)

    val pairs = xSub zip ySub

    List(x, y) map(genotype => genotype map {
      case (VALUES, _) => (VALUES, if (pairs.isEmpty) genotype(VALUES) else swapIt(genotype(VALUES), pairs.head, pairs.tail))
      case other => other
    })
  }
}
