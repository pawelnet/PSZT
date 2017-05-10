package operator.select

import util.Types.Population

trait Select {
  def apply(population: Population, size: Int, unique: Boolean = true): Population
}
