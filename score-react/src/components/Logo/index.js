import React from 'react'
import classnames from 'classnames'
import styles from './index.less'


export default ({ className }) => {
    return (
        <div className={classnames(styles['logo'], className)}>
            <img src={require('../../assets/logo.png')} />
        </div>
    )
}