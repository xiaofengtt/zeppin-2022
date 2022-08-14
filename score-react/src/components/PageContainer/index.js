import React from 'react'
import classnames from 'classnames'
import styles from './index.less';

export default ({ children, className, ...props }) => (
	<div className={classnames(styles['page-container'], className)} {...props}>
		<div className={styles['container-wrapper']}>
			{children}
		</div>
	</div>
)