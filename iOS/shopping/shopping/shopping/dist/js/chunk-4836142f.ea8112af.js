(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4836142f"],{3697:function(t,e,n){},"69dc":function(t,e,n){"use strict";var a=n("3697"),o=n.n(a);o.a},"6e7a":function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("nacl-page-container",{attrs:{"no-header":""}},[n("nacl-page-content",[n("div",{staticClass:"content"},[n("div",{staticClass:"text-area"},[n("textarea",{directives:[{name:"model",rawName:"v-model",value:t.text,expression:"text"}],attrs:{placeholder:"请输入您的宝贵意见和问题…",rows:"10"},domProps:{value:t.text},on:{input:function(e){e.target.composing||(t.text=e.target.value)}}})]),n("div",{staticClass:"btn-wrap"},[n("nacl-button",{attrs:{type:"primary",radius:""},on:{click:t.submit}},[t._v("提交反馈")])],1)])])],1)},o=[],s=n("d8d8"),i={name:"feedback",components:{NaclButton:s["NaclButton"]},data:function(){return{text:""}},methods:{goRouting:function(t){this.toGetUser(),window.webkit.messageHandlers.goRouting.postMessage(t)},toGetUser:function(){for(var t=Math.ceil(JSON.stringify(localStorage).length/1e3),e=0;e<t;e++)e!=t-1?window.webkit.messageHandlers.getUser.postMessage(e+"@_@"+encodeURIComponent(JSON.stringify(localStorage).substring(1e3*e,1e3*(e+1)))):window.webkit.messageHandlers.getUser.postMessage(e+"@_@"+encodeURIComponent(JSON.stringify(localStorage).substring(1e3*e,JSON.stringify(localStorage).length)))},submit:function(){alert("我们已经收到您的意见以及反馈,感谢以往您对我们的支持,谢谢!"),this.toGetUser(),window.webkit.messageHandlers.goBack.postMessage("")}},mounted:function(){window.goRouting=this.goRouting}},r=i,c=(n("69dc"),n("2877")),l=Object(c["a"])(r,a,o,!1,null,"602a0075",null);e["default"]=l.exports}}]);
//# sourceMappingURL=chunk-4836142f.ea8112af.js.map