import React, { Component } from 'react';
import { connect } from 'dva'
import { Link } from 'dva/router'
import numeral from 'numeral'
import autobind from 'autobind-decorator'
import { Flex, Icon,Toast,PullToRefresh} from 'antd-mobile'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import moment from 'moment';
import Base64  from 'base-64';
import MD5  from 'md5';
import ReactDOM from 'react-dom';

import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'

import styles from './index.less'
import {ASSETS_URL} from "../../config";

const categoryList = [
    {
        name : "新闻",
        key : 0
    }, {
        name : "阵容",
        key : 1
    }, {
        name : "赛程",
        key : 2
    }
]

const statusList = {
    "unstart" : "未开赛" ,
    "finished" : "完赛" ,
    "postponed" : "延期" ,
    "living" : "进行中" 
}
const weekList = {
    1:'星期一',
    2:'星期二',
    3:'星期三',
    4:'星期四',
    5:'星期五',
    6:'星期六',
    0:'星期日'
}

@connect(({ teamInfo, loading }) => ({ teamInfo, loading }))
class TeamInfo extends Component {
    constructor(props) {
        super(props)
        this.state = {
            tab1 : 'display_block',
            tab2 : 'display_none',
            tab3 : 'display_none',
            nodata : 'display_block',
            activeCategory:0,
            contentState:'display_none',
            concern : 'display_block',//+关注
            cancleConcern : 'display_none',//已关注
            pagesize : 20,
            pagenum : 1,
            pagenumPre:1,
            teamUuid : '',
            Forwards : [],//前锋
            Midfielders : [],//中场
            Defenders : [],//后卫
            Goalkeepers : [],//守门员
            coachesList : [],//教练
            listData:[],
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {

        });
        this.installLoading();
        const hei = document.documentElement.clientHeight;
        this.setState({
            height: hei
        }) 
    }
    componentWillUnmount(){
        // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
        this.setState = (state,callback)=>{
            return;
          };
    }
    handleTouch = () =>{  
        if(document.getElementsByClassName("team-info")[0].getBoundingClientRect()){
            if(document.getElementsByClassName("team-info")[0].getBoundingClientRect().top>10){
                Toast.loading('加载中...', 30, () => {  
                });
                // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
                this.installLoading();
            }
        }
              
      }
    installLoading(){
        const { dispatch,match } = this.props
        dispatch({ type: 'teamInfo/getTeamInfo' , payload:match.params}).then(result=>{
            // 处理球队队员分类
            result.playersList.map((r,key)=>{
                if(r.type=="Goalkeepers"){
                    this.state.Goalkeepers.push(r)
                }else if(r.type=="Forwards") {
                    this.state.Forwards.push(r)
                }else if(r.type=="Midfielders") {
                    this.state.Midfielders.push(r)
                }else if(r.type=="Defenders") {
                    this.state.Defenders.push(r)
                }               
            })
            this.setState({coachesList:result.coachesList})
            if(result.coachesList.length==0){
                this.setState({nodata:'display_none'})
            }else{
                this.setState({nodata:'display_block'})
            }
            const category = result.category.indexOf(',')>-1?result.category.substring(0,result.category.indexOf(',')):result.category
            // dispatch({ type: 'teamInfo/getTeamNewsList',payload:{pageSize:this.state.pagesize,pageNum:this.state.pagenum,category:category,team:result.uuid}}) 
            dispatch({ type: 'teamInfo/getTeamNewsList',payload:{pageSize:this.state.pagesize,pageNum:this.state.pagenum,category:category,team:''}}) 
            dispatch({ type: 'match/getMatchList',payload:'pageSize='+this.state.pagesize+'&pageNum='
                    +(this.state.pagenum)+'&team='+result.uuid+'&sort=time desc'}).then(result=>{
                        this.setState({listData:result})
            })
            setTimeout(function(){
                Toast.hide();
              },500) 
              this.setState({contentState:'display_block'})
            //   document.getElementById("root").addEventListener('touchend', this.handleTouch)
              if(localStorage.getItem("mobile")){     
                let device = "01";
                let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
                let openId = localStorage.getItem("mobile");
                let md5 = "";
                let token = "";
                dispatch({ type: 'login/getTime' }).then((r)=>{
                    md5 = MD5(Key + r.data + openId);
                    token = Base64.encode(device + r.data + openId + md5);
                    dispatch({ type: 'teamInfo/getTeamConcren' , payload:{team:this.props.teamInfo.teamInfo.uuid,token:token}}).then(result=>{
                        if(result.uuid){
                            this.setState({concern:'display_none',cancleConcern:'display_block',teamUuid:result.uuid});
                        }else{
                            this.setState({cancleConcern:'display_none',concern:'display_block'});
                        }
                    })
                })
            }else{
                this.setState({cancleConcern:'display_none',concern:'display_block'});
            } 
        })
        
        
    }

    tabClick(event){
        let index = event.currentTarget.getAttribute("data-index");
        this.setState({ activeCategory: index })
        const {matchProgress} = this.state;
        document.getElementsByClassName('am-pull-to-refresh')[0].scrollTop=0;
        if(index=='0'){
            this.setState({ tab1: 'display_block',tab2:'display_none',tab3:'display_none' })
        }else if(index=='1'){
            this.setState({ tab1: 'display_none',tab2:'display_block',tab3:'display_none' })
            
        }else if(index=='2'){
            this.setState({ tab1: 'display_none',tab2:'display_none',tab3:'display_block'})
            
        }
      }
      addConcern(){
        const { dispatch,teamInfo,match } = this.props
        if(localStorage.getItem("mobile")){
            let device = "01";
            let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
            let openId = localStorage.getItem("mobile");
            let md5 = "";
            let token = "";
            dispatch({ type: 'login/getTime' }).then((r)=>{
                md5 = MD5(Key + r.data + openId);
                token = Base64.encode(device + r.data + openId + md5);
                dispatch({ type: 'favorites/addConcren',payload:{uuid:this.props.teamInfo.teamInfo.uuid,token:token}}).then(data=>{
                    if(data.status=="SUCCESS"){
                        this.setState({ concern: 'display_none',cancleConcern:'display_block',teamUuid:data.data.uuid})
                    }
                })
            
            })
        }else{
            localStorage.setItem("goback",'teamInfo/'+match.params.uuid);
            this.props.history.push(`/login`)     
        }
      }
      cancleConcern(){
        const { dispatch,teamInfo,match } = this.props
        if(localStorage.getItem("mobile")){
            let device = "01";
            let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
            let openId = localStorage.getItem("mobile");
            let md5 = "";
            let token = "";
            dispatch({ type: 'login/getTime' }).then((r)=>{
                md5 = MD5(Key + r.data + openId);
                token = Base64.encode(device + r.data + openId + md5);
                dispatch({ type: 'favorites/cancleConcren',payload:{uuid:this.state.teamUuid,token:token}}).then(data=>{
                    if(data.status=="SUCCESS"){
                        this.setState({ cancleConcern: 'display_none',concern:'display_block'})
                    }
                })
            
            })
        }else{
            localStorage.setItem("goback",'teamInfo/'+match.params.uuid);
            this.props.history.push(`/login`)     
        }
      }
      onRefresh = () => {
        Toast.loading('加载中...', 30, () => {  
        });
        const hei = document.documentElement.clientHeight;
        this.setState({
            height: hei
        }) 
        this.installLoading();
      };
      render() {
        const { teamInfo,newsList} = this.props.teamInfo
        const { cancleConcern,concern,coachesList,tab1,tab2,tab3,activeCategory,listData,contentState,Forwards,Midfielders,Defenders,Goalkeepers,nodata } = this.state
        return (
            <PageContainer style={{ paddingTop: 0,paddingBottom:0 }} className={[contentState+" paddingTopNone paddingBottomNone"]}>
                <PageContent id={['scorllTop']}>
                    {/* 球队详情 */}
                     <div className={['team-info match-info']}>
                        <div className={['match-info-head']}>
                            <div className={['match-info-head-top']}>
                                <img className={['team-goback']} src={require('../../assets/icon-back-white.png')} onClick={() => this.props.history.go(-1)}/>
                                <h1>球队详情</h1>
                                <div className={['match-box']}>
                                        <div className={['match-info-hometeam']}>
                                            <div className={['match-info-teamIcon']}>
                                                <img src={teamInfo.iconUrl?`${ASSETS_URL}${teamInfo.iconUrl}`:require('../../assets/pic-match-team.png')}  />
                                            </div>
                                            <span>{teamInfo.name}</span>
                                        </div>
                                </div>
                                {/* 关注 */}
                                <a className={['concern '+concern]} onClick={()=>this.addConcern()}><span>+</span>&nbsp;关注</a>
                                <a className={['cancle-concern '+cancleConcern]} onClick={()=>this.cancleConcern()}>已关注</a>
                            </div>
                            <div className={['match-info-head-bottom']}>
                                <ul>
                                    {
                                        categoryList.map((r, key) => {
                                            return (
                                                <li key={key} uuid={r.uuid} className={activeCategory == r.key ? ['light'] : ''}
                                                    onClick={(e)=>this.tabClick(e)}  data-index={r.key}
                                                >
                                                    <a>{r.name}</a>
                                                </li>
                                            )
                                        })
                                    }
                                </ul>
                            </div>
                        </div>
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
                        <div className={['match-data '+tab1]}>
                            {/* 新闻列表 */}
                            <div className={['home-newslist']}>
                                <ul>
                                    {
                                    newsList.map((r, key) => {
                                            return (
                                                <li key={key}>
                                                    <Link to={`/class/${r.uuid}`}>    
                                                        <img className={styles['image']} src={r.coverUrl ? ASSETS_URL+r.coverUrl:require('../../assets/bg-newcomer3.png')}/>
                                                        <div className={styles['text']}>
                                                            <h1 className={styles['title']}>{r.title}</h1>
                                                            <span>{r.newstime.slice(5,r.newstime.length)}</span>
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
                        <div className={['team-lineUp '+tab2]}>
                            <div className={['team-lineUp-title '+nodata]}>
                                <div className={['team-position']}>教练</div>
                            </div>
                            <ul className={['team-player-list']}>
                                {
                                    coachesList.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <div className={['team-player-info']}>
                                                    <img src={require('../../assets/team-player.png')} />
                                                    <div>
                                                        <p className={['team-player-name']}>{r.coach_name}</p>
                                                        <p className={['team-player-number']}>教练 / {r.coach_age}岁</p>
                                                    </div>
                                                    
                                                </div>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                            <div className={['team-lineUp-title']}>
                                <div className={['team-position']}>前锋</div>
                                <div className={['team-matchplayed']}>出场数</div>
                                <div className={['team-goals']}>进球数</div>
                                <div className={['team-yellowcards']}>黄牌</div>
                                <div className={['team-redcards']}>红牌</div>
                            </div>
                            <ul className={['team-player-list']}>
                                {
                                    Forwards.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <div className={['team-player-info']}>
                                                    <img src={require('../../assets/team-player.png')} />
                                                    <div>
                                                        <p className={['team-player-name']}>{r.cnname}</p>
                                                        <p className={['team-player-number']}> {r.number}号&nbsp;/&nbsp;{r.age}岁</p>
                                                    </div>
                                                </div>
                                                <div className={['team-player-matchplayed']}>
                                                    {r.matchplayed}
                                                </div>
                                                <div className={['team-player-goals']}>
                                                    {r.goals}
                                                </div>
                                                <div className={['team-player-yellowcards']}>
                                                    {r.yellowcards}
                                                </div>
                                                <div className={['team-player-redcards']}>
                                                    {r.redcards}
                                                </div>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                            <div className={['team-lineUp-title']}>
                                <div className={['team-position']}>中场</div>
                                <div className={['team-matchplayed']}>出场数</div>
                                <div className={['team-goals']}>进球数</div>
                                <div className={['team-yellowcards']}>黄牌</div>
                                <div className={['team-redcards']}>红牌</div>
                            </div>
                            <ul className={['team-player-list']}>
                                {
                                    Midfielders.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <div className={['team-player-info']}>
                                                    <img src={require('../../assets/team-player.png')} />
                                                    <div>
                                                        <p className={['team-player-name']}>{r.cnname}</p>
                                                        <p className={['team-player-number']}> {r.number}号&nbsp;/&nbsp;{r.age}岁</p>
                                                    </div>
                                                </div>
                                                <div className={['team-player-matchplayed']}>
                                                    {r.matchplayed}
                                                </div>
                                                <div className={['team-player-goals']}>
                                                    {r.goals}
                                                </div>
                                                <div className={['team-player-yellowcards']}>
                                                    {r.yellowcards}
                                                </div>
                                                <div className={['team-player-redcards']}>
                                                    {r.redcards}
                                                </div>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                            <div className={['team-lineUp-title']}>
                                <div className={['team-position']}>后卫</div>
                                <div className={['team-matchplayed']}>出场数</div>
                                <div className={['team-goals']}>进球数</div>
                                <div className={['team-yellowcards']}>黄牌</div>
                                <div className={['team-redcards']}>红牌</div>
                            </div>
                            <ul className={['team-player-list']}>
                                {
                                    Defenders.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <div className={['team-player-info']}>
                                                    <img src={require('../../assets/team-player.png')} />
                                                    <div>
                                                        <p className={['team-player-name']}>{r.cnname}</p>
                                                        <p className={['team-player-number']}> {r.number}号&nbsp;/&nbsp;{r.age}岁</p>
                                                    </div>
                                                </div>
                                                <div className={['team-player-matchplayed']}>
                                                    {r.matchplayed}
                                                </div>
                                                <div className={['team-player-goals']}>
                                                    {r.goals}
                                                </div>
                                                <div className={['team-player-yellowcards']}>
                                                    {r.yellowcards}
                                                </div>
                                                <div className={['team-player-redcards']}>
                                                    {r.redcards}
                                                </div>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                            <div className={['team-lineUp-title']}>
                                <div className={['team-position']}>守门员</div>
                                <div className={['team-matchplayed']}>出场数</div>
                                <div className={['team-goals']}>进球数</div>
                                <div className={['team-yellowcards']}>黄牌</div>
                                <div className={['team-redcards']}>红牌</div>
                            </div>
                            <ul className={['team-player-list']}>
                                {
                                    Goalkeepers.map((r, key) => {
                                        return (
                                            <li key={key}>
                                                <div className={['team-player-info']}>
                                                    <img src={require('../../assets/team-player.png')} />
                                                    <div>
                                                        <p className={['team-player-name']}>{r.cnname}</p>
                                                        <p className={['team-player-number']}> {r.number}号&nbsp;/&nbsp;{r.age}岁</p>
                                                    </div>
                                                </div>
                                                <div className={['team-player-matchplayed']}>
                                                    {r.matchplayed}
                                                </div>
                                                <div className={['team-player-goals']}>
                                                    {r.goals}
                                                </div>
                                                <div className={['team-player-yellowcards']}>
                                                    {r.yellowcards}
                                                </div>
                                                <div className={['team-player-redcards']}>
                                                    {r.redcards}
                                                </div>
                                            </li>
                                        )
                                    })
                                }
                            </ul>
                        </div>
                        <div className={['team-match '+tab3]}>
                            <div className={['match-list']}>
                                {
                                    listData.map((r,key) => {
                                        return (
                                            <div key={key} className={['match-item-item']}>
                                                <a id={moment(r.time).format("YYYY-MM-DD")}></a>
                                                <Link to={`/matchInfo/${r.uuid}`}> 
                                                <h5>
                                                {key>0&&(moment(r.time).format("YYYY-MM-DD")==moment(listData[key-1].time).format("YYYY-MM-DD"))?'':moment(r.time).format("YYYY-MM-DD")
                                                +' '+weekList[moment(r.time).format("d")]}
                                                </h5>
                                                <div className={['match-content']}>
                                                    <div className={['match-content-left']}>
                                                        <span className={['content-time']}>{moment(r.time).format("HH:mm")}</span>
                                                        <span className={['match-roundName']} style={{"WebkitBoxOrient": "vertical"}}>{r.categoryName+r.roundName}</span>
                                                    </div>
                                                    <div className={['match-content-center']}>
                                                        <b><img src={r.hometeamIconUrl?`${ASSETS_URL}${r.hometeamIconUrl}`:require('../../assets/pic-match-team.png')} /></b>
                                                        <span className={['teamName']}>{r.hometeamName}</span>
                                                    </div>
                                                    <div className={['match-content-centers']}>
                                                        <span className={['match-result']}>{r.finalresult==""||r.finalresult=="-"?"VS":r.finalresult}</span>                          
                                                    </div>
                                                    <div className={['match-content-right']}>
                                                        <b><img src={r.awayteamIconUrl?`${ASSETS_URL}${r.awayteamIconUrl}`:require('../../assets/pic-match-team.png')} /></b>                                       
                                                        <span className={['teamName']}>{r.awayteamName}</span>
                                                    </div>
                                                    <p className={['match-content-progress']}>{r.progress?r.progress:''}</p>
                                                    <p className={['match-status match-status-'+r.status]}>{statusList[r.status]}</p>
                                                </div>
                                                </Link>
                                            </div>
                                        )
                                    })
                                }
                            </div>
                        </div>
                        </PullToRefresh>
                     </div>
                </PageContent>
            
            </PageContainer>
    );
  }
}

export default TeamInfo;