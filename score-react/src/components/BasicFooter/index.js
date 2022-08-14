import React, { Component } from 'react'
import { withRouter } from 'dva/router'
import { TabBar } from 'antd-mobile'
import IconFont from '../../components/IconFont'
import styles from './index.less'

const { Item } = TabBar

const tabItems = [
	{
		name: 'home',
		title: '首页',
		link: '/home',
		icon: <IconFont type="home" />,
		selectedIcon: <IconFont type="home1" />
	},
	{
		name: 'match',
		title: '赛程',
        link: '/match',
        icon: <IconFont type="match" />,
        selectedIcon: <IconFont type="match1" />
	},
    {
        name: 'ranking',
        title: '排名',
        link: '/ranking',
        icon: <IconFont type="ranking" />,
        selectedIcon: <IconFont type="ranking1" />
    },
	{
		name: 'data-model',
		title: '数据',
        link: '/data-model',
        icon: <IconFont type="data" />,
        selectedIcon: <IconFont type="data1" />
	},
	{
		name: 'my',
		title: '我的',
        link: '/my',
        icon: <IconFont type="my" />,
        selectedIcon: <IconFont type="my1" />
	},
]

class BasicFooter extends Component {
	_handleTabItemClick(name) {
		this.setState({ selected: name });
		this.props.history.push(`/${name}`)
	}
	render() {
		const props = this.props
		return (
            <div className={styles['basic-footer']}>
                <TabBar noRenderContent {...props} barTintColor="#ffffff" unselectedTintColor="#8a8a8a" tintColor="">
					{
						tabItems.map(({ name, ...props }) => (
							<Item key={name} {...props} onPress={() => this._handleTabItemClick(name)} selected={this.props.location.pathname.includes(name)} />
						))
					}
                </TabBar>
            </div>
		)
	}
}


export default withRouter(BasicFooter)