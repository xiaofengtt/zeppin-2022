import React, { Component,PropTypes } from 'react';
import { connect } from 'dva'
import { Link } from 'dva/router'
import autobind from 'autobind-decorator'
import { Tabs } from 'antd-mobile'
import { Toast, PullToRefresh, ListView } from 'antd-mobile';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import moment from 'moment';
import ReactDOM from 'react-dom';


import BasicHeader from '../../components/BasicHeader'
import PageContainer from '../../components/PageContainer'
import PageContent from '../../components/PageContent'
import BasicFooter from '../../components/BasicFooter'


import styles from './index.less'
import {ASSETS_URL} from "../../config";

const categoryList = [
    {
        title : "头条",
        uuid : "",
        key : 0
    }, {
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
const tabs = [
    { title: 'First Tab' },
    { title: 'Second Tab' },
    { title: 'Third Tab' },
  ];
@connect(({ home, loading }) => ({ home, loading }))
class Home extends Component {
    constructor(props) {
        super(props)
        this.state = {
            activeCategory: 0,
            pagesize : 10,
            pagenum : 1,
            contentState : 'display_none',
            listData : [],
            nextFlag : true,//是否可以翻页
            shell:'display_none',
            height: document.documentElement.clientHeight,
            newsHasdata:'display_none',
        }
        this.handleTouch = this.handleTouch.bind(this);
    }
    componentWillMount(){
        //弹屏 如果第一次加载 就显示 
        if(sessionStorage.getItem("index")){
            this.setState({shell:'display_none'});
        }else{
            this.setState({shell:'display_block'});
            sessionStorage.setItem("index","1");
        }
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {
    
        });
        //弹屏 三秒后自动关闭
        setTimeout(function(){
            this.setState({shell:'display_none'});
        }.bind(this),3000) 
    }
  componentDidMount() {
        const { dispatch } = this.props
        //新闻
        dispatch({ type: 'home/getNewsList',payload:{pageSize:this.state.pagesize,pageNum:this.state.pagenum,category:categoryList[0].uuid}}).then(result => {
            this.setState({listData:result})
            //赛程
            dispatch({ type: 'home/getMatchListNocategory',payload:{pageSize:this.state.pagesize,pageNum:this.state.pagenum,category:categoryList[0].uuid} }).then(()=>{
                Toast.hide();
                this.setState({contentState:'display_block'})
                document.getElementById("root").addEventListener('touchend', this.handleTouch) 
            }) 
        })  
        const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight;
        const hei = this.state.height - ReactDOM.findDOMNode(this.ptr).offsetTop-offsetheight;
         this.setState({
             height: hei
         }) 
  }
  componentWillUnmount(){
    //取消监听滑动
    document.getElementById("root").removeEventListener('touchend', this.handleTouch)
    this.setState = (state,callback)=>{
        return;
    };
  }
  handleTouch = () =>{  
    const { dispatch } = this.props
    if(document.getElementsByClassName("am-tabs-default-bar-top")[0]&&document.getElementsByClassName("tabsContent")[this.state.activeCategory]){
        const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight
        if(document.getElementsByClassName("tabsContent")[this.state.activeCategory].getBoundingClientRect().top>offsetheight+10){
            this.getSecondCategory(categoryList[this.state.activeCategory])
        }
        let largeHeight = document.getElementById("root").offsetHeight;
        let bottomHeight = document.getElementsByClassName("am-tab-bar")[0].offsetHeight;
        let height = document.getElementsByClassName("tabsContent")[this.state.activeCategory].getBoundingClientRect().bottom;
        if(height<largeHeight-bottomHeight+50&&this.state.nextFlag){           
            dispatch({ type: 'home/getNewsList',payload:{pageSize:this.state.pagesize,pageNum:this.state.pagenum+1,category:categoryList[this.state.activeCategory].uuid}}).then(result => {
                setTimeout(function(){
                    Toast.hide();
                },500)                            
                this.setState({pagenum:this.state.pagenum+1,listData:this.state.listData.concat(result)})
                if(result.length<this.state.pagesize){
                    this.setState({nextFlag:false,newsHasdata:'display_block'})
                } 
            })
            const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight-offsetheight;
            this.setState({
                height: hei
            })  
        }
    }
          
  }
  getSecondCategory(categoryId) {
    const { dispatch } = this.props
    this.setState({ activeCategory: categoryId.key,nextFlag:true,pagenum:1,newsHasdata:'display_none'})
    dispatch({ type: 'home/getNewsList',payload:{pageSize:this.state.pagesize,pageNum:1,category:categoryId.uuid}}).then(result => {
        this.setState({listData:result})
        setTimeout(function(){
            Toast.hide();
        },500)  
        if(result.length<this.state.pagesize){
            this.setState({nextFlag:false,newsHasdata:'display_block'})
        } 
        const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight-offsetheight;
        this.setState({
            height: hei
        })  
    })
    if(categoryId.key==0){
        dispatch({ type: 'home/getMatchListNocategory',payload:{pageSize:this.state.pagesize,pageNum:1,category:categoryList[0].uuid} }) 
    }else{
        dispatch({ type: 'home/getMatchList',payload:'pageSize='+this.state.pagesize+'&pageNum=1&category='+categoryId.uuid+'&sort=time desc' })
        
    }
    
  }   
    @autobind
    handleFirstCategoryClick(categoryId) {
        this.getSecondCategory(categoryId)
    }
    skip(){
        this.setState({shell:'display_none'});
    }
    onRefresh = () => {
        this.setState({ refreshing: true, isLoading: true });
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight-document.getElementsByClassName("am-navbar")[0].offsetHeight;
        setTimeout(() => {
          this.setState({
            refreshing: false,
            isLoading: false,
            pagenum:1,
            height: hei,
          });
        }, 600);
        this.getSecondCategory(categoryList[this.state.activeCategory])
      };

  render() {
    const { activeCategory,contentState,listData,shell,newsHasdata } = this.state
    const { matchList } = this.props.home
    const matchsList = matchList.slice(0, 2)
    return (
      <PageContainer>
        <BasicHeader>
            <img className="ttbf-title-logo" src={require('../../assets/ttbf-title-logo.png')} />
        </BasicHeader>
      	<PageContent className={[contentState]}>
            <Tabs tabs={categoryList} initialPage={0} useOnPan={false} animated={false}
                onChange={(tab, index) => {this.handleFirstCategoryClick(tab)}}
                onTabClick={(tab, index) => {this.handleFirstCategoryClick(tab)}}
                renderTabBar={props => <Tabs.DefaultTabBar {...props} page={7} />}>

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
                    <div className={['tabsContent']} style={{ backgroundColor:'rgba(0,0,0,0)',}}>
                        {/* banner区新闻 */}
                        <div className={['home-banner']}>
                                <img src={require('../../assets/banner-index.png')} />
                            </div>
                            {/* 赛事 */}
                            <div className={['home-match']}>
                                <ul>
                                    {
                                        matchsList.map((r,key) => {
                                            return (
                                                <li key={key}>
                                                <Link to={`/matchInfo/${r.uuid}`}>    
                                                    <h2>{r.categoryName+r.roundName}</h2>
                                                    <span className={'home-match-status home-match-'+r.status}>{r.status=='unstart'?moment(r.time).format("MM-DD HH:mm"):statusList[r.status]}</span>
                                                    <div className={['home-match-left']}>
                                                        <b><img src={r.hometeamIconUrl?`${ASSETS_URL}${r.hometeamIconUrl}`:require('../../assets/pic-match-team.png')} /></b>
                                                        <span className={['teamName']}>{r.hometeamName}</span>
                                                        <span className={['match-result']}>{r.finalresult=="-"||r.finalresult==""?"-":r.finalresult.substring(0,r.finalresult.lastIndexOf('-'))}</span>
                                                    </div>
                                                    <div className={['home-match-right']}>
                                                        <b><img src={r.awayteamIconUrl?`${ASSETS_URL}${r.awayteamIconUrl}`:require('../../assets/pic-match-team.png')} /></b>
                                                        
                                                        <span className={['teamName']}>{r.awayteamName}</span>
                                                        <span className={['match-result']}>{r.finalresult=="-"||r.finalresult==""?"-":r.finalresult.substring(r.finalresult.lastIndexOf('-')+1,r.finalresult.length)}</span>
                                                    </div>
                                                    </Link>
                                                </li>
                                            )
                                        })
                                    }
                                </ul>
                            </div>
                            {/* 新闻列表 */}
                            <div className={['home-newslist']}>
                                <ul>
                                    {
                                    listData.map((r, key) => {
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
                                <p className={['lookMore '+newsHasdata]}>——&nbsp;&nbsp;好像已经到底了&nbsp;&nbsp;——</p>
                            </div>

                        </div>         
                </PullToRefresh>
            </Tabs>
        </PageContent>
        <BasicFooter />    
        {/* 启动弹屏 */}
        <div className={['shell-screen '+shell]}>
            <a onClick={()=>this.skip()}>跳过</a>
            <div className={['splash-bg']}></div>
            {/* <img className={['splash-bg']} src={require('../../assets/pic-splash-bg.png')} /> */}
            <img className={['splash-logo']} src={require('../../assets/pic-splash-logo.png')} />
        </div> 
      </PageContainer>
    );
  }
}

export default Home;
