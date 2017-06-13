import {combineReducers} from 'redux'
import {routerReducer} from 'react-router-redux'

import {layout} from './layout'
import {auth} from './auth'
import {loginCR} from './login_component_reducer'

import {stats} from './stats'
import {run} from './run'

export const rootReducer = combineReducers({
  stats,
  run,
  layout,

  auth,

  loginCR,
  routing: routerReducer
})
