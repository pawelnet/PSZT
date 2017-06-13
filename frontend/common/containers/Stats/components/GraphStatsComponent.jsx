import React, {Component} from 'react'
import PropTypes from 'prop-types'
import { // LineChart, Line,
    Legend, ComposedChart, Bar, XAxis, YAxis, Tooltip
} from 'recharts'
import _ from 'lodash'

class GraphStatsComponent extends Component {
  static propTypes = {
    iteration: PropTypes.array.isRequired,
    iterationIndex: PropTypes.number
  };

  shouldComponentUpdate (nextProps) {
    console.log('next iter: ', nextProps)
    return !_.isEqual(nextProps.iterationIndex, this.props.iterationIndex) || !_.isEqual(nextProps.iteration.length, this.props.iteration.length)
  }

  render () {
    const {iteration} = this.props
    return (
            <ComposedChart width={1000} height={600} data={iteration}
                           margin={{top: 20, right: 80, bottom: 20, left: 20}}>
                <XAxis dataKey="name"/>
                <YAxis label="Index"/>
                <Tooltip/>
                <Legend/>
                {/* <CartesianGrid stroke='#f5f5f5'/> */}
                {/* <Area type='monotone' dataKey='amt' fill='#8884d8' stroke='#8884d8'/> */}
                <Bar dataKey='score' barSize={20} fill='#413ea0'/>
                {/* <Line type='monotone' dataKey='uv' stroke='#ff7300'/> */}
            </ComposedChart>
    )
  }
}

export default GraphStatsComponent
