import React from 'react'
import ReactRater from 'react-rater'
import classnames from 'classnames'
import 'react-rater/lib/react-rater.css'

import Star from './Star'
import styles from './index.less'

const Rater = ({ className, starClass, ...props }) => (
    <span className={classnames(className, styles['rater'])}>
        <ReactRater {...props}>
            <Star starClass={starClass} />
        </ReactRater>
    </span>
)

export default Rater