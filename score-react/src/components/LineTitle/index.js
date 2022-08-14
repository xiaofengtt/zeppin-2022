import React from 'react'
import classnames from 'classnames'

import styles from './index.less'

const LineTitle = ({ className = '', children, ...props }) => {
    return (
        <div className={classnames(styles['line-title'], className)}>
            {children}
        </div>
    )
}

export default LineTitle