import { getIterationStatisctics } from 'api/IterationStatsService'
import { getPopulationStatisctics } from 'api/PopulationStatsService'
import { resultOK } from 'api/utils'

// define action types
export const GET_ITERATION_STATISTICS_SUCCESS = 'GET_ITERATION_STATISTICS_SUCCESS'
export const GET_ITERATION_STATISTICS_FAIL = 'GET_ITERATION_STATISTICS_FAIL'
export const GET_POPULATION_STATISTICS_SUCCESS = 'GET_POPULATION_STATISTICS_SUCCESS'
export const GET_POPULATION_STATISTICS_FAIL = 'GET_POPULATION_STATISTICS_FAIL'

export const GET_ITERATION_STATISTICS = async (offset = 0) => {
  let result = await getIterationStatisctics(offset)
  if (resultOK(result)) {
    return { type: GET_ITERATION_STATISTICS_SUCCESS, result: result.data }
  }
  return { type: GET_ITERATION_STATISTICS_FAIL, error: result.data }
}

export const GET_POPULATION_STATISTICS = async (offset = 0) => {
  let result = await getPopulationStatisctics(offset)
  if (resultOK(result)) {
    return { type: GET_POPULATION_STATISTICS_SUCCESS, result: result.data }
  }
  return { type: GET_POPULATION_STATISTICS_FAIL, error: result.data }
}
