import React, {Component} from 'react'
import {connect} from 'react-redux'
import UploadConfig from './components/UploadConfig'
import SelectParams from './components/SelectParams'
import {SET_CONFIG_RUN, RUN_NEW_ALGORITHM} from 'actions/run'
import {Loader} from 'semantic-ui-react'
// import {SET_CONFIG_RUN_SUCCESS} from 'actions/run'
import PropTypes from 'prop-types'
class Run extends Component {
  static propTypes = {
    config: PropTypes.object,
    setNewConfig: PropTypes.func,
    cities: PropTypes.array,
    run: PropTypes.func,
    inProgress: PropTypes.bool
  };

  componentDidMount () {
  }

  render () {
    const {config, setNewConfig, cities, run, inProgress} = this.props

    return (<div>

            {!inProgress && !config && <UploadConfig uploadAFile={setNewConfig}/>}

            {!inProgress && config && <SelectParams cities={cities} handleNewRun={run.bind(this)} config={config}/>}
            {inProgress && <Loader active inline/>}
        </div>)
  }
}

function mapStateToProps (state) {
  console.log('state: ', state)
  return {
    config: state.run.config,
    cities: state.run.cities && Array.from(state.run.cities),
    inProgress: state.run.isRunning
  }
}
function mapDispatchToProps (dispatch) {
  console.log('Sprawdzam1')
  return {
    setNewConfig: (config) => dispatch(SET_CONFIG_RUN(config)),
    run: async (parameters) => {
      let result = await RUN_NEW_ALGORITHM(parameters)
      dispatch(result)
    }

  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Run)
