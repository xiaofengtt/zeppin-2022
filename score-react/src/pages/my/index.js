import React, { Component } from 'react'
import { List, Button,Toast,PullToRefresh } from 'antd-mobile'
import { connect } from 'dva'
import { Link } from 'dva/router'
import autobind from 'autobind-decorator'
import classnames from 'classnames'
import Base64  from 'base-64';
import MD5  from 'md5';
import ReactDOM from 'react-dom';

import { ASSETS_URL } from "../../config";
import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import IconFont from '../../components/IconFont'
import BasicFooter from '../../components/BasicFooter'

import styles from './index.less'

@connect(({ app, loading }) => ({ app, loading }))
class My extends Component {
    constructor(props) {
        super(props)
        this.state = {
            userlogOut : 'display_block',
            userLog : 'display_none',
            username:'',
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {       
        this.props.history.listen(function (location) {
            if(location.pathname=="/my"&&localStorage.getItem("refresh")){
                localStorage.removeItem("refresh");
                window.location.reload();
            }else{
                
            }
        })
        if(localStorage.getItem("mobile")){
            this.setState({ userLog: 'display_block',userlogOut:'display_none',username:localStorage.getItem("mobile")})
        }else{
            this.setState({ userLog: 'display_none',userlogOut:'display_block',})
        }
        // this.getUser();
        // document.getElementById("root").addEventListener('touchend', this.handleTouch)
    }
    componentWillUnmount(){
        // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
        this.setState = (state,callback)=>{
            return;
          };
      }
      handleTouch = () =>{  
        if(document.getElementsByClassName("my-content")[0].getBoundingClientRect()){
            if(document.getElementsByClassName("my-content")[0].getBoundingClientRect().top>10){
                Toast.loading('加载中...', 30, () => {
        
                });
                if(localStorage.getItem("mobile")){
                    this.setState({ userLog: 'display_block',userlogOut:'display_none',username:localStorage.getItem("mobile")})
                }else{
                    this.setState({ userLog: 'display_none',userlogOut:'display_block',})
                }
                setTimeout(function(){
                    Toast.hide();
                },2000) 
            }
        }
              
      }
    @autobind
    logout() {
      this.props.dispatch({ type: 'app/logout' }).then((r) => {
        if(r.status=="SUCCESS"){
            Toast.info("退出登录成功");
            localStorage.removeItem("mobile");
            localStorage.removeItem("uuid");
            if(this.judgePhone()=='isAndroid'||this.judgePhone()==''){
				window.JavascriptInterface.logout();
			}else if(this.judgePhone()=='isiOS'){
				window.webkit.messageHandlers.logout.postMessage('');	
            }		            
            setTimeout(()=>{window.location.reload()},500);
        }else{
            Toast.info(r.message);
        }
      })
    }
    judgePhone(){
        let u = navigator.userAgent;
        let isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
        let isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端 
        if(isAndroid){
          return 'isAndroid';
        }else if(isiOS){
          return 'isiOS';
        }else{
          return '';
        }
      }
    @autobind
    getUser(){
        const {dispatch } =this.props
        let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
            dispatch({
                type:'app/getUserInfo',payload:{token:token},
            }).then(r=>{
                if(r.status=="ERROR"&&r.errorCode=="302"){
                    // this.props.history.push(`/login`)
                    this.setState({ userLog: 'display_none',userlogOut:'display_block',})
                }else if(r.status=="SUCCESS"){
                    this.setState({ userLog: 'display_block',userlogOut:'display_none',username:r.data.mobile})
                }
            })
        })
    }
    render() {
        const user  = JSON.parse(window.localStorage.getItem('user')) || {}
        const { userlogOut,userLog,username } = this.state
        return (
            <PageContainer style={{ paddingTop: 0 }} className={['paddingTopNone']}>
                <PageContent className="my-content">
                    <div className={['avater-banner '+userLog]}>
                        <div className={['avater']}>
                            <img src={require('../../assets/default-avater-nor.png')}/>
                        </div>
                        <div className={['user-profile']}>
                            <p className={['user-name']}>{username}</p>
                        </div>
                    </div>
                    <div className={['avater-banner '+userlogOut]} onClick={()=>{localStorage.setItem("goback",'my');this.props.history.push(`/login`);}}>
                        <div className={['avater']}>
                            <img src={require('../../assets/default-avater.png')}/>
                        </div>
                        <div className={['user-profile']}>
                            <p className={['user-notLog']}>立即登录</p>
                        </div>
                    </div>
                    <List className={classnames(styles['list'], styles['mt-10'])}>
                        <List.Item arrow=""  onClick={() => {localStorage.setItem("goback",'my');userLog=='display_block'?this.props.history.push('/my/collect'):this.props.history.push('/login');}}>
                          
                            <div className={styles['item']}><img src={require('../../assets/icon-my-collection.png')} /><span>我的关注</span></div>
                          
                        </List.Item>
                        <List.Item arrow="" onClick={() => this.props.history.push('/setting/about')}>
                            <div className={styles['item']}><img src={require('../../assets/icon-my-us.png')} /><span>关于我们</span></div>
                        </List.Item>
                    </List>
                    <div className={['logout-btn '+userLog]} onClick={this.logout}>
                      <a type="">退出登录</a>
                    </div>
                </PageContent>
                <BasicFooter />
            </PageContainer>
        )
    }
}

export default My