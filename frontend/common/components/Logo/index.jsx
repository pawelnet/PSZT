import React, {Component} from 'react'

class Logo extends Component {
  shouldComponentUpdate () {
    return false
  }

  render () {
    return (
      <div className="logo">
        <i aria-hidden="true" className="home large icon"></i>
      </div>
    )
  }
}

export default Logo
