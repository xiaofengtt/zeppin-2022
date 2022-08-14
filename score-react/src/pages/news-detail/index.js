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
const CommentFooter = ({ onClick,onClicks,onClickSare,...props }) => (
    <div className={['comment-footer']} {...props}>
        <div className={['comment-input']} onClick={onClick}>
            <input placeholder="我来说两句…" disabled />
        </div>
        <a onClick={onClicks}><span className={['comment-number']}><b>{props.number}</b></span></a>
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

@connect(({ classDetail, trade, loading }) => ({ classDetail, trade, loading }))
class ClassDetail extends Component {
    constructor(props) {
        super(props)
        this.state = {
            categoryId:'',
            commentTotal:'0',
            commentState : 'display_none',
            textareaValue : "",
            commentNodata : "display_block",
            contentState:'display_none',
            commentHasdata:'display_none',
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {
    
        });
        const { dispatch, match } = this.props
        dispatch({ type: 'classDetail/getNewsDetail', payload: match.params }).then(()=>{
            dispatch({ type: 'classDetail/getNewsList',payload:{category:this.props.classDetail.newsDetail.category,except:match.params.classId}}).then(()=>{
                Toast.hide();
                this.setState({contentState:'display_block'})
                // document.getElementById("root").addEventListener('touchend', this.handleTouch) 
                const offsetheight = document.getElementsByClassName("am-navbar")[0].offsetHeight
                const hei = this.state.height - ReactDOM.findDOMNode(this.ptr).offsetTop-offsetheight;
                this.setState({
                    height: hei
                })
            })
        })
        dispatch({ type: 'classDetail/getComment', payload: match.params }).then((result)=>{
            if(result.totalResultCount<100){
                this.setState({ commentTotal: result.totalResultCount })
            }else{
                this.setState({ commentTotal: '99+' })
            }
            if(result.totalResultCount>0){
                this.setState({ commentNodata: 'display_none',commentHasdata: 'display_block' })
            }
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
        dispatch({ type: 'classDetail/getNewsDetail', payload: match.params }).then(()=>{
            dispatch({ type: 'classDetail/getNewsList',payload:{category:this.props.classDetail.newsDetail.category,except:match.params.classId}}).then(()=>{
                setTimeout(function(){
                    Toast.hide();
                },1000) 
                this.setState({contentState:'display_block'})
            })
        })
        dispatch({ type: 'classDetail/getComment', payload: match.params }).then((result)=>{
            if(result.totalResultCount<100){
                this.setState({ commentTotal: result.totalResultCount })
            }else{
                this.setState({ commentTotal: '99+' })
            }
            if(result.totalResultCount>0){
                this.setState({ commentNodata: 'display_none',commentHasdata: 'display_block' })
            }
        })
              
      }
    @autobind    
    commentAdd(){
        const { dispatch,match } = this.props
        if(localStorage.getItem("mobile")){
            this.setState({ commentState: 'display_block' })
        }else{
            localStorage.setItem("goback",'news/'+match.params.classId);
            this.props.history.push(`/login`)
        }
        
    }
    @autobind 
    share(){
        const { dispatch, match,classDetail } = this.props
        const { newsDetail = {}} = classDetail
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
                    localStorage.setItem("goback",'news/'+match.params.classId);
                    setTimeout(()=>{this.props.history.push(`/login`)},2000);
                }else if(r.status=="SUCCESS"){
                    Toast.info('评论发送成功',2);
                    dispatch({ type: 'classDetail/getComment', payload: match.params }).then((result)=>{
                        if(result.totalResultCount<100){
                            this.setState({ commentTotal: result.totalResultCount })
                        }else{
                            this.setState({ commentTotal: '99+' })
                        }      
                    })
                    this.setState({ commentNodata: 'display_none',commentHasdata: 'display_block' })
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
        const { commentState,commentTotal,commentNodata,contentState,commentHasdata } = this.state;
        const { classDetail, loading,match} = this.props;
        const { newsDetail = {}, comments = []} = classDetail
        const { newsList } = this.props.classDetail
        return (
            <PageContainer style={{ paddingBottom: 0 }} className={[contentState+' paddingBottomNone']}>
                <BasicHeader>
                    <img src={require('../../assets/icon-back.png')} onClick={() => this.props.history.go(-1)}/>
                    <img className="ttbf-title-logo" src={require('../../assets/ttbf-title-logo.png')} />
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
                    {/* 新闻详情 */}
                    <div className={['newsDetail']}>
                        <h1>{newsDetail.title}</h1>
                        <span className={['newsTime']}>{newsDetail.newstime}</span>
                        <span className={['readNumber text-right']}>{parseInt(match.params.classId.substring(match.params.classId.length-3,match.params.classId.length),16)}&nbsp;阅读</span>
                        <div className={['newsContent']} dangerouslySetInnerHTML={{__html: newsDetail.content}}></div>
                        {/* <div className={['shareBtn']}>
                            <span>分享至:</span>
                            <ul>
                                <li><img src={require('../../assets/pic-share-wx.png')} /></li>
                                <li><img src={require('../../assets/pic-share-pyq.png')} /></li>
                                <li><img src={require('../../assets/pic-share-wb.png')} /></li>
                                <li><img src={require('../../assets/pic-share-qq.png')} /></li>
                                <li><img src={require('../../assets/pic-share-qqzore.png')} /></li>
                            </ul>
                        </div> */}
                    </div>
                    {/* 相关推荐 */}
                    <div className={['related-recommend']}>
                        <div className={['related-title']}>
                            <span></span>
                            <h3>相关推荐</h3>
                            <span></span>
                        </div>
                        {/* 新闻列表 */}
                        <div className={['home-newslist']}>
                            <ul>
                                {
                                newsList.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <Link to={`/class/${r.uuid}`} replace>    
                                                    <img className={styles['image']} src={r.coverUrl ? ASSETS_URL+r.coverUrl:require('../../assets/bg-newcomer3.png')}/>
                                                    <div className={styles['text']}>
                                                        <h1 className={styles['title']}>{r.title}</h1>
                                                        <span>{r.newstime.slice(5, r.newstime.length)}</span>
                                                        <span className={['text-right']}>{parseInt(r.uuid.substring(r.uuid.length-3,r.uuid.length),16)}&nbsp;阅读</span>
                                                    </div>
                                                </Link>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                        </div>
                    </div>
                    {/* 评论 */}
                    <a id="screens"> </a>
                    <div className={['comment']}>  
                        <div className={['related-title']}>
                            <span></span>
                            <h3>评论</h3>
                            <span></span>
                        </div>
                        {/* 暂无评论 */}
                        <div className={['comment-nodata '+commentNodata]}>
                            <img src={require(`../../assets/pic-nodata-comment.png`)} />
                            <p>还没有评论，快来抢沙发~</p>
                        </div>
                        <Comment data={comments}/>
                        <Link to={`/allComment/`+this.props.match.params.classId}> 
                            <p className={['lookMore '+commentHasdata]}>点击查看全部评论</p>
                        </Link>
                    </div>
                   </PullToRefresh>
                    {/* 添加评论 */}
                    {
                        <CommentFooter number={commentTotal} 
                        onClick={this.commentAdd} 
                        onClicks={()=>this.scrollToAnchor('screens')}
                        onClickSare={()=>this.share()}/>
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

export default ClassDetail

