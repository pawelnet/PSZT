import {get} from './utils'

export async function getIterationStatisctics (offset = 0) {
  return get(`http://localhost:8080/salesManProblem/iteration/${offset}`)
}
