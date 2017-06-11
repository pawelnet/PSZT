package webServer.model

import pszt.eventBus.EventBus.Iteration
import solution.Solution
import util.Types.Population


case class SolutionResponse(solution: (List[List[Double]], Double), winner: String)


