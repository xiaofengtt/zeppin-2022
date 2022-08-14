import React, { PureComponent } from 'react'
import classnames from 'classnames'
import styles from './index.less'

const countTimer = 60

class CodeButton extends PureComponent {
  constructor(props) {
    super(props)
    this.state = {
      isCountdown: false,
      countdown: countTimer,
    }
  }
  handleClick(e) {
    const { isCountdown } = this.state
    console.log(isCountdown)
    if (isCountdown) {
      this.props.onClick()
      this.setState({ isCountdown: true }, () => {
        this.timer = setInterval(() => {
          const { countdown } = this.state
          if (countdown <= 0) {
            clearInterval(this.timer)
            this.setState({ isCountdown: false, countdown: countTimer })
          } else {
            this.setState({ countdown: countdown - 1 })
          }
        }, 1000)
      })
    } else {
      // return false
    }
    
  }
  render() {
    const { isCountdown, countdown } = this.state
    const { onClick, className, ...props } = this.props;
    return (
      <a className={classnames(styles['code-button'], className)} onClick={this.handleClick.bind(this)} {...props}>
        { !isCountdown ? '获取验证码' : countdown+'s'}
      </a>
    )
  }
}

export default CodeButton