import React, {Component} from 'react'
import {connect} from 'react-redux'
import PropTypes from 'prop-types'
import GraphStatsComponent from './components/GraphStatsComponent'
import {GET_ITERATION_STATISTICS} from 'actions/stats'
import Slider, {createSliderWithTooltip} from 'rc-slider'
import 'rc-slider/assets/index.css'
import _ from 'lodash'
import {Segment, Divider} from 'semantic-ui-react'

function percentFormatter (v) {
  return `iteration: ${v} `
}
const SliderWithTooltip = createSliderWithTooltip(Slider)
const style = {width: 800, margin: 50}

class IterationStats extends Component {
  static propTypes = {
    iteration: PropTypes.array.isRequired,
    getIteration: PropTypes.func.isRequired,
    iterationIndex: PropTypes.number
  };

  shouldComponentUpdate (nextProps) {
    return !_.isEqual(nextProps.iterationIndex, this.props.iterationIndex) || !_.isEqual(nextProps.iteration.length, this.props.iteration.length)
  }

  componentDidMount () {
    this.props.getIteration()
  }

  update (e) {
    this.props.getIteration(e)
  }

  render () {
    console.log('iterationa: ', this.props.iteration)
    let {iteration, iterationIndex} = this.props
    let props = {iteration, iterationIndex}

    return <div>

            {iteration.length === 0 ? (<Segment padded>
                <Divider horizontal>No data</Divider></Segment>) : (
                    <div>
                <div style={style}>
                    <p>Select iteration</p>
                    <SliderWithTooltip
                        tipFormatter={percentFormatter}
                        tipProps={{overlayClassName: 'foo'}}
                        onAfterChange={this.update.bind(this)}
                    />
                </div>
                < GraphStatsComponent {...props} />
            </div>)
            }

        </div>
  }
}

function mapStateToProps (state) {
  console.log('state update', state)
  return {
    iteration: state.stats.iteration[0] ? state.stats.iteration[0].population.map((p, i) => {
      return {
        name: i,
        score: p.fitness
      }
    }) : [],
    iterationIndex: state.stats.iteration[0] && state.stats.iteration[0].iter
  }
}
function mapDispatchToProps (dispatch) {
  return {
    getIteration: async (offset) => {
      let result = await GET_ITERATION_STATISTICS(offset)
      dispatch(result)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(IterationStats)
