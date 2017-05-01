package operator.stopcase

import util.Types.Population

trait Stopcase {
  def apply(population: Population, iteration: Int): Boolean
}
