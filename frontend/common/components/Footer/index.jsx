import React, {Component} from 'react'
// import PropTypes from 'prop-types'
import {Grid, Header, Icon} from 'semantic-ui-react'
import './Footer.scss'

export default class Footer extends Component {
  shouldComponentUpdate () {
    return false
  }

  render () {
    return (
      <footer>
          <div className="footer-inner">
              <Grid>
            <Grid.Row>
              <Grid.Column width={12}>
                <a href="">
                  <Header as="h3" inverted>
                    <Icon name="student" />
                    <Header.Content>
                      Projekt PSZT
                      <Header.Subheader>
                        Evolutionary algorithm - Salesman problem
                      </Header.Subheader>
                    </Header.Content>
                  </Header>
                </a>
              </Grid.Column>
            </Grid.Row>
          </Grid>
        </div>
      </footer>
    )
  }
}
