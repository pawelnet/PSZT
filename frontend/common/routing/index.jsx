import React from 'react'
import {Redirect, Switch} from 'react-router'
import {createBrowserHistory} from 'history'
import {App, IterationStats, ResultStats, Run} from 'containers'
import {RouteAuth} from 'components'

export const history = getHistory()

export const appRouting = [
  {
    path: '/run',
    name: 'Run',
    exact: true,
    icon: 'rocket',
    sidebarVisible: true,
    tag: RouteAuth,
    component: Run
  },
  {
    path: '/result',
    name: 'Result',
    exact: true,
    icon: 'comments outline',
    sidebarVisible: true,
    tag: RouteAuth,
    component: ResultStats
  },
  {
    path: '/iterationStatistics',
    name: 'Iteration statistics',
    exact: true,
    icon: 'bar chart',
    sidebarVisible: true,
    tag: RouteAuth,
    component: IterationStats
  }
    // {
    //   path: '/auth',
    //   name: 'Auth',
    //   tag: Route,
    //   component: Login
    // }
]

/**
 * Returns application routing with protected by AuthCheck func routes
 * @param {Function} authCheck checks is user logged in
 */
export const Routing = authCheck => {
    // remove components that aren't application routes, (e.g. github link in sidebar)
  let routes = appRouting.filter(a => a.tag || a.component)
    // render components that are inside Switch (main view)
  let routesRendered = routes.map((a, i) => {
        // get tag for Route. is it RouteAuth `protected route` or Route?
    let Tag = a.tag
    let {path, exact, strict, component} = a
        // can visitor access this route?
    let canAccess = authCheck
        // select only props that we need
    let b = {path, exact, strict, component, canAccess}
    return <Tag key={i} {...b} />
  })

  return (
        <App>
            <Switch>
                {routesRendered}
                <Redirect to="/run"/>
            </Switch>
        </App>
  )
}

function getHistory () {
  const basename = ''

  return createBrowserHistory({basename})
}
