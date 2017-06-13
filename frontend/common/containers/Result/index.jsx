import React, {Component} from 'react'
import {connect} from 'react-redux'
import PropTypes from 'prop-types'
import ResultStatsGraphComponent from './components/ResultStatsGraphComponent'
import {GET_POPULATION_STATISTICS} from 'actions/stats'
import {Segment, Divider} from 'semantic-ui-react'
class ResultStats extends Component {
  static propTypes = {
    population: PropTypes.array,
    getPopulation: PropTypes.func.isRequired,
    winner: PropTypes.array,
    config: PropTypes.object
  };

  componentDidMount () {
    this.props.getPopulation()
  }

  render () {
    let {population, winner, config} = this.props
    let props = {population, winner, config}

    return <div>
            {population.length === 0 ? (<Segment padded>
                <Divider horizontal>No data</Divider>
            </Segment>)
                : <ResultStatsGraphComponent {...props} />}
        </div>
  }
}

function mapStateToProps (state) {
  console.log('statae', state)
  return {
    population: state.stats.population ? state.stats.population.population.map((score, iteration) => {
      return {
        score, iteration
      }
    }) : [],
    winner: state.stats.population && state.stats.population.winnner,
    config: state.run
  }
}

function mapDispatchToProps (dispatch) {
  return {
    getPopulation: async () => {
      let result = await GET_POPULATION_STATISTICS()
      dispatch(result)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ResultStats)
