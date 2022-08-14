import React, { Component } from 'react';
import { connect } from 'dva'
import { Link } from 'dva/router'
import numeral from 'numeral'
import autobind from 'autobind-decorator'
import { Flex, Icon,Toast,PullToRefresh } from 'antd-mobile'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import moment from 'moment';
import ReactDOM from 'react-dom';

import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'

import styles from './index.less'
import {ASSETS_URL} from "../../config";
const categoryList = [
    {
        name : "数据",
        key : 0
    }, {
        name : "赛况",
        key : 1
    }, {
        name : "阵容",
        key : 2
    }
]

const statusList = {
    "unstart" : "未开赛" ,
    "finished" : "完赛" ,
    "postponed" : "延期" ,
    "living" : "进行中" 
}

const statisticsList = {//技术统计名称
    "Ball Possession":"控球率",
    "Goal Attempts":"射门",
    "Shots on Goal":"射正",
    "Shots off Goal":"射偏",
    "Blocked Shots":"射门被封堵",
    "Corner Kicks":"角球",
    "Free Kicks":"任意球",
    "Offsides":"越位",
    "Goalkeeper Saves":"扑救",
    "Fouls":"犯规",
    "Red Cards":"红牌",
    "Yellow Cards":"黄牌",
    "Total Passes":"传球",
    "Completed Passes":"传球成功",
    "Tackles":"铲断",
    "Throw-in":"界外球"
}

@connect(({ matchInfo, loading }) => ({ matchInfo, loading }))
class MatchInfo extends Component {
    constructor(props) {
        super(props)
        this.state = {
            matchRecentHome : [],
            matchRecentAway : [],
            matchProgress : [],
            tab1 : 'display_block',
            tab2 : 'display_none',
            tab3 : 'display_none',
            nodata : 'display_none',
            activeCategory:0,
            contentState:'display_none',
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
  componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {

        });
        this.installLoading();
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop;
        this.setState({
            height: hei
        }) 
  }
  installLoading(){
    const { dispatch,match } = this.props
    dispatch({ type: 'matchInfo/getMatchInfo' , payload:match.params}).then(()=>{
      dispatch({ type: 'matchInfo/getMatchClash' , payload:{hometeam:this.props.matchInfo.matchInfo.hometeam,awayteam:this.props.matchInfo.matchInfo.awayteam}})
      dispatch({ type: 'matchInfo/getMatchRecent' , payload:{hometeam:this.props.matchInfo.matchInfo.hometeam}}).then(()=>{
          this.setState({ matchRecentHome: this.props.matchInfo.matchRecent })
          setTimeout(function(){
            Toast.hide();
          },500) 
          this.setState({contentState:'display_block'})
        //   document.getElementById("root").addEventListener('touchend', this.handleTouch) 
      })        
      // 相关推荐
      dispatch({ type: 'matchInfo/getNewsList',payload:{category:this.props.matchInfo.matchInfo.category,except:match.params.uuid}})   
      dispatch({ type: 'matchInfo/getMatchRecent' , payload:{hometeam:this.props.matchInfo.matchInfo.awayteam}}).then(()=>{
          this.setState({ matchRecentAway: this.props.matchInfo.matchRecent })
      })  
      //处理比赛数据
      //先把三个数组整合到一起
      let matchProgress = [];
      //处理上下场数据
      this.props.matchInfo.matchInfo.substitutes.map((r,key)=>{
          if(this.props.matchInfo.matchInfo.substitutes[key].home_scorer.length==0){
              this.props.matchInfo.matchInfo.substitutes[key].owntype = 'awayType substitutes';
              this.props.matchInfo.matchInfo.substitutes[key].home_scorer_substitutes= {
                  "out": "",
                  "in": ""
              }
              this.props.matchInfo.matchInfo.substitutes[key].away_scorer_substitutes= this.props.matchInfo.matchInfo.substitutes[key].away_scorer
              this.props.matchInfo.matchInfo.substitutes[key].card = "";
              this.props.matchInfo.matchInfo.substitutes[key].away_fault = "";
              this.props.matchInfo.matchInfo.substitutes[key].home_fault = "";
              this.props.matchInfo.matchInfo.substitutes[key].away_scorer_goals="";
              this.props.matchInfo.matchInfo.substitutes[key].home_scorer_goals="";
              this.props.matchInfo.matchInfo.substitutes[key].score_goals="";
          }else{
              this.props.matchInfo.matchInfo.substitutes[key].owntype = 'homeType substitutes';
              this.props.matchInfo.matchInfo.substitutes[key].away_scorer_substitutes = {
                  "out": "",
                  "in": ""
              }
              this.props.matchInfo.matchInfo.substitutes[key].home_scorer_substitutes= this.props.matchInfo.matchInfo.substitutes[key].home_scorer
              this.props.matchInfo.matchInfo.substitutes[key].card = "";
              this.props.matchInfo.matchInfo.substitutes[key].away_fault = "";
              this.props.matchInfo.matchInfo.substitutes[key].home_fault = "";
              this.props.matchInfo.matchInfo.substitutes[key].away_scorer_goals="";
              this.props.matchInfo.matchInfo.substitutes[key].home_scorer_goals="";
              this.props.matchInfo.matchInfo.substitutes[key].score_goals="";
          }
      })
      //处理进球数据
      this.props.matchInfo.matchInfo.goals.map((r,key)=>{
          if(this.props.matchInfo.matchInfo.goals[key].home_scorer ==''){
              this.props.matchInfo.matchInfo.goals[key].owntype = 'awayType goals';
              
          }else{
              this.props.matchInfo.matchInfo.goals[key].owntype = 'homeType goals';
          }
          this.props.matchInfo.matchInfo.goals[key].home_scorer_goals=this.props.matchInfo.matchInfo.goals[key].home_scorer
          this.props.matchInfo.matchInfo.goals[key].away_scorer_goals=this.props.matchInfo.matchInfo.goals[key].away_scorer
          this.props.matchInfo.matchInfo.goals[key].score_goals=this.props.matchInfo.matchInfo.goals[key].score.replace(/-/, ":").replace(/\s/g,"");
          this.props.matchInfo.matchInfo.goals[key].away_fault = "";
          this.props.matchInfo.matchInfo.goals[key].home_fault = "";
          this.props.matchInfo.matchInfo.goals[key].card = "";
          this.props.matchInfo.matchInfo.goals[key].home_scorer_substitutes= {
              "out": "",
              "in": ""
          }
          this.props.matchInfo.matchInfo.goals[key].away_scorer_substitutes= {
              "out": "",
              "in": ""
          }
      })
      //处理黄牌数据
      this.props.matchInfo.matchInfo.cards.map((r,key)=>{
          if(this.props.matchInfo.matchInfo.cards[key].home_fault ==''){
              this.props.matchInfo.matchInfo.cards[key].owntype = 'awayType cards';
          }else{
              this.props.matchInfo.matchInfo.cards[key].owntype = 'homeType cards';
          }
          this.props.matchInfo.matchInfo.cards[key].home_scorer_goals=""
          this.props.matchInfo.matchInfo.cards[key].away_scorer_goals=""
          this.props.matchInfo.matchInfo.cards[key].score_goals="";
          this.props.matchInfo.matchInfo.cards[key].home_scorer_substitutes= {
              "out": "",
              "in": ""
          }
          this.props.matchInfo.matchInfo.cards[key].away_scorer_substitutes= {
              "out": "",
              "in": ""
          }
      })
      this.props.matchInfo.matchInfo.substitutes.map((r,key)=>{
          this.props.matchInfo.matchInfo.goals.push(this.props.matchInfo.matchInfo.substitutes[key])
      })
      this.props.matchInfo.matchInfo.cards.map((r,key)=>{
          this.props.matchInfo.matchInfo.goals.push(this.props.matchInfo.matchInfo.cards[key])
      })
      matchProgress=this.props.matchInfo.matchInfo.goals;
      let temp;
      for(let i=0;i<matchProgress.length;i++){
          for(let j=i+1;j<matchProgress.length;j++){
              if(matchProgress[i].time.indexOf('+')>0&&matchProgress[j].time.indexOf('+')>0){//两个分钟都含有加时
                  let arr1 = Number(matchProgress[i].time.substring(0,matchProgress[i].time.indexOf('+')));
                  let arr2 = Number(matchProgress[i].time.substring(matchProgress[i].time.indexOf('+'),matchProgress[i].time.length));
                  let arr3 = Number(matchProgress[j].time.substring(0,matchProgress[j].time.indexOf('+')));
                  let arr4 = Number(matchProgress[j].time.substring(matchProgress[j].time.indexOf('+'),matchProgress[j].time.length));
                  if(arr1>arr3)
                  {
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }else if(arr1==arr3&&arr2>arr4){
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }
              }else if(matchProgress[i].time.indexOf('+')>0&&matchProgress[j].time.indexOf('+')<=0){
                  let arr1 = Number(matchProgress[i].time.substring(0,matchProgress[i].time.indexOf('+')));
                  let arr2 = Number(matchProgress[i].time.substring(matchProgress[i].time.indexOf('+'),matchProgress[i].time.length));
                  let arr3 = Number(matchProgress[j].time);
                  
                  if(arr1>arr3)
                  {
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }else if(arr1==arr3){
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }
              }else if(matchProgress[i].time.indexOf('+')<=0&&matchProgress[j].time.indexOf('+')>0){
                  let arr1 = Number(matchProgress[i].time);
                  let arr3 = Number(matchProgress[j].time.substring(0,matchProgress[j].time.indexOf('+')));
                  let arr4 = Number(matchProgress[j].time.substring(matchProgress[j].time.indexOf('+'),matchProgress[j].time.length));
                  
                  if(arr1>arr3)
                  {
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }else if(arr1==arr3){
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }
              }else{
                  let arr1 = Number(matchProgress[i].time);
                  let arr3 = Number(matchProgress[j].time);
                  if(arr1>arr3)
                  {
                      temp=matchProgress[i];
                      matchProgress[i]=matchProgress[j];
                      matchProgress[j]=temp;
                  }
              }
              this.setState({ matchProgress: matchProgress })
          }
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
    if(document.getElementsByClassName("match-info")[0].getBoundingClientRect()){
        if(document.getElementsByClassName("match-info")[0].getBoundingClientRect().top>10){
            Toast.loading('加载中...', 30, () => {  
            });
            // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
            this.installLoading();
        }
    }
          
  }
  tabClick(event){
    let index = event.currentTarget.getAttribute("data-index");
    this.setState({ activeCategory: index })
    const {matchProgress} = this.state;
    document.getElementsByClassName('am-pull-to-refresh')[0].scrollTop=0;
    if(index=='0'){
        this.setState({ tab1: 'display_block',tab2:'display_none',tab3:'display_none',nodata:'display_none' })
    }else if(index=='1'){
        if(matchProgress.length==0){
            this.setState({ tab1: 'display_none',tab2:'display_none',tab3:'display_none',nodata:'display_block' })
        }else{
            this.setState({ tab1: 'display_none',tab2:'display_block',tab3:'display_none',nodata:'display_none' })
        }
        
    }else if(index=='2'){
        if(this.props.matchInfo.matchInfo.homestarting&&this.props.matchInfo.matchInfo.homestarting.length>0){
            this.setState({ tab1: 'display_none',tab2:'display_none',tab3:'display_block',nodata:'display_none'})
        }else{
            this.setState({ tab1: 'display_none',tab2:'display_none',tab3:'display_none',nodata:'display_block' })
        }
        
    }
  }
  onRefresh = () => {
    Toast.loading('加载中...', 30, () => {  
    });
    const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop;
    this.setState({
        height: hei
    }) 
    this.installLoading();
  };
  render() {
    const { matchInfo,matchClash ,matchRecent,newsList} = this.props.matchInfo
    const { matchRecentHome,matchRecentAway,matchProgress,tab1,tab2,tab3,activeCategory,nodata,contentState } = this.state
    let matchLineUpHome = [] //主队首发
    let matchLineUpAway = [] //客队首发
    let matchSubstitutesHome = [] //主队替补
    let matchSubstitutesAway = [] //客队替补
    let statistics = []//技术统计数据
    if(matchInfo.homestarting){
        matchLineUpHome = matchInfo.homestarting
    }
    if(matchInfo.awaystarting){
        matchLineUpAway = matchInfo.awaystarting
    }
    if(matchInfo.homesubstitutes){
        matchSubstitutesHome = matchInfo.homesubstitutes
    }
    if(matchInfo.awaysubstitutes){
        matchSubstitutesAway = matchInfo.awaysubstitutes
    }
    if(matchInfo.statistics){
        matchInfo.statistics.map((r,key)=>{
            let statisticsItem
            if(r.type=="Ball Possession"){
                statisticsItem = {
                    'type' : 'Ball Possession',
                    'home' : r.home.substring(0,r.home.length-1),
                    'away' : r.away.substring(0,r.away.length-1),
                    'homeratio' : Number(r.home.substring(0,r.home.length-1))/(Number(r.home.substring(0,r.home.length-1))+Number(r.away.substring(0,r.away.length-1)))*100+"%",
                    'awayratio' : Number(r.away.substring(0,r.home.length-1))/(Number(r.home.substring(0,r.home.length-1))+Number(r.away.substring(0,r.away.length-1)))*100+"%"
                }
            }else{
                statisticsItem = {
                    'type' : r.type,
                    'home' : r.home,
                    'away' : r.away,
                    'homeratio' : Number(r.home)/(Number(r.home)+Number(r.away))*100+"%",
                    'awayratio' : Number(r.away)/(Number(r.home)+Number(r.away))*100+"%"
                }
            }
            statistics[key]=statisticsItem
        })
    }
    // 处理近期交锋数据
    let clashResultNumber = { //赢、平、输的数目
        win : 0,
        drawn : 0,
        lost : 0
    }
    let clashResult = []
    matchClash.map((r,key) => {
        let splitResult = [];
        splitResult = r.finalresult.split('-');//把比分切割开进行比较
        if(r.hometeam == matchInfo.hometeam){ //比赛主队名称与头部主队名称一致
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ //win
                clashResultNumber.win++;
                clashResult.push('win');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){//drawn
                clashResultNumber.drawn++;
                clashResult.push('drawn');
            }else{
                clashResultNumber.lost++;
                clashResult.push('lost');
            }
        }else if(r.hometeam == matchInfo.awayteam){
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ 
                clashResultNumber.lost++;
                clashResult.push('lost');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){
                clashResultNumber.drawn++;
                clashResult.push('drawn');
            }else{
                clashResultNumber.win++;
                clashResult.push('win');
            }
        }
    });
    // 处理近期主队战绩
    let homeResultNumber = { //赢、平、输的数目
        win : 0,
        drawn : 0,
        lost : 0
    }
    let homeResult = []
    matchRecentHome.map((r,key) => {
        let splitResult = [];
        splitResult = r.finalresult.split('-');//把比分切割开进行比较
        if( matchInfo.hometeam == r.hometeam){ //比赛主队名称与头部主队名称一致
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ //win
                homeResultNumber.win++;
                homeResult.push('win');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){//drawn
                homeResultNumber.drawn++;
                homeResult.push('drawn');
            }else{
                homeResultNumber.lost++;
                homeResult.push('lost');
            }
        }else if(matchInfo.hometeam == r.awayteam){
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ 
                homeResultNumber.lost++;
                homeResult.push('lost');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){
                homeResultNumber.drawn++;
                homeResult.push('drawn');
            }else{
                homeResultNumber.win++;
                homeResult.push('win');
            }
        }
    });
    // 处理近期客队战绩
    let awayResultNumber = { //赢、平、输的数目
        win : 0,
        drawn : 0,
        lost : 0
    }
    let awayResult = []
    matchRecentAway.map((r,key) => {
        let splitResult = [];
        splitResult = r.finalresult.split('-');//把比分切割开进行比较
        if( matchInfo.awayteam == r.hometeam){ //比赛主队名称与头部主队名称一致
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ //win
                awayResultNumber.win++;
                awayResult.push('win');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){//drawn
                awayResultNumber.drawn++;
                awayResult.push('drawn');
            }else{
                awayResultNumber.lost++;
                awayResult.push('lost');
            }
        }else if(matchInfo.awayteam == r.awayteam){
            if(parseInt(splitResult[0])>parseInt(splitResult[1])){ 
                awayResultNumber.lost++;
                awayResult.push('lost');
            }else if(parseInt(splitResult[0])==parseInt(splitResult[1])){
                awayResultNumber.drawn++;
                awayResult.push('drawn');
            }else{
                awayResultNumber.win++;
                awayResult.push('win');
            }
        }
    });
    
    

    return (
      <PageContainer style={{ paddingTop: 0,paddingBottom:0 }} className={[contentState+" paddingTopNone paddingBottomNone"]}>
      	<PageContent id={['scorllTop']}>
            {/* 赛程详情 */}
            <div className={['match-info']}>
                <div className={['match-info-head']}>
                    <div className={['match-info-head-top']}>
                        <img className={['match-goback']} src={require('../../assets/icon-back-white.png')} onClick={() => this.props.history.go(-1)}/>
                        <h1>{moment(matchInfo.time).format("MM-DD HH:mm")}&nbsp;&nbsp;&nbsp;{matchInfo.categoryName+matchInfo.roundName}</h1>
                        <div className={['match-box']}>
                            <Link to={`/teamInfo/${matchInfo.hometeam}`}>
                                <div className={['match-info-hometeam']}>
                                    <div className={['match-info-teamIcon']}>
                                        <img src={matchInfo.hometeamIconUrl?`${ASSETS_URL}${matchInfo.hometeamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                                    </div>
                                    <span>{matchInfo.hometeamName}</span>
                                </div>
                            </Link>
                            <div className={['match-info-result']}>
                                <span className={['match-result']}>{matchInfo.finalresult==""||matchInfo.finalresult=="-"?"VS":matchInfo.finalresult}</span>   
                                <p>{matchInfo.status=="living"?matchInfo.progress:statusList[matchInfo.status]}</p>
                            </div>
                            <Link to={`/teamInfo/${matchInfo.awayteam}`}>
                                <div className={['match-info-awayteam']}>
                                    <div className={['match-info-teamIcon']}>
                                        <img src={matchInfo.awayteamIconUrl?`${ASSETS_URL}${matchInfo.awayteamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                                    </div>
                                    <span>{matchInfo.awayteamName}</span>
                                </div>
                            </Link>
                        </div>
                        
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
                {/* 数据部分 */}
                <div className={['match-data '+tab1]}>
                    <div className={['match-clash']}>
                        <h1 className={['match-title']}>历史交锋</h1>
                        <p className={['match-clash-info']}>
                            {matchInfo.hometeamName}&nbsp;&nbsp;VS&nbsp;&nbsp;{matchInfo.awayteamName}&nbsp;&nbsp;—&nbsp;&nbsp;
                            <span className={['win-number']}>{clashResultNumber.win}</span>胜&nbsp;<span className={['drawn-number']}>{clashResultNumber.drawn}</span>平
                            &nbsp;<span className={['lost-number']}>{clashResultNumber.lost}</span>负
                        </p>
                        <div className={['match-visualization']}>
                            <div className={['win-bg']}>{clashResultNumber.win}胜</div>
                            <div className={['drawn-bg']}>{clashResultNumber.drawn}平</div>
                            <div className={['lost-bg']}>{clashResultNumber.lost}负</div>
                        </div>
                        <table>
                            <tbody>
                                <tr><th width='12.5%' className={['text-center']}>日期</th>
                                    <th width='30%' className={['text-right']}>主队</th>
                                    <th width='16%' className={['text-center']}>比分</th>
                                    <th width='30%' className={['text-left']}>客队</th>
                                    <th className={['text-right']}>赛事</th>
                                </tr>
                                {
                                    matchClash.map((r,key)=>{
                                        return (
                                            <tr key={key}>
                                                <td className={['text-center']}>{moment(r.time).format("YYYY")}<br/>{moment(r.time).format("MM-DD")}</td>
                                                <td className={['text-right']}>{r.hometeamName}</td>
                                                <td className={['text-center']}><span className={['result-'+clashResult[key]]}>{r.finalresult.replace("-",":")}</span></td>
                                                <td className={['text-left']}>{r.awayteamName}</td>
                                                <td className={['text-right']}>{r.categoryName}</td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                    {/* 近期战绩 */}
                    <div className={['match-recent']}>
                        <h1 className={['match-title']}>近期战绩</h1>
                        <p className={['match-clash-info']}>
                            <img src={matchInfo.hometeamIconUrl?`${ASSETS_URL}${matchInfo.hometeamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                            {matchInfo.hometeamName}&nbsp;&nbsp;—&nbsp;&nbsp;
                            <span className={['win-number']}>{homeResultNumber.win}</span>胜&nbsp;<span className={['drawn-number']}>{homeResultNumber.drawn}</span>平
                            &nbsp;<span className={['lost-number']}>{homeResultNumber.lost}</span>负
                        </p>
                        
                        <table>
                            <tbody>
                                <tr><th width='12.5%' className={['text-center']}>日期</th>
                                    <th width='30%' className={['text-right']}>主队</th>
                                    <th width='16%' className={['text-center']}>比分</th>
                                    <th width='30%' className={['text-left']}>客队</th>
                                    <th className={['text-right']}>赛事</th>
                                </tr>
                                {
                                    matchRecentHome.map((r,key)=>{
                                        return (
                                            <tr key={key}>
                                                <td className={['text-center']}>{moment(r.time).format("YYYY")}<br/>{moment(r.time).format("MM-DD")}</td>
                                                <td className={['text-right']}>{r.hometeamName}</td>
                                                <td className={['text-center']}><span className={['result-'+homeResult[key]]}>{r.finalresult.replace("-",":")}</span></td>
                                                <td className={['text-left']}>{r.awayteamName}</td>
                                                <td className={['text-right']}>{r.categoryName}</td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>


                        <p className={['match-clash-info']}>
                            <img src={matchInfo.awayteamIconUrl?`${ASSETS_URL}${matchInfo.awayteamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                            {matchInfo.awayteamName}&nbsp;&nbsp;—&nbsp;&nbsp;
                            <span className={['win-number']}>{awayResultNumber.win}</span>胜&nbsp;<span className={['drawn-number']}>{awayResultNumber.drawn}</span>平
                            &nbsp;<span className={['lost-number']}>{awayResultNumber.lost}</span>负
                        </p>
                        <table>
                            <tbody>
                                <tr><th width='12.5%' className={['text-center']}>日期</th>
                                    <th width='30%' className={['text-right']}>主队</th>
                                    <th width='16%' className={['text-center']}>比分</th>
                                    <th width='30%' className={['text-left']}>客队</th>
                                    <th className={['text-right']}>赛事</th>
                                </tr>
                                {
                                    matchRecentAway.map((r,key)=>{
                                        return (
                                            <tr key={key}>
                                                <td className={['text-center']}>{moment(r.time).format("YYYY")}<br/>{moment(r.time).format("MM-DD")}</td>
                                                <td className={['text-right']}>{r.hometeamName}</td>
                                                <td className={['text-center']}><span className={['result-'+awayResult[key]]}>{r.finalresult.replace("-",":")}</span></td>
                                                <td className={['text-left']}>{r.awayteamName}</td>
                                                <td className={['text-right']}>{r.categoryName}</td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
                {/* 赛况 */}
                <div className={['match-outs '+tab2]}>
                    {/* 比赛数据 */}
                    <h1 className={['match-title']}>比赛数据</h1>
                    <div className={['match-progress']}>
                        <div className={['match-progress-top']}>
                            <img src={require('../../assets/icon-match-yellowCards.png')} />
                            <span>黄牌</span>
                            <img src={require('../../assets/icon-match-goal.png')} />
                            <span>进球</span>
                            <img src={require('../../assets/icon-match-replaceLG.png')} />
                            <img src={require('../../assets/icon-match-replaceRG.png')} />
                            <span>上场</span>
                            <img src={require('../../assets/icon-match-replaceLR.png')} />
                            <img src={require('../../assets/icon-match-replaceRR.png')} />
                            <span>下场</span>
                        </div>
                        <img className={['match-whistle']} src={require('../../assets/icon-match-whistle.png')} />
                        <ul>
                           {
                               matchProgress.map((r,key)=>{
                                   return (
                                    <li className={["active "+r.owntype+" "+r.card]} key={key}>
                                        <div className={['leftcon']}>
                                            {/* 左侧黄牌 */}
                                            <div className={['cards-content-left']}>
                                                <span>{r.home_fault}</span>
                                                <img src={require('../../assets/icon-match-yellowCards.png')} />
                                            </div>
                                            {/* 左侧进球 */}
                                            <div className={['golds-content-left']}>
                                                <span>{r.home_scorer_goals}</span>
                                                <img src={require('../../assets/icon-match-goal.png')} />
                                            </div>
                                            {/* 左侧上下场 */}
                                            <div className={['substitutes-content-left']}>
                                                <p>
                                                    <img src={require('../../assets/icon-match-replaceLG.png')} />
                                                    <span>{r.home_scorer_substitutes.in}</span>
                                                </p>
                                                <p>
                                                    <span>{r.home_scorer_substitutes.out}</span>
                                                    <img src={require('../../assets/icon-match-replaceLR.png')} />
                                                </p>
                                            </div>
                                            <p className="time-left">{r.time}'</p>
                                            {/* 中间内容 */}
                                            <div className={['centercon']}>
                                                {/* 中心园 */}
                                                <b><span className={['cycle']}></span></b>
                                                {/* 比分 */}
                                                <span className={['score']}>{r.score_goals}</span>
                                            </div>
                                        </div>
                                        <p className={['line']}></p>
                                        <div className={['rightcon']}>
                                            {/* 右侧黄牌 */}
                                            <div className={['cards-content-right']}>
                                                <img src={require('../../assets/icon-match-yellowCards.png')} />
                                                <span>{r.away_fault}</span>
                                            </div>
                                            {/* 右侧进球 */}
                                            <div className={['golds-content-right']}>
                                                <img src={require('../../assets/icon-match-goal.png')} />
                                                <span>{r.away_scorer_goals}</span>
                                            </div>
                                            {/* 右侧上下场 */}
                                            <div className={['substitutes-content-right']}>
                                                <p>
                                                    <span>{r.away_scorer_substitutes.in}</span>                                                
                                                    <img src={require('../../assets/icon-match-replaceRG.png')} />
                                                </p>
                                                <p>
                                                    <img src={require('../../assets/icon-match-replaceRR.png')} />
                                                    <span>{r.away_scorer_substitutes.out}</span>
                                                </p>
                                            </div>
                                            <p className="time-right">{r.time}'</p>
                                        </div>
                                        
                                    </li>
                                   )
                               })
                           }     
                        </ul>
                        <img className={['match-whistle']} src={require('../../assets/icon-match-whistle.png')} />
                    </div>

                    {/* 技术统计 */}
                    <h1 className={['match-title']}>技术统计</h1>
                    {
                        statistics.map((r,key)=>{
                            return (
                                <div className={['match-statistics-item']} key={key}>
                                    <div className={['match-statistics-item-top']}>
                                        <p className={['text-left']}>{r.home}</p>
                                        <p className={['text-center']}>{statisticsList[r.type]}</p>
                                        <p className={['text-right']}>{r.away}</p>
                                    </div>
                                    <div className={['match-statistics-item-bottom']}>
                                        <p style={{width:r.homeratio}}><span></span></p>
                                        <p style={{width:r.awayratio}}><span></span></p>
                                    </div>
                                </div>
                            )
                        })
                    }
                    
                    
                </div>
                {/* 阵容 */}
                <div className={['match-lineUp '+tab3]}>
                    <div className={['match-lineUp-topBox']}>
                        <p className={['match-clash-info']}>
                            <img src={matchInfo.hometeamIconUrl?`${ASSETS_URL}${matchInfo.hometeamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                            {matchInfo.hometeamName}
                        </p>
                        <p className={['match-clash-info']}>
                            <img src={matchInfo.awayteamIconUrl?`${ASSETS_URL}${matchInfo.awayteamIconUrl}`:require('../../assets/pic-match-team.png')}  />
                            {matchInfo.awayteamName}
                        </p>
                    </div>
                    <h1>首发</h1>
                    <div className={['match-lineUp-list']}>
                        <ul>                          
                            {
                                matchLineUpHome.map((r,key)=>{
                                    return (
                                        <li key={key}>
                                            <span>#{r.player_number}</span>
                                            <b>{r.player}</b>
                                        </li>
                                    )
                                })
                                
                            }
                        </ul>
                        <ul>                          
                            {
                                matchLineUpAway.map((r,key)=>{
                                    return (
                                        <li key={key}>
                                            <span>#{r.player_number}</span>
                                            <b>{r.player}</b>
                                        </li>
                                    )
                                })
                                
                            }
                        </ul>
                    </div>
                    <h1>替补</h1>
                    <div className={['match-lineUp-list']}>
                        <ul>
                            {
                                matchSubstitutesHome.map((r,key)=>{
                                    return (
                                        <li key={key}>
                                            <span>#{r.player_number}</span>
                                            <b>{r.player}</b>
                                        </li>
                                    )
                                })
                            }
                        </ul>
                        <ul>
                            {
                                matchSubstitutesAway.map((r,key)=>{
                                    return (
                                        <li key={key}>
                                            <span>#{r.player_number}</span>
                                            <b>{r.player}</b>
                                        </li>
                                    )
                                })
                            }
                        </ul>
                    </div>
                </div>
                <div className={['match-nodata '+nodata]}>
                    <img src={require(`../../assets/pic-nodata-match.png`)} />
                    <p>数据和守门员都走丢了，先看会新闻等等吧~</p>
                </div>
                {/* 相关推荐 */}
                <div className={['related-recommend']}>
                    <div className={['related-title']}>
                        <span></span>
                        <h3>相关新闻</h3>
                        <span></span>
                    </div>
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
                </PullToRefresh>
            </div>
        </PageContent>
      </PageContainer>
    );
  }
}

export default MatchInfo;
