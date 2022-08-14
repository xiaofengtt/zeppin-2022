import React from 'react'
import classnames from 'classnames'
import styles from './index.less'
import './iconfont'

export default ({ type, className, ...props }) => (
    <svg className={classnames(styles['icon'], className)} aria-hidden="true" {...props}>
        <use xlinkHref={`#icon-${type}`}></use>
    </svg>
)