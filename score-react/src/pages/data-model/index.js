import React, { Component } from 'react'
import { connect } from 'dva'
import autobind from 'autobind-decorator'
import PageContent from "../../components/PageContent";
import { Toast,Tabs,PullToRefresh} from 'antd-mobile';
import ReactDOM from 'react-dom';

import BasicHeader from '../../components/BasicHeader'
import BasicFooter from '../../components/BasicFooter'
import PageContainer from "../../components/PageContainer";
import styles from './index.less'
import { relative } from 'path';

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
    }
]

@connect(({ dataModel, loading }) => ({ dataModel, loading }))
class DataModel extends Component {
    constructor(props) {
        super(props)
        this.state = {
            activeCategory: 1,
            contentState:'display_none',
            height: document.documentElement.clientHeight
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {
        this.setState({contentState:'display_none'})
        Toast.loading('加载中...', 30, () => {
    
        });
        const { dispatch } = this.props
        dispatch({ type: 'dataModel/getTopscoreList',payload:categoryList[0].uuid}).then(() =>{
            Toast.hide();
            this.setState({contentState:'display_block'})
            // document.getElementById("root").addEventListener('touchend', this.handleTouch) 
            const offsetheight = document.getElementsByClassName("am-navbar")[0].offsetHeight+document.getElementsByClassName('th-fixed')[0].offsetHeight;
            const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight-offsetheight;
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
        if(document.getElementsByClassName("am-tabs-default-bar-top")[0]&&document.getElementsByClassName("tabsContent")[this.state.activeCategory-1].getBoundingClientRect()){
            const offsetheight = document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight+document.getElementsByClassName("am-navbar")[0].offsetHeight+document.getElementsByClassName("th-fixed")[0].offsetHeight
            if(document.getElementsByClassName("tabsContent")[this.state.activeCategory-1].getBoundingClientRect().top>offsetheight+10){
                Toast.loading('加载中...', 30, () => {
        
                });
                this.getSecondCategory(categoryList[this.state.activeCategory-1])
            }
        }
              
      }
    getSecondCategory(categoryId) {
        const { dispatch } = this.props
        dispatch({ type: 'dataModel/getTopscoreList',payload:categoryId.uuid}).then(()=>{
            setTimeout(function(){
                Toast.hide();
            },2000) 
        })
        
      } 
    @autobind
    handleFirstCategoryClick( categoryId ) {
        document.getElementById('scorllTop').scrollTop=0;
        this.setState({ activeCategory: categoryId.key })
        this.getSecondCategory(categoryId)
    }
    onRefresh = () => {
        const offsetheight = document.getElementsByClassName("am-navbar")[0].offsetHeight+document.getElementsByClassName('th-fixed')[0].offsetHeight;
        const hei = document.documentElement.clientHeight - ReactDOM.findDOMNode(this.ptr).offsetTop-document.getElementsByClassName("am-tabs-default-bar-top")[0].offsetHeight-offsetheight;
        this.setState({
            height: hei
        }) 
        this.getSecondCategory(categoryList[this.state.activeCategory-1])
    };
    render() {
        const { activeCategory,contentState } = this.state
        const { topScoreList } = this.props.dataModel
        return (
            <PageContainer>
                <BasicHeader>
                    <img className="ttbf-title-logo" src={require('../../assets/ttbf-title-logo.png')} />
                </BasicHeader>
                <PageContent id={['scorllTop']} className={[contentState]}> 
                    <Tabs animated={false} tabs={categoryList} initialPage={0} useOnPan={false} 
                    onChange={(tab) => {this.handleFirstCategoryClick(tab)}}
                    onTabClick={(tab) => {this.handleFirstCategoryClick(tab)}}
                    renderTabBar={props => <Tabs.DefaultTabBar {...props} page={7} />}
                    style={{ backgroundColor:'rgba(0,0,0,0)',position:'relative'}}>
                    
                    <div className={['tabsContent']} style={{ backgroundColor:'rgba(0,0,0,0)',top:'0'}}>
                        {/* table */}
                        <div className={['th-fixed']}>
                            <div>球员</div>    
                            <div>球队</div>   
                            <div>进球</div> 
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
                        <table className={['data-table']}>
                            <tbody>
                            {
                                topScoreList.map((r,key)=>{
                                    return (
                                        <tr key={key}>
                                            <td width='10.93333%' className={['text-center']}>{key+1}</td>
                                            <td width='38.6666%'>{r.playerName}</td>
                                            <td width='37.8666%'>{r.teamName}</td>
                                            <td className={['text-right']}>{r.goals}</td>
                                        </tr>
                                    )
                                })
                            }
                            </tbody>
                        </table>
                        </PullToRefresh>       
                        </div>
                        
                    </Tabs>
                </PageContent>
                <BasicFooter />
            </PageContainer>
        )
    }
}

export default DataModel