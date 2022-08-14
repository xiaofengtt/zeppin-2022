import React, { Component } from 'react';
import {ActivityIndicator, Button, Toast} from 'antd-mobile'
import { connect } from 'dva'
import { createForm } from 'rc-form'
import autobind from 'autobind-decorator'

import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import CodeButton from '../../components/CodeButton'

import styles from './index.less'

let countTimer = 60
@connect(({ login, loading }) => ({ login, loading }))
class Login extends Component {
  constructor(props) {
    super(props)
    this.state = {
      // captchaCode: 'http://223.202.134.215:8080/score/loginkaptcha/getCaptchaCode',
      mobileValue:'',
      captchValue:'',
      codeValue:'',
      sendSmsButton:'no-send',
      sendloginBtn:'login-no-send',
      isCountdown: false,
      countdown: countTimer,
      codeRandom:'',//随机验证码
      isTimer:false,//倒计时是否可点
    }
  }

  @autobind
  handleLogin() {
    if(this.state.sendloginBtn=="login-sure-send"){
      const { form, dispatch, history } = this.props
      const { validateFields, getFieldsError } = form
      validateFields((error, values) => {
          if (!error) {
            dispatch({ type: 'login/getTime' }).then((rs)=>{
              dispatch({
                  type: 'login/loginBtn',
                  payload: {mobile:values.mobile,password:values.userPassword,userCode: values.userCode,time:rs.data},
              }).then((r) => {
                if(r.status=="FAILED"){
                  Toast.info(r.message);
                  this.setState({ isCountdown: false,isTimer:false});
                  clearTimeout(this.timer);
                  this.setState({ countdown: countTimer })
                  history.push('/login')
                }else{
                  localStorage.setItem("mobile",r.data.frontUser.mobile);
                  localStorage.setItem("uuid",r.data.frontUser.uuid);
                  localStorage.setItem("refresh",'true');
                  clearTimeout(this.timer);
                  if(this.judgePhone()=='isAndroid'||this.judgePhone()==''){
                    setTimeout(()=>{history.go(-1)},500);
                    window.JavascriptInterface.auth(values.mobile+r.data.frontUser.uuid);
                  }else if(this.judgePhone()=='isiOS'){
                    if(localStorage.getItem("goback")){
                      setTimeout(()=>{history.replace('/'+localStorage.getItem("goback"))},500);
                    }else{
                      setTimeout(()=>{history.replace('/home')},500);
                    }
                    
                    window.webkit.messageHandlers.auth.postMessage(values.mobile+r.data.frontUser.uuid);		
                  }		                
                }
              }).catch((msg) => {
                
              })
           })
          } else {
              
          }
      })
    }else{
      Toast.info("请输入相应的内容");
    }
  }
  handleClick(e) {
    const { isCountdown } = this.state
    const { form, dispatch } = this.props
    const { validateFields, getFieldsError } = form
    if(this.state.sendSmsButton=="no-send"){
      Toast.info("请输入正确的手机号或图形验证码");
    }else{
    validateFields(['mobile','userPassword'], (error, values) => {
      if(values.userPassword!=this.state.codeRandom){
        Toast.info("图形验证码输入错误");
        this.createCode();
      }else{
        if(!this.state.isTimer){
          this.setState({isTimer:true});
            this.setState({ isCountdown: true }, () => {
              this.timer = setInterval(() => {
                const { countdown } = this.state
                if (countdown <= 0) {
                  clearInterval(this.timer)
                  this.setState({ isCountdown: false, countdown: countTimer,isTimer:false })
                } else {
                  this.setState({ countdown: countdown - 1 })
                }
              }, 1000)
                dispatch({ type: 'login/getTime' }).then((rs)=>{
                    const { dispatch, history } = this.props
                        if (!error) {
                            dispatch({
                              type: 'login/sendSms',
                              payload: {mobile:values.mobile,password:values.userPassword,time:rs.data},
                            }).then(r => {
                              Toast.info(r.message);
                              if(r.status=="FAILED"){
                                this.setState({ isCountdown: false,isTimer:false});
                                clearTimeout(this.timer);
                                this.setState({ countdown: countTimer })
                                this.createCode();
                                history.push('/login')
                              }else{
                                
                              }
                            }).catch(r => {
                                Toast.info(r.message);
                                this.createCode();
                                this.setState({ isCountdown: false,isTimer:false });
                                clearTimeout(this.timer);
                                this.setState({ countdown: countTimer })
                                history.push('/login')
                            })
                        } else {
                            
                        }
                  })
              })
            }
          }
       })
    }  
    
  }
  componentDidMount() {
      const { dispatch } = this.props
      this.createCode();
      window.addEventListener('input', function(){
        setTimeout(function(){
          const { form, dispatch } = this.props
          const { validateFields, getFieldsError } = form
          let phoneregex = /^1(3|4|5|6|7|8|9)\d{9}$/;
          validateFields(['mobile','userPassword','userCode'], (error, values) => {
            setTimeout(function(){
              this.setState({mobileValue:values.mobile,captchValue:values.userPassword,codeValue:values.userCode});
              if(phoneregex.test(values.mobile)&&values.userPassword!=""){
                this.setState({sendSmsButton:'sure-send'})
              }else{
                this.setState({sendSmsButton:'no-send'})
              }
              if(phoneregex.test(values.mobile)&&values.userPassword!=""&&values.userCode!=""){
                this.setState({sendloginBtn:'login-sure-send'})
              }else{
                this.setState({sendloginBtn:'login-no-send'})
              }
          }.bind(this),100)
          })
       }.bind(this),100)//延迟回调模块中input中输入的值
    }.bind(this));
  }
  componentWillUnmount(){
    clearTimeout(this.timer);
    this.setState = (state,callback)=>{
      return;
    };
  }
  @autobind
  createCode(){
      let code = '';
      let codeLength = 4;
      let random = new Array(0,1,2,3,4,5,6,7,8,9);
      for(let i = 0; i < codeLength; i++){
          let index = Math.floor(Math.random()*10);
          code += random[index]; 
      }
      this.setState({ codeRandom: code})
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
  render() {
    const { sendSmsButton,sendloginBtn,isCountdown,countdown,codeRandom} = this.state
    const { loading } = this.props
    const { getFieldDecorator } = this.props.form
    return (
      <PageContainer className={styles['login']} style={{ paddingTop: 0, paddingBottom: 0 }} id="loginPaddingNone">
          <ActivityIndicator toast text="正在登录..." animating={loading.effects['app/login'] || false} />
          <PageContent>
          <div className={['login-go-back']} onClick={() => this.props.history.go(-1)}>
            <img src={require('../../assets/icon-login-back.png')} />
          </div>
          {/* logo在这 */}
          <img className={['icon-logo']} src={require('../../assets/icon-logo.png')} />
          <form className={styles['login-form']}>         
            <div className={styles['form-row']}>
              <div className={styles['input']}>
                {
                  getFieldDecorator('mobile', {
                    initialValue: '',
                    rules: [{
                      required: true, message: '请输入手机号码'
                    }]
                  })(
                    <input placeholder="请输入手机号码" type="tel" maxLength="11"/>
                  )
                }
              </div>
            </div>
            <div className={styles['form-row']}>
              <div className={styles['input']}>
                {
                  getFieldDecorator('userPassword', {
                    initialValue: '',
                    rules: [{
                      required: true, message: '请输入图形验证码'
                    }]
                  })(
                    <input placeholder="请输入图形验证码" type="tel" maxLength="4"/>
                  )
                }
                <span className={['codeRandom']} onClick={() => this.createCode()}>{codeRandom}</span>
              </div>
            </div>
            <div className={styles['form-row']}>
              <div className={styles['input']}>
                {
                  getFieldDecorator('userCode', {
                    initialValue: '',
                    rules: [{
                      required: true, message: '请输入验证码'
                    }]
                  })(
                    <input placeholder="请输入验证码" type="tel" maxLength={6} />
                  )
                }
                <a className={['code-button '+sendSmsButton]} onClick={this.handleClick.bind(this)}>
                  { !isCountdown ? '获取验证码' : countdown+'s'}
                </a>
              </div>
            </div>
            <div className={['submit-button '+sendloginBtn]} onClick={this.handleLogin}>
              <a type="">登录</a>
            </div>
          </form>
        </PageContent>
      </PageContainer>
    );
  }
}

export default createForm()(Login);
