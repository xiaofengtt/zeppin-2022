(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["search"],{1075:function(t,e,i){"use strict";i("68ef"),i("4fbc")},"165a":function(t,e,i){"use strict";var s=i("2b38"),n=i.n(s);n.a},"1d86":function(t,e){t.exports="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACIAAAAgCAMAAABAUVr7AAAA1VBMVEUAAAD///9/f3////+qqqr///+/v7/MzMzU1NTb29vU1NTS0tLR0dHW1tbX19fS0tLW1tbX19fT09PU1NTS0tLS0tLU1NTU1NTX19fW1tbV1dXS0tLU1NTT09PU1NTU1NTS0tLU1NTT09PU1NTV1dXU1NTU1NTU1NTS0tLT09PT09PT09PU1NTS0tLU1NTU1NTT09PT09PS0tLT09PT09PT09PT09PU1NTT09PU1NTT09PU1NTT09PT09PT09PT09PT09PT09PT09PS0tLT09PT09PT09O09ZP0AAAARnRSTlMAAQICAwMEBQYHDBEWGSYoLC0uLzM5Ozw/RE9VWW5wcnh8f4KFjY6TlJucnZ+goKWtr7e6v8DBxMfI4+Xm6Ons7vn7/Pz9+t5WzwAAAT5JREFUOMuVk21TglAQhdfIpLKibmEvKBFpYVBUGpVlhPT8/5/UB1JxeHE6n+7snNm7e85ZkSU0ZdmObSlNKtAJYjLEQaeMoMbkMVYFRjcBmIZ9tx9OAZLuKqHlAalvZkNopp8CXitP8YDIyBWMCPDyvwBDfaWvPgSWf6kEhoXphpAsZh5DpBcoegTjuR6QGiUyGCn86ROAX6qlD0G2YgxmKcWEWBMRUTAtN0WbghIRsSCscC0ES0TEhn4FpQ+2iIgDbgXFBWdNlwH01sxyBef1G8nOtd2o12WJSnVzma3wSA6Wz3Knm2+8tuvzcgtMTmtS17wBYOZsVGV3/wV4+gSe22UXIMfeN/CwtTcCJoeFOxo8vgMklw2RTfcH7iqucXSUlc8+Zhc5fe7nN/0VnCyq27urMVOW7fQsoyn/xS+SsEjVwmnitAAAAABJRU5ErkJggg=="},2994:function(t,e,i){"use strict";i("68ef"),i("e3b3"),i("c0c2")},"2b38":function(t,e,i){},"2bdd":function(t,e,i){"use strict";var s=i("d282"),n=i("02de"),a=i("a8c1"),o=i("5fbe"),r=i("543e"),c=Object(s["a"])("list"),l=c[0],h=c[1],u=c[2];e["a"]=l({mixins:[Object(o["a"])((function(t){this.scroller||(this.scroller=Object(a["d"])(this.$el)),t(this.scroller,"scroll",this.check)}))],model:{prop:"loading"},props:{error:Boolean,loading:Boolean,finished:Boolean,errorText:String,loadingText:String,finishedText:String,immediateCheck:{type:Boolean,default:!0},offset:{type:[Number,String],default:300},direction:{type:String,default:"down"}},data:function(){return{innerLoading:this.loading}},updated:function(){this.innerLoading=this.loading},mounted:function(){this.immediateCheck&&this.check()},watch:{loading:"check",finished:"check"},methods:{check:function(){var t=this;this.$nextTick((function(){if(!(t.innerLoading||t.finished||t.error)){var e,i=t.$el,s=t.scroller,a=t.offset,o=t.direction;e=s.getBoundingClientRect?s.getBoundingClientRect():{top:0,bottom:s.innerHeight};var r=e.bottom-e.top;if(!r||Object(n["a"])(i))return!1;var c=!1,l=t.$refs.placeholder.getBoundingClientRect();c="up"===o?e.top-l.top<=a:l.bottom-e.bottom<=a,c&&(t.innerLoading=!0,t.$emit("input",!0),t.$emit("load"))}}))},clickErrorText:function(){this.$emit("update:error",!1),this.check()},genLoading:function(){var t=this.$createElement;if(this.innerLoading&&!this.finished)return t("div",{key:"loading",class:h("loading")},[this.slots("loading")||t(r["a"],{attrs:{size:"16"}},[this.loadingText||u("loading")])])},genFinishedText:function(){var t=this.$createElement;if(this.finished){var e=this.slots("finished")||this.finishedText;if(e)return t("div",{class:h("finished-text")},[e])}},genErrorText:function(){var t=this.$createElement;if(this.error){var e=this.slots("error")||this.errorText;if(e)return t("div",{on:{click:this.clickErrorText},class:h("error-text")},[e])}}},render:function(){var t=arguments[0],e=t("div",{ref:"placeholder",key:"placeholder",class:h("placeholder")});return t("div",{class:h(),attrs:{role:"feed","aria-busy":this.innerLoading}},["down"===this.direction?this.slots():e,this.genLoading(),this.genFinishedText(),this.genErrorText(),"up"===this.direction?this.slots():e])}})},"2d3b":function(t,e,i){"use strict";i.r(e);var s=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{attrs:{id:"Search"}},[s("van-row",{staticClass:"header",attrs:{align:"center",justify:"space-between",type:"flex"}},[s("van-col",{on:{click:t.onClickLeft}},[s("i",{staticClass:"van-icon van-icon-arrow-left van-nav-bar__arrow"})]),s("form",{staticClass:"search",attrs:{action:"/"}},[s("Search",{attrs:{"left-icon":i("1d86"),"show-action":!1,background:"none",shape:"round"},on:{search:t.onRefresh},model:{value:t.keys,callback:function(e){t.keys=e},expression:"keys"}})],1),s("p",{staticClass:"search-button",on:{click:t.search}},[t._v("search")])],1),s("div",{attrs:{id:"content"}},[0===t.listData.length&&0===t.pageNum?s("div",{staticClass:"hot"}):s("pull-refresh",{ref:"a",attrs:{"loading-text":"loading","loosing-text":"Release to refresh","pulling-text":"Drop down to refresh"},on:{refresh:t.onRefresh},model:{value:t.downLoading,callback:function(e){t.downLoading=e},expression:"downLoading"}},[0===t.listData.length&&t.finished?s("div",{staticClass:"nodata"},[s("img",{attrs:{src:i("e5aa")}}),s("p",[t._v("No goods yet")])]):s("list",{staticClass:"list",attrs:{finished:t.finished,"immediate-check":!1,"finished-text":"No More Items","loading-text":"loading"},on:{load:t.onLoad},model:{value:t.upLoading,callback:function(e){t.upLoading=e},expression:"upLoading"}},t._l(t.listData,(function(e){return s("div",{key:e.uuid,attrs:{to:"/lottery-goods-details/"+e.uuid}},[s("van-row",{staticClass:"item",attrs:{align:"center",justify:"space-between",type:"flex"}},[s("van-col",[s("img",{staticClass:"img",attrs:{src:""+e.coverImg,alt:""}})]),s("van-col",{staticClass:"right"},[s("p",{staticClass:"title van-multi-ellipsis--l2"},[s("span",[t._v("Issue:"+t._s(e.issueNum)+" | ")]),t._v(" "+t._s(e.title)+" ")]),s("van-row",{attrs:{align:"bottom",justify:"space-between",type:"flex"}},[s("van-col",{staticClass:"left"},[s("van-row",{attrs:{align:"center",justify:"space-between",type:"flex"}},[s("van-col",{staticClass:"schedule"},[t._v(" Progress: "),s("span",[t._v(" "+t._s(t.getDrawProgress(e.betShares,e.shares))+"% ")])]),s("van-col",{staticClass:"schedule"},[s("span",[t._v(t._s(e.betShares)+"/")]),s("b",[t._v(t._s(e.shares))])])],1),s("div",{staticClass:"progress"},[s("Progress",{attrs:{percentage:t.getDrawProgress(e.betShares,e.shares),"show-pivot":!1,color:"#FFBC02","track-color":"#E8EBEE"}})],1)],1),s("van-col",[s("div",{on:{click:function(i){return t.androidUrl("/lottery-goods-details/"+e.uuid+"?isShowBuy=true")}}},[s("div",{staticClass:"button"},[t._v("Join")])])])],1)],1)],1)],1)})),0)],1)],1),t.isScore?s("div",{staticClass:"goscore-box"},[s("div",{staticClass:"box"},[s("img",{attrs:{src:i("d66c")}}),s("div",{staticClass:"goscore-box-inner"},[s("p",[t._v("To claim your prize,please check your order to learn more.If you have any questions,just contact us.")]),s("van-col",{staticClass:"right",on:{click:t.goScore}},[t._v("Claim Your Prize")]),s("van-col",{staticClass:"left",on:{click:function(e){t.isScore=!1}}},[t._v("Later")])],1)])]):t._e()],1)},n=[],a=(i("99af"),i("d3b7"),i("ddb0"),i("96cf"),i("c964")),o=i("f3f3"),r=(i("2994"),i("2bdd")),c=(i("ab71"),i("58e6")),l=(i("1075"),i("f600")),h=(i("68ef"),i("9d70"),i("3743"),i("1a04"),i("1146"),i("f032"),i("2638")),u=i.n(h),d=i("c31d"),g=i("d282"),f=i("ba31"),p=i("1325"),v=i("565f"),b=Object(g["a"])("search"),m=b[0],S=b[1],T=b[2];function w(t,e,i,s){function n(){if(i.label||e.label)return t("div",{class:S("label")},[i.label?i.label():e.label])}function a(){if(e.showAction)return t("div",{class:S("action"),attrs:{role:"button",tabindex:"0"},on:{click:n}},[i.action?i.action():e.actionText||T("cancel")]);function n(){i.action||(Object(f["a"])(s,"input",""),Object(f["a"])(s,"cancel"))}}var o={attrs:s.data.attrs,on:Object(d["a"])({},s.listeners,{keypress:function(t){13===t.keyCode&&(Object(p["c"])(t),Object(f["a"])(s,"search",e.value)),Object(f["a"])(s,"keypress",t)}})},r=Object(f["b"])(s);return r.attrs=void 0,t("div",u()([{class:S({"show-action":e.showAction}),style:{background:e.background}},r]),[null==i.left?void 0:i.left(),t("div",{class:S("content",e.shape)},[n(),t(v["a"],u()([{attrs:{type:"search",border:!1,value:e.value,leftIcon:e.leftIcon,rightIcon:e.rightIcon,clearable:e.clearable,clearTrigger:e.clearTrigger},scopedSlots:{"left-icon":i["left-icon"],"right-icon":i["right-icon"]}},o]))]),a()])}w.props={value:String,label:String,rightIcon:String,actionText:String,background:String,showAction:Boolean,clearTrigger:String,shape:{type:String,default:"square"},clearable:{type:Boolean,default:!0},leftIcon:{type:String,default:"search"}};var k=m(w),x=i("ed08"),y=i("2f62"),A={name:"categories",components:{Search:k,Progress:l["a"],PullRefresh:c["a"],List:r["a"]},data:function(){return{listData:[],keys:"",downLoading:!1,upLoading:!1,finished:!1,pageNum:0,pageSize:20,isFlag:!1,isScore:!1}},watch:{keys:function(){0===this.listData.length&&""===this.keys&&(this.pageNum=0)}},computed:Object(o["a"])({},Object(y["e"])(["channel_uuid","version_id"])),mounted:function(){window.scoreShow=this.scoreShow},methods:{onClickLeft:function(){window.JavascriptInterface.goRoute("/categories")},getDrawProgress:function(t,e){return Object(x["e"])(t,e)},getList:function(){var t=this;return Object(a["a"])(regeneratorRuntime.mark((function e(){var i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$api.get("/goods/list",{status:"betting",pageNum:t.pageNum,pageSize:t.pageSize,name:t.keys,channel:window.localStorage.getItem("channelUuid"),version:window.localStorage.getItem("version")});case 2:return i=e.sent,t.isFlag=!0,e.abrupt("return",i);case 5:case"end":return e.stop()}}),e)})))()},onRefresh:function(){var t=this;return Object(a["a"])(regeneratorRuntime.mark((function e(){var i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.pageNum=1,e.next=3,t.getList();case 3:i=e.sent,setTimeout((function(){t.listData=[],t.listData=t.listData.concat(i),i.length<t.pageSize&&(t.finished=!0),t.downLoading=!1}),600);case 5:case"end":return e.stop()}}),e)})))()},onLoad:function(){var t=this;return Object(a["a"])(regeneratorRuntime.mark((function e(){var i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.pageNum++,e.next=3,t.getList();case 3:i=e.sent,setTimeout((function(){t.listData=t.listData.concat(i),i.length<t.pageSize&&(t.finished=!0),t.upLoading=!1}),600);case 5:case"end":return e.stop()}}),e)})))()},search:function(){""===this.keys?this.$toast("Please enter the search content"):Object(x["h"])(this.keys)?this.$toast("Do not enter special characters"):this.onLoad()},androidUrl:function(t){window.localStorage.setItem("androidPage","/search"),window.JavascriptInterface.goRoute("other"),this.$router.push(t)},scoreShow:function(){this.isScore=!0},goScore:function(){window.JavascriptInterface.goRoute("/recordlist/mywonlist?id=1&type=score")}}},C=A,P=(i("165a"),i("2877")),L=Object(P["a"])(C,s,n,!1,null,"ffb2ac70",null);e["default"]=L.exports},"4fbc":function(t,e,i){},"58e6":function(t,e,i){"use strict";var s=i("d282"),n=i("1325"),a=i("a8c1"),o=i("3875"),r=i("543e"),c=Object(s["a"])("pull-refresh"),l=c[0],h=c[1],u=c[2],d=50,g=["pulling","loosing","success"];e["a"]=l({mixins:[o["a"]],props:{disabled:Boolean,successText:String,pullingText:String,loosingText:String,loadingText:String,value:{type:Boolean,required:!0},successDuration:{type:[Number,String],default:500},animationDuration:{type:[Number,String],default:300},headHeight:{type:[Number,String],default:d}},data:function(){return{status:"normal",distance:0,duration:0}},computed:{touchable:function(){return"loading"!==this.status&&"success"!==this.status&&!this.disabled},headStyle:function(){if(this.headHeight!==d)return{height:this.headHeight+"px"}}},watch:{value:function(t){this.duration=this.animationDuration,t?this.setStatus(+this.headHeight,!0):this.slots("success")||this.successText?this.showSuccessTip():this.setStatus(0,!1)}},mounted:function(){this.bindTouchEvent(this.$refs.track),this.scrollEl=Object(a["d"])(this.$el)},methods:{checkPullStart:function(t){this.ceiling=0===Object(a["c"])(this.scrollEl),this.ceiling&&(this.duration=0,this.touchStart(t))},onTouchStart:function(t){this.touchable&&this.checkPullStart(t)},onTouchMove:function(t){this.touchable&&(this.ceiling||this.checkPullStart(t),this.touchMove(t),this.ceiling&&this.deltaY>=0&&"vertical"===this.direction&&(Object(n["c"])(t),this.setStatus(this.ease(this.deltaY))))},onTouchEnd:function(){var t=this;this.touchable&&this.ceiling&&this.deltaY&&(this.duration=this.animationDuration,"loosing"===this.status?(this.setStatus(+this.headHeight,!0),this.$emit("input",!0),this.$nextTick((function(){t.$emit("refresh")}))):this.setStatus(0))},ease:function(t){var e=+this.headHeight;return t>e&&(t=t<2*e?e+(t-e)/2:1.5*e+(t-2*e)/4),Math.round(t)},setStatus:function(t,e){var i;i=e?"loading":0===t?"normal":t<this.headHeight?"pulling":"loosing",this.distance=t,i!==this.status&&(this.status=i)},genStatus:function(){var t=this.$createElement,e=this.status,i=this.distance,s=this.slots(e,{distance:i});if(s)return s;var n=[],a=this[e+"Text"]||u(e);return-1!==g.indexOf(e)&&n.push(t("div",{class:h("text")},[a])),"loading"===e&&n.push(t(r["a"],{attrs:{size:"16"}},[a])),n},showSuccessTip:function(){var t=this;this.status="success",setTimeout((function(){t.setStatus(0)}),this.successDuration)}},render:function(){var t=arguments[0],e={transitionDuration:this.duration+"ms",transform:this.distance?"translate3d(0,"+this.distance+"px, 0)":""};return t("div",{class:h()},[t("div",{ref:"track",class:h("track"),style:e},[t("div",{class:h("head"),style:this.headStyle},[this.genStatus()]),this.slots()])])}})},"6ab3":function(t,e,i){},ab71:function(t,e,i){"use strict";i("68ef"),i("e3b3"),i("6ab3")},c0c2:function(t,e,i){},d66c:function(t,e,i){t.exports=i.p+"img/pic-score.5c4bb762.png"},e5aa:function(t,e,i){t.exports=i.p+"img/icon-nodata-cash.e08a0394.png"},f032:function(t,e,i){},f600:function(t,e,i){"use strict";var s=i("d282"),n=i("ea8e"),a=Object(s["a"])("progress"),o=a[0],r=a[1];e["a"]=o({props:{color:String,inactive:Boolean,pivotText:String,textColor:String,pivotColor:String,trackColor:String,strokeWidth:[Number,String],percentage:{type:[Number,String],required:!0,validator:function(t){return t>=0&&t<=100}},showPivot:{type:Boolean,default:!0}},data:function(){return{pivotWidth:0,progressWidth:0}},mounted:function(){this.setWidth()},watch:{showPivot:"setWidth",pivotText:"setWidth"},methods:{setWidth:function(){var t=this;this.$nextTick((function(){t.progressWidth=t.$el.offsetWidth,t.pivotWidth=t.$refs.pivot?t.$refs.pivot.offsetWidth:0}))}},render:function(){var t=arguments[0],e=this.pivotText,i=this.percentage,s=null!=e?e:i+"%",a=this.showPivot&&s,o=this.inactive?"#cacaca":this.color,c={color:this.textColor,left:(this.progressWidth-this.pivotWidth)*i/100+"px",background:this.pivotColor||o},l={background:o,width:this.progressWidth*i/100+"px"},h={background:this.trackColor,height:Object(n["a"])(this.strokeWidth)};return t("div",{class:r(),style:h},[t("span",{class:r("portion"),style:l},[a&&t("span",{ref:"pivot",style:c,class:r("pivot")},[s])])])}})}}]);