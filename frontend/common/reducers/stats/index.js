import {
    GET_ITERATION_STATISTICS_SUCCESS,
    GET_ITERATION_STATISTICS_FAIL,
    GET_POPULATION_STATISTICS_SUCCESS
} from 'actions/stats'
import {LOCATION_CHANGE} from 'actions/common'

export const initialState = {
  iteration: []
}

export function stats (state = initialState, action) {
  switch (action.type) {
    case GET_ITERATION_STATISTICS_SUCCESS:
      return {
        ...state,
        iteration: action.result
      }
    case GET_POPULATION_STATISTICS_SUCCESS:
      return {
        ...state,
        population: action.result
      }
    case GET_ITERATION_STATISTICS_FAIL:
      return state
    case LOCATION_CHANGE: {
      if (action.payload.pathname !== '/') {
        return initialState
      }
      return state
    }
    default:
      return state
  }
}
