import React, {Component} from 'react'
import PropTypes from 'prop-types'
import Files from 'react-files'
class UploadConfig extends Component {
  static propTypes = {
    uploadAFile: PropTypes.func.isRequired
  };

  onFilesChange (files) {
    console.log(files)
    var reader = new FileReader()
    reader.onload = function (e) {
      let text = reader.result
      console.log(JSON.parse(text))
      this.props.uploadAFile(JSON.parse(text))
    }.bind(this)
    reader.readAsText(files[0])
  }

  onFilesError (error, file) {
    console.log('error code ' + error.code + ': ' + error.message)
  }

  render () {
    return (
            <div className="files">
                <Files
                    className='files-dropzone'
                    onChange={this.onFilesChange.bind(this)}
                    onError={this.onFilesError}
                    multiple
                    maxFiles={1}
                    maxFileSize={10000000}
                    minFileSize={0}
                    clickable
                >

                    <h2 className="ui icon header aligned center ">
                        <i aria-hidden="true" className="settings icon"></i>Load new config<br/>Drop files here or click
                        to upload
                    </h2>
                </Files>
            </div>
    )
  }
}

export default UploadConfig
