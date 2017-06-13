import {post} from './utils'

export async function runNewAlgorithm (data) {
  return post('http://localhost:8080/salesManProblem', data)
}
