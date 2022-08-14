import React from 'react'
import classnames from 'classnames'
import { NavBar } from 'antd-mobile'

import styles from './index.less'

export default ({ className, children, ...props }) => <NavBar className={classnames(styles['basic-header'], className)} {...props}>{children}</NavBar>