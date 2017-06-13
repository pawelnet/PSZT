import React, {Component} from 'react'
import PropTypes from 'prop-types'
import {Checkbox, Form, Input, Dropdown} from 'semantic-ui-react'
class SelectParams extends Component {
  static propTypes = {
    cities: PropTypes.array.isRequired,
    handleNewRun: PropTypes.func.isRequired,
    config: PropTypes.object.isRequired
  }
  state = {}
  handleOrigin = (e, {value}) => this.setState({origin: value})
  handleDestination = (e, {value}) => this.setState({destination: value})
  handleMu = (e, {value}) => this.setState({mu: value})
  handleLambda = (e, {value}) => this.setState({lambda: value})
  handleIterationLimit = (e, {value}) => this.setState({iterationLimit: value})
  handleAlgorithm = (e, {value}) => this.setState({algorithm: value})

  handleNewRun () {
        // const {origin, destination, mu, lambda, iterationLimit, algorithm} = this.state
    const {cities, config} = this.props
    console.log('test3', config)
    const {distances} = config
    const matrix = cities.map(originCity => cities.map(destinationCity => {
      let candidate = null
      for (let i = 0, length = distances.length; i < length; i++) {
        if (distances[i].from === originCity && distances[i].to === destinationCity) {
          return distances[i].distance
        }
        if (distances[i].from === destinationCity && distances[i].to === originCity) {
          candidate = distances[i].distance
        }
      }
      return candidate
    }
        ))
    this.props.handleNewRun({matrix, ...this.state})
  }

  render () {
    console.log('state', this.state)
    const {cities} = this.props
    return (
            <div>
                <div className="ui vertically divided grid">
                    <div className="ui padded segment">

                        <button className="ui fluid primary button" onClick={this.handleNewRun.bind(this)}
                                disabled={!cities[this.state.origin] || !cities[this.state.destination] || !this.state.algorithm }>
                            RUN
                        </button>
                        <div className="ui horizontal divider">Settings</div>
                    </div>
                    <div className="two column row">
                        <div className="column">
                            <h4> Cities</h4>

                            <div role="list" className="ui divided middle aligned list">
                                {cities.map((c, i) => (
                                    <div key={i} role="listitem" className="item">
                                        <div className="content">{c}</div>
                                    </div>
                                ))
                                }

                            </div>
                        </div>
                        <div className="column">
                            <h4>Config</h4>
                            <Form>
                                <Form.Field>
                                    <label>Origin city: </label>
                                    <Dropdown onChange={this.handleOrigin} text={cities[this.state.origin]}
                                              options={cities.map((text, key) => {
                                                return {
                                                  key, value: key, text
                                                }
                                              })} fluid/>

                                </Form.Field>
                                <Form.Field>
                                    <label>Destination city: </label>
                                    <Dropdown onChange={this.handleDestination} text={cities[this.state.destination]}
                                              options={cities.map((text, key) => {
                                                return {
                                                  key, value: key, text
                                                }
                                              })} fluid/>

                                </Form.Field>
                                <b>Algorithm</b>
                                <Form.Field>
                                    <Checkbox
                                        radio
                                        label='&mu; , &lambda;'
                                        name='algorithmCheckbox'
                                        value='mulambda'
                                        checked={this.state.algorithm === 'mulambda'}
                                        onChange={this.handleAlgorithm}
                                    />
                                </Form.Field>
                                <Form.Field>
                                    <Checkbox
                                        radio
                                        label='&mu; + &lambda; '
                                        name='algorithmCheckbox'
                                        value='mupluslambda'
                                        checked={this.state.algorithm === 'mupluslambda'}
                                        onChange={this.handleAlgorithm}
                                    />
                                </Form.Field>
                                <Form.Field>
                                    <Checkbox
                                        radio
                                        label='1 + 1'
                                        name='algorithmCheckbox'
                                        value='oneplusone'
                                        checked={this.state.algorithm === 'oneplusone'}
                                        onChange={this.handleAlgorithm}
                                    />
                                </Form.Field>
                                <Form.Field><label>&lambda; value</label>
                                    <Input type="number" placeholder='&lambda;' onChange={this.handleLambda}/>

                                </Form.Field>
                                <Form.Field>
                                    <label>&mu; value</label>
                                    <Input type="number" placeholder='&mu;' onChange={this.handleMu}/>
                                </Form.Field>
                                <Form.Field><label>Iteration limit</label>
                                    <Input type="number" min="1" placeholder='Iteration limit'
                                           onChange={this.handleIterationLimit}/>

                                </Form.Field>
                            </Form>
                        </div>
                    </div>

                </div>
            </div>

    )
  }
}

export default SelectParams
