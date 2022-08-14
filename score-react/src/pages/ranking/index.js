import React, { Component } from 'react'
import { connect } from 'dva'
import autobind from 'autobind-decorator'
import PageContent from "../../components/PageContent";
import { Toast,Tabs,PullToRefresh } from 'antd-mobile';
import ReactDOM from 'react-dom';

import BasicHeader from '../../components/BasicHeader'
import BasicFooter from '../../components/BasicFooter'
import PageContainer from "../../components/PageContainer";
import styles from './index.less'
import {ASSETS_URL} from "../../config";


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
const rankingRole = [
    {
        role: '<h3>中超决定名次规则：</h3>'
        +'<p>（一）每队胜一场得３分，平一场得１分，负一场得０分。</p> '
        +'<p>（二）中超联赛当年度比赛全部完成后，积分多者名次列前。如果两队或两队以上积分相等，依下列顺序排列名次：<br/>'
        +'1．积分相等队之间相互比赛积分多者，名次列前；<br/>'
        +'2．积分相等队之间相互比赛净胜球多者，名次列前；<br/>'
        +'3．积分相等队之间相互比赛进球数多者，名次列前；<br/>'
        +'4．积分相等队之间，按权重比例计算预备队与各青少年梯队积分，排名靠前者,联赛名次列前；</p> '
        +'<p>（三）中超联赛进行中，如果两队或两队以上积分相等，积分相等队之间净胜球多者，名次列前。</p> '
        +'<p>（四）亚冠和降级：<br/>'
        +'1.联赛冠军（第一顺位）、足协杯冠军（第二顺位）和联赛亚军（第三顺位）直接晋级下赛季亚冠正赛，联赛第三名（第四顺位）获得亚冠附加赛参赛资格。<br/>'
        +' 2.若足协杯冠军已通过联赛确保亚冠参赛资格，那么该名额让给联赛名次靠前的球队。<br/>'
        +'3.当年中超联赛名次列最后两名的俱乐部队降级，参加次年中甲联赛。</p> '
    },{
        role : '<h3>英超决定名次规则:</h3>'
        +'<p>（一）每队胜一场得３分，平一场得１分，负一场得０分。</p>'
        +'<p>（二）英超联赛当年度比赛全部完成后，积分多者名次列前。如果两队或两队以上积分相等，依下列顺序排列名次：<br/>'
        +'1．联赛净胜球多者，名次列前； <br/>'
        +'2．联赛进球数多者，名次列前； <br/>'
        +'3．若联赛结束后，净胜球和进球数相同。那么相关球队需要通过安排附加赛来决出最终名次，附加赛具体的时间、场次将由双方或者'
        +'多方协商确定，比赛在中立场地进行。</p> '
        +'<p>（三）欧战和降级 <br/>'
        +'1.联赛前四名获得次年的欧冠正赛参赛资格。 <br/>'
        +'2.联赛第五名获得欧联杯正赛参赛资格。<br/>'
        +'3.英格兰足总杯冠军可获得次年欧联杯参赛资格。若足总杯冠军已经获得欧战资格，那么该名额让给未获得欧战资格的联赛排名靠前的球队。<br/> '
        +'4.英格兰联赛杯冠军可获得次年欧联杯参赛资格。若联赛杯冠军已经获得欧战资格，那么该名额让给未获得欧战资格的联赛排名靠前的球队。 <br/>'
        +'5.当年英超联赛名次列最后三名的俱乐部队降级，参加次年英冠联赛。'
        +'</p>'
    },{
        role : '<h3>西甲决定名次规则：</h3>'
        +'<p>（一）每队胜一场得３分，平一场得１分，负一场得０分。</p>'
        +'<p>（二）西甲联赛当年度比赛全部完成后，积分多者名次列前。如果两队或两队以上积分相等，依下列顺序排列名次：<br/>'
        +'1．相互比赛积分多者，名次列前；<br/>'
        +'2.相互比赛净胜球多者，名次列前（不比较客场进球）；<br/>'
        +'3.联赛净胜球多者，名次列前；<br/>'
        +'4.联赛进球数多者，名次列前；<br/>'
        +'5.公平竞赛积分高者，名次列前；<br/>'
        +' 6.若联赛结束后，如果上述方法仍无法决定球队排名且涉及到联赛冠军、降级名额及欧战名额，那么相关球队需要通过安排两回合主客场附加赛来决出最终名次。</p>'
        +'<p>（三）西甲联赛进行中，同分球队相互比赛未比完前，优先比较联赛净胜球与联赛进球数排列名次，多者名次列前。</p>'
        +'<p>（四）欧战和降级<br/>'
        +'1.联赛前四名获得下赛季的欧冠正赛参赛资格。<br/>'
        +'2.联赛第五名获得下赛季的欧联杯正赛参赛资格，第六名获得欧联杯第二轮资格赛参赛资格。<br/>'
        +'3.国王杯冠军获得下赛季的欧联杯正赛参赛资格。若国王杯冠军已经获得欧战资格，那么该名额让给未获得欧战资格的联赛排名靠前的球队。<br/>'
        +'4.当年西甲联赛名次列最后三名的俱乐部队降级，参加次年的西乙联赛。</p>'
    },{
        role : '<h3>意甲决定名次规则：</h3>'
        +'<p>注：2018/2019赛季切沃俱乐部由于账目作假，被意大利足协扣除3个联赛积分。</p> '
        +'<p>（一）每队胜一场得３分，平一场得１分，负一场得０分。</p> '
        +'<p>（二）意甲联赛当年度比赛全部完成后，积分多者名次列前。如果两队或两队以上积分相等，依下列顺序排列名次：<br/>'
        +'1．相互比赛积分多者，名次列前；<br/>'
        +' 2.相互比赛净胜球多者，名次列前（不比较客场进球）；<br/>'
        +'3.联赛净胜球多者，名次列前；<br/>'
        +'4.联赛进球数多者，名次列前；<br/>'
        +' 5.若仍然不分高下，相关球队需通过抽签来决出最终名次。</p> '
        +'<p>（三）欧战和降级<br/>'
        +'1.联赛前四名获得下赛季的欧冠正赛参赛资格。<br/>'
        +' 2.联赛第五名获得下赛季欧联杯正赛参赛资格，联赛第六名获得下赛季欧联杯资格赛第二轮参赛资格。<br/>'
        +'3.意大利杯冠军获得欧联杯正赛资格，若意大利杯冠军已获得欧战资格，那么该名额让给未获得欧战资格的联赛排名靠前的球队。<br/>'
        +'4.当年意甲联赛名次列最后三名的俱乐部队降级，参加次年的意乙联赛。</p>'
    },{
        role : '<h3>德甲决定名次规则：</h3>'
        +'<p>（一）每队胜一场得３分，平一场得１分，负一场得０分。</p> '
        +'<p>（二）德甲联赛当年度比赛全部完成后，积分多者名次列前。</p> '
        +'<p>（三）如果两队或两队以上积分相等，依下列顺序排列名次：</p> '
        +' 1.联赛净胜球多者，名次列前；<br/>'
        +'2.联赛进球数多者，名次列前；<br/>'
        +'3.相互比赛积分多者，名次列前；<br/>'
        +'4.相互比赛净胜球多者，名次列前；<br/>'
        +'5.相互比赛客场进球数多者，名次列前；<br/>'
        +'6.联赛客场进球数多者，名次列前；<br/>'
        +'7.若仍然不分高下，相关球队需要通过安排单场附加赛来决出最终名次，附加赛中立球场进行。</p> '
        +'<p>（四）欧战和降级<br/>'
        +'1.联赛前四名获得下赛季的欧冠正赛参赛资格。<br/>'
        +' 2.联赛第五名获得下赛季的欧联杯正赛参赛资格，联赛第六名获得下赛季欧联杯资格赛第二轮参赛资格。<br/>'
        +'3.德国杯冠军获得下赛季欧的联杯正赛参赛资格，若德国杯冠军已获得欧战资格，那么该名额让给未获得欧战资格的联赛排名靠前的球队。<br/>'
        +'4.当年德甲联赛名次列最后两名的俱乐部队降级，参加次年的德乙联赛。倒数第三名和德乙第三名进行两回合的升降级附加赛。</p> '
    },{
        role :'<h3>欧冠淘汰赛抽签和晋级规则：</h3>'
        +'<p>淘汰赛（含决赛）阶段除冠军争夺战为中立场地一场定胜负外，其余均采用主客场两回合制的淘汰赛。另外欧冠联赛只设有冠军和亚军这两个奖项，没有季军争夺战。</p>'
        +'<p>A. 1/8决赛对阵由抽签决定，但根据以下规则：<br/>'
        +'（1）小组第一对阵小组第二； <br/>'
        +'（2）同小组同协会球队不会相遇； <br/>'
        +'（3）首回合先在小组第二t的球队主场进行。 </p>'
        +'<p>B. 1/4决赛对阵由抽签决定，抽签没有任何限制，主客场由抽签决定。 </p>'
        +'<p>C. 半决赛抽签与1/4决赛抽签同日进行，抽签时将明确划分上下半区，根据球队落位决定半决赛的潜在对阵。此外抽签仪式还将确定晋级决赛球队的主队与客队身份。</p>'
        +'<p>D. 1/8决赛、1/4决赛和半决赛晋级规则：（加时赛进球算客场进球）<br/>'
        +'（1）两回合总比分占优者晋级<br/>'
        +'（2）若总比分打平，客场进球数多者晋级<br/>'
        +'（3）若总比分打平，客场进球数也一样，则进入30分钟加时赛，加时赛比分占优者晋级<br/>'
        +'（4）若加时赛打成1-1以上平局，第二回合客队凭借客场进球优势晋级<br/>'
        +'（5）若加时赛打成0-0，进入点球大战，点球大战胜者晋级。</p>'
        +'<p>E. 决赛中双方若在90分钟内打平，会进入30分钟加时赛，加时赛仍打平则通过点球大战定胜负。决赛胜利者为该赛季欧洲冠军联赛冠军，并可以参加同年的世俱杯、欧洲超级杯以及下赛季的欧洲冠军联赛小组赛。</p> '
    },{
        role : '<h3>亚冠小组赛出线规则：</h3>'
        +'<p>(一)每队胜一场得3分，平一场得1分，负一场得0分;</p> '
        +'<p>(二)积分多的球队名次列前;</p> '
        +'<p>(三)如果两队或两队以上积分相等，依下列顺序排列名次：<br/>'
        +'1.积分相等队之间相互比赛积分多者，名次列前;<br/>'
        +'2.积分相等队之间相互比赛净胜球多者，名次列前;<br/>'
        +'3.积分相等队之间相互比赛进球数多者，名次列前;<br/>'
        +'4.积分相等队之间相互比赛的客场进球数多者，名次列前<br/>'
        +'5.若在比较前4项后，仍有2队或2队以上排名相同，那么再将球队单独拿出来，按前4项的顺序进行比较<br/>'
        +'6.积分相等队在该组全部比赛中净胜球数多者，名次列前;<br/>'
        +'7.积分相等队在该组全部比赛中进球数多者，名次列前;<br/>'
        +'8.若两队依旧无法分出高下，且最后一轮有直接交锋，那么直接进行点球大战<br/>'
        +'9.小组赛期间红黄牌统计少者，名次列前;<br/>'
        +'10该球队所在联赛的上一年度亚足联技术分排名（MA积分排名），排名高者名次列前</p> '
        +'<p>(四)每个小组第一名和第二名晋级淘汰赛阶段</p> '
    }
]

@connect(({ ranking, loading }) => ({ ranking, loading }))
class Ranking extends Component {
    constructor(props) {
        super(props)
        this.state = {
            activeCategory: 1,
            contentState:'display_none',
            height: document.documentElement.clientHeight,
        }
        // this.handleTouch = this.handleTouch.bind(this);
    }
    componentDidMount() {
        const { dispatch } = this.props
        dispatch({ type: 'ranking/getStandingList',payload:categoryList[0].uuid}).then(()=>{
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
        dispatch({ type: 'ranking/getStandingList',payload:categoryId.uuid}).then(()=>{
            setTimeout(function(){
                Toast.hide();
            },500) 
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
        const { standingList } = this.props.ranking
        let classNameColor = ''
        const dataObj = {}
        standingList.map((r,value) => {
            if(dataObj.hasOwnProperty(r.round)){
                dataObj[r.round].push(r);
            }else{
                dataObj[r.round]=[];
                dataObj[r.round].push(r);
            }
            return dataObj;
        })
        
        
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
                        <div className={['div-rank']}>排名</div>    
                        <div className={['div-team']}>球队</div>   
                        <div className={['div-games']}>场</div> 
                        <div className={['div-victory']}>胜/平/负</div>
                        <div className={['div-goal']}>进/失</div>
                        <div className={['div-score']}>积分</div>
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
                    <table className={['data-table ranking-table']}>
                        {
                            Object.keys(dataObj).map((key,i) => {
                                var item = dataObj[key].map((r,value)=>{
                                    //西甲
                                    if(activeCategory==3){//西甲
                                        if(value<4){
                                            classNameColor = 'color-red';
                                        }else if(value>=4&&value<6){
                                            classNameColor = 'color-yellow';
                                        }else if(value>dataObj[key].length-4){
                                            classNameColor = 'color-green';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }else if(activeCategory==2){//英超
                                        if(value<4){
                                            classNameColor = 'color-red';
                                        }else if(value>=4&&value<5){
                                            classNameColor = 'color-yellow';
                                        }else if(value>dataObj[key].length-4){
                                            classNameColor = 'color-green';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }else if(activeCategory==4){//意甲
                                        if(value<4){
                                            classNameColor = 'color-red';
                                        }else if(value>=4&&value<6){
                                            classNameColor = 'color-yellow';
                                        }else if(value>dataObj[key].length-4){
                                            classNameColor = 'color-green';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }else if(activeCategory==1){//中超
                                        if(value<3){
                                            classNameColor = 'color-red';
                                        }else if(value>dataObj[key].length-3){
                                            classNameColor = 'color-green';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }else if(activeCategory==5){//德甲
                                        if(value<4){
                                            classNameColor = 'color-red';
                                        }else if(value>=4&&value<6){
                                            classNameColor = 'color-yellow';
                                        }else if(value>dataObj[key].length-4){
                                            classNameColor = 'color-green';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }else if(activeCategory==7){//德甲
                                        if(value<2){
                                            classNameColor = 'color-red';
                                        }else{
                                            classNameColor = '';
                                        }  
                                    }
                                    return (
                                        <tr key={value} className={[classNameColor]}>
                                            <td width='12.8%'>{r.place}</td>
                                            <td className={['text-left']} width='35%'>
                                                <img src={`${ASSETS_URL}${r.teamIconUrl}`} />
                                                <span>{r.teamName}</span>
                                            </td>
                                            <td width='8%'>{r.played}</td>
                                            <td width='18%'>{r.number}{r.won}/{r.drawn}/{r.lost}</td>
                                            <td width='14.93333%'>{r.scored}/{r.against}</td>
                                            <td width=''>{r.point}</td>
                                        </tr>                                       
                                    )
                                })
                                return(
                                    <tbody key={i}>{key?<tr><td colSpan="6">{key}</td></tr>:<tr></tr>}{item}</tbody>
                                )
                            })
                            
                        }
                    </table>
                    {/* 规则 */}
                    <div className={['ranking-role']} dangerouslySetInnerHTML={{__html: rankingRole[activeCategory-1].role}}>
                    
                    </div>

                    </PullToRefresh>
                    </div>
                    
                    </Tabs>
                </PageContent>
                <BasicFooter />
            </PageContainer>
        )
    }
}

export default Ranking