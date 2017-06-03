package task

import operator.Operators
import operator.crossover.PMX
import operator.mutate.AdaptiveGaussianShuffle
import operator.select.ProportionalSelect
import operator.stopcase.IterationNumberStopcase
import org.scalatest.FlatSpec
import solver.MuPlusLambda

class TravellingSalesmanTaskSpec extends FlatSpec {
  val solver = new MuPlusLambda(
    operators = Operators(
      new PMX,
      new AdaptiveGaussianShuffle,
      new ProportionalSelect,
      new ProportionalSelect,
      new IterationNumberStopcase(100)
    ),
    mu = 50,
    lambda = 100
  )

  val result = solver.solve(new TravellingSalesmanTask(List(
  //wawa olszt tor gdan bial lubl lodz kiel
    List(0,192,201,309,192,167,129,168),
    List(192,0,164,209,148,343,256,353),
    List(201,164,0,162,333,365,165,301),
    List(309,148,162,0,356,473,320,447),
    List(192,209,333,356,0,223,321,334),
    List(167,343,165,473,233,0,243,155),
    List(129,256,165,320,321,243,0,140),
    List(168,353,301,447,334,155,140,0)
  ), 0, 7))

  print(result)
}
