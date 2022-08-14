import React, { Component } from 'react'
import { connect } from 'dva'
import PageContainer from "../../components/PageContainer";
import BasicHeader from "../../components/BasicHeader";
import {Icon,Toast,PullToRefresh} from "antd-mobile";
import PageContent from "../../components/PageContent";
import autobind from 'autobind-decorator'
import Base64  from 'base-64';
import MD5  from 'md5';
import ReactDOM from 'react-dom';

import styles from "./index.less";
import {ASSETS_URL} from "../../config";
import numeral from "numeral";

const categoryList = [
  {
      name : "已关注",
      uuid : "",
      key : 0
  }, {
    name : "中超",
    uuid : "f4e00df4-6f3f-4de3-b64e-0b2b6910e07e",
    key : 1
  }, {
      name : "英超",
      uuid : "9bd4e736-e57f-46d2-ab25-b91a4c36b061",
      key : 2
  }, {
      name : "西甲",
      uuid : "dad45ea2-5c9d-4102-8445-b9720267f93d",
      key : 3
  }, {
      name : "意甲",
      uuid : "adf1fb28-306d-4870-96e9-875402d044b7",
      key : 4
  },{
      name : "德甲",
      uuid : "42fee8ba-677f-4152-b10c-69d3befc6467",
      key : 5
  }, {
      name : "欧冠",
      uuid : "5f61cb0b-8d40-4449-9d25-cbcddde89a57",
      key : 6
  }, {
      name : "亚冠",
      uuid : "5c3a7159-70e5-490e-b242-328c2f5c3cc1",
      key : 7
  }
]

@connect(({ favorites, loading }) => ({ favorites, loading }))
class MyCollect extends Component {
  constructor(props) {
    super(props)
    this.state = {
        activeCategory: 0,
        collectList : [],
        activeType : '',
        nodata:'display_block',
        hasdata:'display_none',
        contentState:'display_none',
        height: document.documentElement.clientHeight,
    }
    // this.handleTouch = this.handleTouch.bind(this);
  }
  componentDidMount() {
    this.setState({contentState:'display_none'})
    Toast.loading('加载中...', 30, () => {

    });
    const { dispatch } = this.props
    const { teamList,concrenList } = this.props.favorites
    let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
            dispatch({ type: 'favorites/getConcrenList',payload:{token:token}}).then(()=>{
              this.props.favorites.concrenList.map((r,key)=>{
                if(this.props.favorites.concrenList[key].teamName){
                  this.props.favorites.concrenList[key].name = this.props.favorites.concrenList[key].teamName;
                  this.props.favorites.concrenList[key].iconUrl = this.props.favorites.concrenList[key].teamIconUrl;
                  this.props.favorites.concrenList[key].className = "active";
                  this.props.favorites.concrenList[key].teamCancle = this.props.favorites.concrenList[key].uuid 
                  this.props.favorites.concrenList[key].teamUuid = this.props.favorites.concrenList[key].team
                }
              })
              this.setState({ collectList: this.props.favorites.concrenList})
              if(this.props.favorites.concrenList.length==0){
                this.setState({ hasdata: 'display_none',nodata:'display_block' })
              }else{
                this.setState({ hasdata: 'display_block',nodata:'display_none' })
              }
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
  }
  componentWillUnmount(){
    // document.getElementById("root").removeEventListener('touchend', this.handleTouch)
    this.setState = (state,callback)=>{
      return;
    };
  }
  handleTouch = () =>{  
    Toast.loading('加载中...', 30, () => {
    
    });
    this.getSecondCategory(categoryList[this.state.activeCategory-1])
          
  }
  getSecondCategory(categoryId,uuid) {
    const { dispatch } = this.props
    const { teamList,concrenList } = this.props.favorites
    let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
    if(categoryId==0){
        dispatch({ type: 'favorites/getConcrenList',payload:{token:token}},).then(()=>{
          this.props.favorites.concrenList.map((r,key)=>{
            if(this.props.favorites.concrenList[key].teamName){
              this.props.favorites.concrenList[key].name = this.props.favorites.concrenList[key].teamName;
              this.props.favorites.concrenList[key].iconUrl = this.props.favorites.concrenList[key].teamIconUrl;
              this.props.favorites.concrenList[key].className = "active"
              this.props.favorites.concrenList[key].teamCancle = this.props.favorites.concrenList[key].uuid 
              this.props.favorites.concrenList[key].teamUuid = this.props.favorites.concrenList[key].team
            }
          })
          this.setState({ collectList: this.props.favorites.concrenList})
          if(this.props.favorites.concrenList.length==0){
            this.setState({ hasdata: 'display_none',nodata:'display_block' })
          }else{
            this.setState({ hasdata: 'display_block',nodata:'display_none' })
          }
          setTimeout(function(){
            Toast.hide();
          },500) 
        })

    }else{
      dispatch({ type: 'favorites/getConcrenList',payload:{token:token}}).then(()=>{
        this.props.favorites.concrenList.map((r,key)=>{
          if(this.props.favorites.concrenList[key].teamName){
            this.props.favorites.concrenList[key].name = this.props.favorites.concrenList[key].teamName;
            this.props.favorites.concrenList[key].iconUrl = this.props.favorites.concrenList[key].teamIconUrl;
            this.props.favorites.concrenList[key].className = "active"
            this.props.favorites.concrenList[key].teamCancle = this.props.favorites.concrenList[key].uuid 
            this.props.favorites.concrenList[key].teamUuid = this.props.favorites.concrenList[key].team
          }
        })
      }).then(()=>{
        dispatch({ type: 'favorites/getCollectTeamList',payload:{uuid:uuid}}).then(()=>{
          this.props.favorites.teamList.map((r,key)=>{
            this.props.favorites.concrenList.map((rs,keys)=>{
              if(r.uuid==rs.team){
                this.props.favorites.teamList[key].className = "active";
                this.props.favorites.teamList[key].teamCancle = this.props.favorites.concrenList[keys].uuid 
              }else{
                this.props.favorites.teamList[key].teamCancle = ""
              }
            })
            
            this.props.favorites.teamList[key].teamUuid = this.props.favorites.teamList[key].uuid 
          })
          this.setState({ collectList: this.props.favorites.teamList})
          if(this.props.favorites.teamList.length==0){
            this.setState({ hasdata: 'display_none',nodata:'display_block' })
          }else{
            this.setState({ hasdata: 'display_block',nodata:'display_none' })
          }
        })
      })
      
    }
  })
  }  
  handleCencernClick = (event) =>{
    let index = event.currentTarget.getAttribute("data-index");
    let active = event.currentTarget.getAttribute("data-state");
    let cancleuuid = event.currentTarget.getAttribute("data-cancleuuid")
    let adduuid = event.currentTarget.getAttribute("data-uuid")
    const { dispatch } = this.props
    const { collectList } = this.state
    let device = "01";
        let Key = "5fa526a2bb401ffddb888d1f0445ec6a";
        let openId = localStorage.getItem("mobile");
        let md5 = "";
        let token = "";
        dispatch({ type: 'login/getTime' }).then((r)=>{
            md5 = MD5(Key + r.data + openId);
            token = Base64.encode(device + r.data + openId + md5);
            if(active=="active"){
              this.props.dispatch({ type: 'favorites/cancleConcren',payload:{uuid:cancleuuid,token:token}}).then(data=>{
                if(data.status=="SUCCESS"){
                  collectList[index].className = "";
                  this.setState({ collectList: collectList})
                }
              })
            }else{
              this.props.dispatch({ type: 'favorites/addConcren',payload:{uuid:adduuid,token:token}}).then(data=>{
                if(data.status=="SUCCESS"){
                  collectList[index].className = "active";
                  collectList[index].teamCancle = data.data.uuid;
                  this.setState({ collectList: collectList})
                }
              })
            }
        })
  }
  onRefresh = () => {
    this.setState({ refreshing: true, isLoading: true });
    const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-navbar")[0].offsetHeight;
    setTimeout(() => {
      this.setState({
        refreshing: false,
        isLoading: false,
        height: hei,
      });
    }, 600);
    this.getSecondCategory(categoryList[this.state.activeCategory].key,categoryList[this.state.activeCategory].uuid)
  };
  @autobind
    handleFirstCategoryClick( categoryId ) {
        this.setState({ activeCategory: categoryId.key })
        this.getSecondCategory(categoryId.key,categoryId.uuid)
    }
  render() {
    const { activeCategory,collectList,nodata,hasdata,contentState } = this.state
    
    return (
      <PageContainer style={{ paddingBottom: 0 }} className={[contentState+' paddingBottomNone']}>
        <BasicHeader >
          <img src={require('../../assets/icon-back.png')} onClick={() => this.props.history.go(-1)}/>我的关注
        </BasicHeader>
        
        <PageContent>
            {/* 分类 */}
            <div className={['collect-category']}>
                  <ul className={['collect-category-list']}>
                      {
                          categoryList.map((r, key) => {
                              return (
                                  <li key={key} uuid={r.uuid} className={activeCategory === r.key ? ['active'] : ''}
                                      onClick={() => this.handleFirstCategoryClick(r)}
                                  >
                                      <span>{r.name}</span>
                                  </li>
                              )
                          })
                      }
                  </ul>
              </div>
              {/* 球队列表 */}
              <ul className={['collect-team-list '+hasdata]}>
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
                {
                  collectList.map((r,key)=>{
                    return(
                      <li key={key} data-uuid={r.teamUuid} data-cancleuuid={r.teamCancle} data-index={key} data-state={r.className} className={[`${r.className}`]} 
                      onClick={(e) => this.handleCencernClick(e)} >
                          <b>
                            <img src={r.iconUrl? `${ASSETS_URL}${r.iconUrl}`:require('../../assets/pic-match-team.png')} />
                          </b>
                          <p>{r.name?r.name:r.teamName}</p>
                      </li>
                    )
                  })
                }
                </PullToRefresh>
              </ul>
              <div className={['collect-nodata '+nodata]}>
                  <img src={require(`../../assets/pic-nodata-collect.png`)} />
                  <p>哎呀，这里什么都没有~</p>
              </div>
        </PageContent>
      </PageContainer>
    )
  }
}

export default MyCollect