import React, { Component } from 'react'
import { connect } from 'dva'
import { Link } from 'dva/router'
import { Toast,PullToRefresh } from 'antd-mobile';
import moment from 'moment'
import autobind from 'autobind-decorator'
import BasicHeader from '../../components/BasicHeader'
import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import Base64  from 'base-64';
import MD5  from 'md5';
import ReactDOM from 'react-dom';

import 'video.js/dist/video-js.css'
import styles from './index.less'
import {ASSETS_URL} from "../../config";

// 评论区
const CommentFooter = ({ onClick,onClickSare,...props }) => (
    <div className={['comment-footer']} {...props}>
        <div className={['comments-input']} onClick={onClick}>
            <input placeholder="我来说两句…" disabled />
        </div>
        <span className={['comment-share']} onClick={onClickSare}></span>
    </div>
)
// 评论
const Comment = ({ data }) => (
    <ul className={styles['comment']}>
        {
            data &&
            data.map((r,key) => {
                const { content, } = r;
                return (
                    <li key={key}>
                        <img className={styles['avater']} src={require('../../assets/default-avater-nor.png')}/>
                        <div className={styles['comment-context']}>
                            <span className={['user-name']}>热心球迷</span>
                            <span className={['user-date']}>{moment(r.createtime).format("MM-DD HH:mm")}</span>
                            <p className={['user-comment']}>{content}</p>
                        </div>
                        
                    </li>
                )
            })
        }
    </ul>
)

@connect(({ allComment, trade, loading }) => ({ allComment, trade, loading }))
class AllComment extends Component {
    constructor(props) {
        super(props)
        this.state = {
            categoryId:'',
            commentTotal:'0',
            commentState : 'display_none',
            textareaValue : "",
            commentNodata : "display_block",
            contentState:'display_none',
            comments:[],
            newsDetail : {},
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {
    
        });
        const { dispatch, match } = this.props
        dispatch({ type: 'classDetail/getNewsDetail', payload: match.params }).then((r)=>{
            this.setState({"newsDetail":r.data})
        })
        dispatch({ type: 'classDetail/getAllComment', payload: match.params }).then((result)=>{
            Toast.hide();
            this.setState({contentState:'display_block'})
            // document.getElementById("root").addEventListener('touchend', this.handleTouch) 
            if(result.totalResultCount<100){
                this.setState({ commentTotal: result.totalResultCount })
            }else{
                this.setState({ commentTotal: '99+' })
            }
            if(result.totalResultCount>0){
                this.setState({ commentNodata: 'display_none',comments:result.data })
            }
            const offsetheight = document.getElementsByClassName("am-navbar")[0].offsetHeight
            const hei = this.state.height - ReactDOM.findDOMNode(this.ptr).offsetTop-offsetheight;
            this.setState({
                height: hei
            })
        })
        
    }
    componentWillUnmount(){
        // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
        this.setState = (state,callback)=>{
            return;
        };
    }
    handleTouch = () =>{  
        const { dispatch, match } = this.props
        dispatch({ type: 'classDetail/getNewsDetail', payload: match.params }).then((r)=>{
            this.setState({"newsDetail":r.data})
        })
        dispatch({ type: 'classDetail/getAllComment', payload: match.params }).then((result)=>{
            setTimeout(function(){
                Toast.hide();
            },1000)                   
            this.setState({contentState:'display_block'})
            if(result.totalResultCount>0){
                this.setState({ commentNodata: 'display_none',comments:result.data })
            }
            
        })
            
    }
    @autobind 
    share(){
        const { dispatch, match,classDetail } = this.props
        const { newsDetail } = this.state
        if(this.judgePhone()=='isAndroid'||this.judgePhone()==''){
            window.JavascriptInterface.share(match.params.classId+'@_@'+newsDetail.title+'@_@'+ASSETS_URL+newsDetail.coverUrl+'@_@'+newsDetail.content.replace(/<.*?>/g, "").substring(0,50));
        }else if(this.judgePhone()=='isiOS'){
            window.webkit.messageHandlers.share.postMessage(match.params.classId+'@_@'+newsDetail.title+'@_@'+ASSETS_URL+newsDetail.coverUrl+'@_@'+newsDetail.content.replace(/<.*?>/g, "").substring(0,50));		
        }		  
    }
    @autobind 
    shareBack(status,message){
        if(!status){
           Toast.info(message);  	
        }
          
    }
    @autobind    
    commentAdd(){
        const { dispatch,match } = this.props
        let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
            this.props.dispatch({
                type:'classDetail/getUserInfo',payload: {token:token},
            }).then((r)=>{
                if(r.status=="ERROR"&&r.errorCode=="302"){
                    localStorage.setItem("goback",'class/'+match.params.classId);
                    this.props.history.push(`/login`)
                    
                }else if(r.status=="SUCCESS"){
                    this.setState({ commentState: 'display_block' })
                }
            })
        })      
        
        
    }
    commentCancle(){
        this.setState({ commentState: 'display_none' })
    }
    commentSure(){
        this.setState({ commentState: 'display_none' })
        this.handcommentClick()
    }
    scrollToAnchor = (anchorName) => {
        if (anchorName) {
            let anchorElement = document.getElementById(anchorName);
            if(anchorElement) { anchorElement.scrollIntoView(); }
        }
      }
    handcommentClick(){
        const { dispatch, match } = this.props
        const {textareaValue} = this.state;
        let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
            dispatch({
                type: 'classDetail/addComment',
                payload: {newspublish:match.params.classId,content:textareaValue,token:token},
            }).then((r) => {
                if(r.status=="ERROR"&&r.errorCode=="302"){
                    Toast.info(r.message,2);
                    localStorage.setItem("goback",'class/'+match.params.classId);
                    setTimeout(()=>{this.props.history.push(`/login`)},2000);
                }else if(r.status=="SUCCESS"){
                    Toast.info('评论发送成功',2);
                    dispatch({ type: 'classDetail/getAllComment', payload: match.params }).then((result)=>{
                        this.setState({ comments:result.data })     
                    })
                    this.setState({ commentNodata: 'display_none' })
                }
                
            })
        })
    }
    handleGetInputValue(event){
        this.setState({
            textareaValue : event.currentTarget.value,
        })
      };
    onRefresh = () => {
        this.setState({ refreshing: true, isLoading: true });
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-navbar")[0].offsetHeight;
        setTimeout(() => {
          this.setState({
            refreshing: false,
            isLoading: false,
            pagenum:1,
            height: hei,
          });
        }, 600);
        this.handleTouch();
    };
    render() {
        const { commentState,commentTotal,commentNodata,contentState,comments } = this.state;
        const { allComment, loading,match} = this.props;
        // const { comments = []} = allComment
        return (
            <PageContainer style={{ paddingBottom: 0,backgroundColor:'#fff', }} className={[contentState+' paddingBottomNone']}>
                <BasicHeader>
                    <img src={require('../../assets/icon-back.png')} onClick={() => this.props.history.go(-1)}/>
                    全部评论
                </BasicHeader>
                <PageContent className={styles['class-detail']}>
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
                    {/* 评论 */}
                    <div className={['comment']} style={{paddingTop:0,}}>  
                        {/* 暂无评论 */}
                        <div className={['comment-nodata '+commentNodata]}>
                            <img src={require(`../../assets/pic-nodata-comment.png`)} />
                            <p>还没有评论，快来抢沙发~</p>
                        </div>
                        <Comment data={comments}/>
                        <p className={['lookMore']}>已加载全部评论</p>
                    </div>
                    </PullToRefresh>
                    {/* 添加评论 */}
                    {
                        <CommentFooter number={commentTotal} onClick={this.commentAdd} onClickSare={()=>this.share()}/>
                    } 
                    {/* 评论输入框 */}
                    <div className={['ccomment-fixed '+commentState]}>
                        <div className={['comment-content']}>
                            <span className={['comment-cancle']} onClick={() =>this.commentCancle()}>取消</span>
                            <span className={['comment-sure']} onClick={() =>this.commentSure()}>发送</span>
                            <textarea placeholder="我来说两句…" onChange={(e) =>this.handleGetInputValue(e)}></textarea>
                        </div>
                    </div>
                </PageContent>
            </PageContainer>
        )
    }
}

export default AllComment


