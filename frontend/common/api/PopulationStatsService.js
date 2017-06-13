import {get} from './utils'

export async function getPopulationStatisctics (offset = 0) {
  return get(`http://localhost:8080/salesManProblem/population`)
}
