(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d97c21f"],{"74e0":function(t,e,n){"use strict";n.r(e);var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("nacl-page-container",{attrs:{"no-header":""}},[n("nacl-page-content",{attrs:{id:"type"}},[n("nacl-flexbox",{staticClass:"list",attrs:{justify:"between"}},[n("ul",{staticClass:"left"},t._l(t.CommodityType,(function(e){return n("li",{key:e.category,on:{click:function(n){return t.categorySwitch(e.category)}}},[n("p",{class:{active:e.category==t.category}},[t._v(t._s(e.title))])])})),0),n("nacl-flexbox",{staticClass:"right"},t._l(t.activeData,(function(e){return n("div",{key:e.type,staticClass:"item"},[n("a",{on:{click:function(n){return t.goCommodityList("/"+e.title)}}},[n("img",{attrs:{src:e.imgSrc}}),n("p",{staticClass:"title"},[t._v(t._s(e.title))])])])})),0)],1),n("p",{staticClass:"recommended"},[t._v("- 时尚专区 -")]),n("recommendedList")],1),n("basic-footer")],1)},a=[],o=(n("20d6"),n("bc3a")),s=n.n(o),c=n("a9cc"),r={name:"type",components:{recommendedList:c["a"]},data:function(){return{CommodityType:[],category:null}},computed:{activeData:function(){var t=this;if(!this.category)return[];var e=this.CommodityType.findIndex((function(e){return e.category===t.category}));return this.CommodityType[e].children}},created:function(){this.getType()},methods:{goRouting:function(t){this.toGetUser(),window.webkit.messageHandlers.goRouting.postMessage(t)},toGetUser:function(){for(var t=Math.ceil(JSON.stringify(localStorage).length/1e3),e=0;e<t;e++)e!=t-1?window.webkit.messageHandlers.getUser.postMessage(e+"@_@"+encodeURIComponent(JSON.stringify(localStorage).substring(1e3*e,1e3*(e+1)))):window.webkit.messageHandlers.getUser.postMessage(e+"@_@"+encodeURIComponent(JSON.stringify(localStorage).substring(1e3*e,JSON.stringify(localStorage).length)))},goCommodityList:function(t){this.toGetUser(),window.webkit.messageHandlers.commodityList.postMessage(t)},categorySwitch:function(t){this.category=t},getType:function(){var t=this;s.a.get("http://47.106.209.243:5000/api/commodity/getType").then((function(e){t.CommodityType=e.data.data,t.categorySwitch(t.CommodityType[0].category)}))}},mounted:function(){window.goRouting=this.goRouting}},d=r,l=(n("97d4"),n("2877")),u=Object(l["a"])(d,i,a,!1,null,"48509ec5",null);e["default"]=u.exports},"97d4":function(t,e,n){"use strict";var i=n("f283"),a=n.n(i);a.a},a9cc:function(t,e,n){"use strict";var i=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"recommended-list"}},[n("commodity-list",{attrs:{"list-data":t.listData}})],1)},a=[],o=(n("ac6a"),n("96cf"),n("3b8d")),s=n("2f94"),c=n("dde5"),r={name:"recommended-list",components:{commodityList:s["a"]},props:{},data:function(){return{keysArray:["T恤","帆布鞋","包包","公仔","针织衫","皮衣"],listData:[]}},created:function(){this.getList()},methods:{getList:function(){var t=Object(o["a"])(regeneratorRuntime.mark((function t(){var e,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e={pageIndex:this.pageIndex,pageSize:20,keyword:this.keys},t.next=3,Object(c["r"])(e);case 3:n=t.sent,this.listData=this.listData.concat(n),0===n.length&&(this.noData=!0),this.busy=!1;case 7:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}()}},d=r,l=n("2877"),u=Object(l["a"])(d,i,a,!1,null,"7da48134",null);e["a"]=u.exports},f283:function(t,e,n){}}]);
//# sourceMappingURL=chunk-3d97c21f.11fe075f.js.map