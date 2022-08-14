import React, { Component } from 'react';
import { connect } from 'dva'
import { Link } from 'dva/router'
import numeral from 'numeral'
import autobind from 'autobind-decorator'
import { Flex, Tabs,Toast, PullToRefresh, ListView } from 'antd-mobile'
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import moment from 'moment';
import ReactDOM from 'react-dom'

import BasicHeader from '../../components/BasicHeader'
import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import BasicFooter from '../../components/BasicFooter'


import styles from './index.less'
import {ASSETS_URL} from "../../config";
import { element } from 'prop-types';

const categoryList = [
    {
        title : "中超",
        uuid : "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e",
        key : 1
    }, {
        title : "英超",
        uuid : "9bd4e736-e57f-46d2-ab25-b91a4c36b061",
        key : 2
    }, {
        title : "西甲",
        uuid : "dad45ea2-5c9d-4102-8445-b9720267f93d",
        key : 3
    }, {
        title : "意甲",
        uuid : "adf1fb28-306d-4870-96e9-875402d044b7",
        key : 4
    },{
        title : "德甲",
        uuid : "42fee8ba-677f-4152-b10c-69d3befc6467",
        key : 5
    }, {
        title : "欧冠",
        uuid : "5f61cb0b-8d40-4449-9d25-cbcddde89a57",
        key : 6
    }, {
        title : "亚冠",
        uuid : "5c3a7159-70e5-490e-b242-328c2f5c3cc1",
        key : 7
    },
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
@connect(({ match, loading }) => ({ match, loading }))
class Match extends Component {
    constructor(props) {
        super(props)
        this.state = {
            activeCategory: 1,
            pagesize : 20,
            pagenum : 1,
            contentState:'display_none',
            listData : [],
            nextFlag : true,//是否可以向下翻页
            preFlag : true,//是否可以向上翻页
            pagenumPre :1,//向上翻页
            firstData : '',//第一条数据日期
            height: document.documentElement.clientHeight,
        }
    }
  componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {

        });
      const { dispatch } = this.props
      dispatch({ type: 'match/getMatchList',payload:'pageSize='+this.state.pagesize+'&pageNum='
      +(this.state.pagenum)+'&category='+categoryList[0].uuid+'&starttime='+this.getNowFormatDate(0)+ ' 00:00'+'&sort=time'}).then(result=>{
        this.setState({listData:result})
        if(this.state.listData.length>0){
            this.setState({firstData:moment(this.state.listData[0].time).format("YYYY-MM-DD")})
        }else{
            dispatch({ type: 'match/getMatchList',payload:'pageSize='+this.state.pagesize+'&pageNum='+this.state.pagenumPre+'&category='+categoryList[0].uuid+'&endtime='+this.getNowFormatDate(0)+ ' 00:00'+'&sort=time desc'}).then(result=>{
                this.setState({listData:result.reverse(),pagenumPre:this.state.pagenumPre+1,firstData:moment(result[result.length-1].time).format("YYYY-MM-DD")})
            })
        }
        Toast.hide();
        this.setState({contentState:'display_block'})
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop;
         this.setState({
             height: hei
         }) 
      })
  }
  componentWillUnmount(){
    this.setState = (state,callback)=>{
        return;
    };
  }

  scrollToAnchor = () => {
    const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight                 
    const firstData = this.state.firstData
    let offsetTop = document.getElementById(firstData).offsetTop-40
    if(offsetTop<0){
        offsetTop = document.getElementsByClassName('am-tabs-pane-wrap-active')[0].firstElementChild.firstElementChild.firstElementChild.offsetHeight;
    }
    document.getElementsByClassName('am-tabs-pane-wrap-active')[0].firstElementChild.firstElementChild.scrollTop=offsetTop;
}
  getNowFormatDate(days) {
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let strDate = date.getDate()+days;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    let currentdate = year + '-' + month + '-'  + strDate ;
    return currentdate;
}
  getSecondCategory(categoryId,pageSize,pageNum,startTime,endTime) {
    const { dispatch } = this.props
    dispatch({ type: 'match/getMatchList',payload:'pageSize='+pageSize+'&pageNum='+pageNum+'&category='+categoryId+'&starttime='+endTime+ ' 00:00'+'&sort=time'}).then(result=>{
        this.setState({listData:result})
        if(this.state.listData.length>0){
            this.setState({firstData:moment(this.state.listData[0].time).format("YYYY-MM-DD")})
        }else{
            dispatch({ type: 'match/getMatchList',payload:'pageSize='+pageSize+'&pageNum='+this.state.pagenumPre+'&category='+categoryId+'&endtime='+endTime+ ' 00:00'+'&sort=time desc'}).then(result=>{
                this.setState({listData:result.reverse(),pagenumPre:this.state.pagenumPre+1,firstData:moment(result[result.length-1].time).format("YYYY-MM-DD")})
            })
        }

    })
  }   
  @autobind
    handleFirstCategoryClick( categoryId ) {
        document.getElementById('scorllTop').scrollTop=0;
        this.setState({ activeCategory: categoryId.key,nextFlag:true,preFlag:true,pagenum:1,pagenumPre:1 })
        this.getSecondCategory(categoryId.uuid,this.state.pagesize,1,'',this.getNowFormatDate(0))
    }
    onRefresh = () => {
        const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight                       
        this.props.dispatch({ type: 'match/getMatchList',payload:'pageSize='+this.state.pagesize+'&pageNum='
        +(this.state.pagenumPre)+'&category='+categoryList[this.state.activeCategory-1].uuid+'&endtime='+this.getNowFormatDate(0)+ ' 23:59'+'&sort=time desc'}).then(result=>{
            const firstData = moment(this.state.listData[0].time).format("YYYY-MM-DD");
            const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-offsetheight-document.getElementsByClassName("am-tab-bar")[0].offsetHeight;
            Toast.hide();
            this.setState({
                refreshing: false,
                isLoading: false,
                height: hei,
                pagenumPre:this.state.pagenumPre+1,
                listData:[].concat(result.reverse(),this.state.listData),
            })
            if(result.length<this.state.pagesize){
                this.setState({preFlag:false})
            }       
            let scrollTop = document.getElementById(firstData).getBoundingClientRect().top - offsetheight-80;
            if(scrollTop<0){
                scrollTop = document.getElementsByClassName('am-tabs-pane-wrap-active')[0].firstElementChild.firstElementChild.firstElementChild.offsetHeight;
            }
            document.getElementsByClassName('am-tabs-pane-wrap-active')[0].firstElementChild.firstElementChild.scrollTop=scrollTop;
        })
        
      };
        
  render() {
    const { listData,contentState } = this.state
    const { matchList } = this.props.match
    return (
      <PageContainer>
        <BasicHeader>
            <img className="ttbf-title-logo" src={require('../../assets/ttbf-title-logo.png')} />
        </BasicHeader>
      	<PageContent id={['scorllTop']} className={[contentState]}>
          <Tabs animated={false} tabs={categoryList} initialPage={0} useOnPan={false} 
                    // onChange={(tab) => {this.handleFirstCategoryClick(tab)}}
                    onTabClick={(tab) => {this.handleFirstCategoryClick(tab)}}
                    renderTabBar={props => <Tabs.DefaultTabBar {...props} page={7} />}
                    style={{ backgroundColor:'rgba(0,0,0,0)',position:'relative'}}>          
            <div className={['tabsContent']} style={{ backgroundColor:'rgba(0,0,0,0)',top:'0'}}>
            <PullToRefresh
                damping={60}
                style={{
                    height: this.state.height,
                    overflow: 'auto',
                }}
                ref={el => this.ptr = el}
                refreshing={this.state.refreshing}
                direction={'down'}
                onRefresh={this.onRefresh}
                indicator={this.state.down ? {} : { deactivate: '上拉加载更多数据' }}>
                {/* 赛程 */}
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
            </PullToRefresh>
            </div>
            </Tabs>
            <p className={['goToday']} onClick={()=>this.scrollToAnchor()}>返回今天</p>
        </PageContent>
        <BasicFooter />
      </PageContainer>
    );
  }
}

export default Match;
