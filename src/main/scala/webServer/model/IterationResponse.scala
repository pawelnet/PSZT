package webServer.model

import pszt.eventBus.EventBus.Iteration
import solution.Solution
import util.Types.Population

case class IterationResponse(population: List[(List[List[Double]],Double)],iter:Double,best:String)



