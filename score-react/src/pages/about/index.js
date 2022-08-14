import React, { Component } from 'react'
import { connect } from 'dva'
import { Link } from 'dva/router'
import autobind from 'autobind-decorator'
import {Button, Icon, List,PullToRefresh} from "antd-mobile";
import PageContent from "../../components/PageContent";
import ReactDOM from 'react-dom';

import BasicHeader from '../../components/BasicHeader'
import PageContainer from "../../components/PageContainer";
import styles from "./index.less";

@connect(({ app, loading }) => ({ app, loading }))
class About extends Component {
    constructor(props) {
        super(props)
        this.state = {
            height: document.documentElement.clientHeight,
        }
    }
    componentDidMount() {
        const offsetheight = document.getElementsByClassName("am-navbar")[0].offsetHeight
        const hei = this.state.height - ReactDOM.findDOMNode(this.ptr).offsetTop-offsetheight;
        this.setState({
            height: hei
        })
    }
    onRefresh = () => {
        // this.setState({ refreshing: true, isLoading: true });
        // const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-navbar")[0].offsetHeight;
        // setTimeout(() => {
        //   this.setState({
        //     refreshing: false,
        //     isLoading: false,
        //     height: hei,
        //   });
        // }, 600);
      };
    render() {
        return (
            <PageContainer style={{ paddingBottom: 0 }}>
                <BasicHeader >
                <img className={['']} src={require('../../assets/icon-back.png')} onClick={() => this.props.history.go(-1)}/>
                    关于我们
                </BasicHeader>
                <PageContent>
                    <PullToRefresh
                        damping={60}
                        style={{
                            height: this.state.height,
                            overflow: 'auto',
                        }}
                        ref={el => this.ptr = el}
                        refreshing={this.state.refreshing}
                        direction={'down'}
                        onRefresh={this.onRefresh}>
                    <div className="introduce">
                        <img src={require('../../assets/icon-logo.png')}/>
                        <p>  提供全球各地区足球比赛数据、比分直播、即时数据、新闻资讯。拥有丰富的赛事数据，涵盖了全球100多个国家地区，各级赛事。
                            为球迷朋友们提供一个专业的看球平台。</p>
                    </div>
                    <div className="aboutus">
                        <p><span className={['content-left']}>联系我们</span><span className={['content-right']}>www.tiantianbifen.com</span></p>
                        <p><span className={['content-left']}>版本号</span><span className={['content-right']}>1.1.2</span></p>
                    </div>
                    </PullToRefresh>
                    <p className={['copyRight']}>Copyright ©️2019 天天比分 All Rights Reserved</p>
                </PageContent>
            </PageContainer>
        )
    }
}

export default About