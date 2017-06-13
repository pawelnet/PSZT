import {
    SET_CONFIG_RUN_SUCCESS, RUN_NEW_ALGORITHM_SUCCESS
} from 'actions/run'
import {LOCATION_CHANGE} from 'actions/common'

export const initialState = {
  config: null
}

export function run (state = initialState, action) {
  console.log('run', action)
  switch (action.type) {
    case SET_CONFIG_RUN_SUCCESS:

      return {
        ...state,
        config: action.result,
        cities: action.result.distances.length ? new Set([].concat(...action.result.distances.map(c => [c.from, c.to]))) : new Set()
      }
    case RUN_NEW_ALGORITHM_SUCCESS:

      return {
        ...state,
        isRunning: true
      }
    case LOCATION_CHANGE: {
      return {
        ...state,
        isRunning: false
      }
    }
    default:
      return state
  }
}
