import {runNewAlgorithm} from 'api/RunService'

// define action types
export const SET_CONFIG_RUN_SUCCESS = 'SET_CONFIG_RUN_SUCCESS'
export const RUN_NEW_ALGORITHM_SUCCESS = 'RUN_NEW_ALGORITHM_SUCCESS'

export const SET_CONFIG_RUN = (config = {}) => {
  return {type: SET_CONFIG_RUN_SUCCESS, result: config}
}

export const RUN_NEW_ALGORITHM = async (data = {}) => {
  console.log('RUN_NEW_ALGORITHM')
  let result = await runNewAlgorithm(data)
  return {type: RUN_NEW_ALGORITHM_SUCCESS, result: result.data}
}
