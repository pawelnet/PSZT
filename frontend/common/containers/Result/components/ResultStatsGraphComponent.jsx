import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {LineChart, Line, XAxis, YAxis, Tooltip, CartesianGrid} from 'recharts'
import _ from 'lodash'
import Graph from 'react-graph-vis'
import {Grid} from 'semantic-ui-react'
class ResultStatsGraphComponent extends Component {
  static propTypes = {
    population: PropTypes.array.isRequired,
    winner: PropTypes.array,
    config: PropTypes.object
  };

  shouldComponentUpdate (nextProps) {
    let {population} = this.props
    let nextStatistics = nextProps.population
    return !_.isEqual(population, nextStatistics)
  }

  render () {
    const {winner, config} = this.props
    var options = {
      autoResize: true,
      height: '800px',
      width: '800px',
      layout: {
        hierarchical: false
      },
      edges: {
        color: '#000000'
      }
    }
    var events = {
      select: function (event) {
                // var { nodes, edges } = event;
      }
    }
    if (winner && config) {
      const cities = Array.from(config.cities)
      var graph = {
        nodes: winner.map((label, id) => {
          return {id, label: cities[label]}
        }),
        edges: winner.map((w, i) => {
          return {from: i, to: i + 1}
        })
      }
    } else if (winner) {
      graph = {
        nodes: winner.map((label, id) => {
          return {id, label}
        }),
        edges: winner.map((w, i) => {
          return {from: i, to: i + 1}
        })
      }
    }

    const {population} = this.props

    return (<div>
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={8}>
                            <LineChart width={1000} height={800} data={population} syncId="anyId"
                                       margin={{top: 10, right: 30, left: 0, bottom: 0}}>
                                <XAxis dataKey="iteration"/>
                                <YAxis/>
                                <CartesianGrid strokeDasharray="3 3"/>
                                <Tooltip/>
                                <Line type='monotone' dataKey='score' stroke='#8884d8' fill='#8884d8'/>
                            </LineChart>
                        </Grid.Column>
                        <Grid.Column width={8}>
                            {winner &&
                            <Graph graph={graph} width="100%" height="100%" options={options} events={events}/>
                            }
                        </Grid.Column>
                    </Grid.Row>
                </Grid>

            </div>
    )
  }
}

export default ResultStatsGraphComponent
