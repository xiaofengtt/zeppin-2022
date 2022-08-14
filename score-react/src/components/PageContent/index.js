import React from 'react'
import classnames from 'classnames'

import styles from './index.less'

export default ({ className, children, ...props}) => <section className={classnames(styles['page-content'], className)} {...props}>{children}</section>