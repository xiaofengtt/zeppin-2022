(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["activity~home"],{"0f36":function(t,e,i){"use strict";var n=i("23f8"),s=i.n(n);s.a},"23f8":function(t,e,i){},"34dd":function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{attrs:{id:"person-times"}},[i("div",{staticClass:"goods-img"},[i("img",{attrs:{src:""+t.goodsDerails.coverImg}})]),i("div",{staticClass:"close"},[i("icon",{attrs:{name:"cross"},on:{click:t.close}})],1),i("div",{staticClass:"title-unitPrice"},[i("p",{staticClass:"title"},[t._v("Participant")]),i("p",{staticClass:"unit-price font-orange"},[t._v("("+t._s(t.goodsDerails.betPerShare)+"coin/participant)")])]),i("van-row",{staticClass:"select",attrs:{align:"center",justify:"space-between",type:"flex"}},t._l(t.options,(function(e){return i("van-col",{key:e.value,class:["item",{active:e.value===t.value}],on:{click:function(i){return t.setValue(e.value)}}},[t._v(t._s(e.text))])})),1),i("van-row",{staticClass:"person-times",attrs:{align:"center",justify:"center",type:"flex"}},[i("van-col",[t._v("Participant:")]),i("van-col",[i("stepper",{attrs:{max:t.paymentGroup?"lucky"===t.paymentGroup?t.goodsDerails.groupShares.lucky:t.goodsDerails.groupShares.raider:t.goodsDerails.remainShares,value:t.value,integer:""},on:{change:t.setValue}})],1)],1)],1)},s=[],a=(i("a9e3"),i("c3a6"),i("ad06")),r=(i("68ef"),i("fb6c"),i("2638")),o=i.n(r),u=i("d282"),l=i("ea8e"),c=i("a142"),h=i("a8fa"),d=i("1325"),p=i("482d"),m=i("90c6"),f=i("78eb"),g=Object(u["a"])("stepper"),b=g[0],v=g[1],y=600,S=200;function w(t,e){return String(t)===String(e)}function x(t,e){var i=Math.pow(10,10);return Math.round((t+e)*i)/i}var V=b({mixins:[f["a"]],props:{value:null,theme:String,integer:Boolean,disabled:Boolean,allowEmpty:Boolean,inputWidth:[Number,String],buttonSize:[Number,String],asyncChange:Boolean,placeholder:String,disablePlus:Boolean,disableMinus:Boolean,disableInput:Boolean,decimalLength:[Number,String],name:{type:[Number,String],default:""},min:{type:[Number,String],default:1},max:{type:[Number,String],default:1/0},step:{type:[Number,String],default:1},defaultValue:{type:[Number,String],default:1},showPlus:{type:Boolean,default:!0},showMinus:{type:Boolean,default:!0},longPress:{type:Boolean,default:!0}},data:function(){var t,e=null!=(t=this.value)?t:this.defaultValue,i=this.format(e);return w(i,this.value)||this.$emit("input",i),{currentValue:i}},computed:{minusDisabled:function(){return this.disabled||this.disableMinus||this.currentValue<=+this.min},plusDisabled:function(){return this.disabled||this.disablePlus||this.currentValue>=+this.max},inputStyle:function(){var t={};return this.inputWidth&&(t.width=Object(l["a"])(this.inputWidth)),this.buttonSize&&(t.height=Object(l["a"])(this.buttonSize)),t},buttonStyle:function(){if(this.buttonSize){var t=Object(l["a"])(this.buttonSize);return{width:t,height:t}}}},watch:{max:"check",min:"check",integer:"check",decimalLength:"check",value:function(t){w(t,this.currentValue)||(this.currentValue=this.format(t))},currentValue:function(t){this.$emit("input",t),this.$emit("change",t,{name:this.name})}},methods:{check:function(){var t=this.format(this.currentValue);w(t,this.currentValue)||(this.currentValue=t)},formatNumber:function(t){return Object(p["a"])(String(t),!this.integer)},format:function(t){return this.allowEmpty&&""===t||(t=this.formatNumber(t),t=""===t?0:+t,t=Object(m["a"])(t)?this.min:t,t=Math.max(Math.min(this.max,t),this.min),Object(c["c"])(this.decimalLength)&&(t=t.toFixed(this.decimalLength))),t},onInput:function(t){var e=t.target.value,i=this.formatNumber(e);if(Object(c["c"])(this.decimalLength)&&-1!==i.indexOf(".")){var n=i.split(".");i=n[0]+"."+n[1].slice(0,this.decimalLength)}w(e,i)||(t.target.value=i),this.emitChange(i)},emitChange:function(t){this.asyncChange?(this.$emit("input",t),this.$emit("change",t,{name:this.name})):this.currentValue=t},onChange:function(){var t=this.type;if(this[t+"Disabled"])this.$emit("overlimit",t);else{var e="minus"===t?-this.step:+this.step,i=this.format(x(+this.currentValue,e));this.emitChange(i),this.$emit(t)}},onFocus:function(t){this.disableInput&&this.$refs.input?this.$refs.input.blur():this.$emit("focus",t)},onBlur:function(t){var e=this.format(t.target.value);t.target.value=e,this.currentValue=e,this.$emit("blur",t),Object(h["a"])()},longPressStep:function(){var t=this;this.longPressTimer=setTimeout((function(){t.onChange(),t.longPressStep(t.type)}),S)},onTouchStart:function(){var t=this;this.longPress&&(clearTimeout(this.longPressTimer),this.isLongPress=!1,this.longPressTimer=setTimeout((function(){t.isLongPress=!0,t.onChange(),t.longPressStep()}),y))},onTouchEnd:function(t){this.longPress&&(clearTimeout(this.longPressTimer),this.isLongPress&&Object(d["c"])(t))}},render:function(){var t=this,e=arguments[0],i=function(e){return{on:{click:function(i){i.preventDefault(),t.type=e,t.onChange()},touchstart:function(){t.type=e,t.onTouchStart()},touchend:t.onTouchEnd,touchcancel:t.onTouchEnd}}};return e("div",{class:v([this.theme])},[e("button",o()([{directives:[{name:"show",value:this.showMinus}],attrs:{type:"button"},style:this.buttonStyle,class:v("minus",{disabled:this.minusDisabled})},i("minus")])),e("input",{ref:"input",attrs:{type:this.integer?"tel":"text",role:"spinbutton",disabled:this.disabled,readonly:this.disableInput,inputmode:this.integer?"numeric":"decimal",placeholder:this.placeholder,"aria-valuemax":this.max,"aria-valuemin":this.min,"aria-valuenow":this.currentValue},class:v("input"),domProps:{value:this.currentValue},style:this.inputStyle,on:{input:this.onInput,focus:this.onFocus,blur:this.onBlur}}),e("button",o()([{directives:[{name:"show",value:this.showPlus}],attrs:{type:"button"},style:this.buttonStyle,class:v("plus",{disabled:this.plusDisabled})},i("plus")]))])}}),A={name:"person-times",props:{quantity:{type:Number,default:10},goodsDerails:{type:Object,default:function(){return{}}},paymentGroup:{type:String,default:null}},components:{Stepper:V,Icon:a["a"]},data:function(){return{}},computed:{value:function(){return this.paymentGroup?"lucky"===this.paymentGroup&&this.quantity>this.goodsDerails.groupShares.lucky?(this.$emit("setQuantity",this.goodsDerails.groupShares.lucky),this.remainShares):"raider"===this.paymentGroup&&this.quantity>this.goodsDerails.groupShares.raider?(this.$emit("setQuantity",this.goodsDerails.groupShares.raider),this.remainShares):this.quantity:this.quantity>this.goodsDerails.remainShares?(this.$emit("setQuantity",this.goodsDerails.remainShares),this.remainShares):this.quantity},options:function(){var t=[{text:"10",value:10},{text:"20",value:20},{text:"50",value:50},{text:"All remaining",value:this.paymentGroup?"lucky"===this.paymentGroup?this.goodsDerails.groupShares.lucky:this.goodsDerails.groupShares.raider:this.goodsDerails.remainShares}];return t}},methods:{setValue:function(t){t>this.goodsDerails.remainShares?(this.$toast("There is not so much left"),this.$emit("setQuantity",this.goodsDerails.remainShares)):this.$emit("setQuantity",t)},close:function(){this.$emit("close")}}},k=A,B=(i("c68e"),i("0f36"),i("2877")),O=Object(B["a"])(k,n,s,!1,null,"2c52d988",null);e["a"]=O.exports},4573:function(t,e){t.exports="data:image/png;base64,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"},"480b":function(t,e,i){"use strict";i("68ef"),i("9d70"),i("3743"),i("9ee3")},"5b77":function(t,e,i){"use strict";var n=i("9969"),s=i.n(n);s.a},7431:function(t,e,i){t.exports=i.p+"img/pk-home-treasure.cd641715.png"},9969:function(t,e,i){},"9e5f":function(t,e,i){t.exports=i.p+"img/pk-home-lucky.c70a4412.png"},"9ee3":function(t,e,i){},a37c:function(t,e,i){"use strict";var n=i("d282"),s=i("a142"),a=i("4598"),r=i("5fbe"),o=i("ad06"),u=Object(n["a"])("notice-bar"),l=u[0],c=u[1];e["a"]=l({mixins:[Object(r["a"])((function(t){t(window,"pageshow",this.start)}))],props:{text:String,mode:String,color:String,leftIcon:String,wrapable:Boolean,background:String,scrollable:{type:Boolean,default:null},delay:{type:[Number,String],default:1},speed:{type:[Number,String],default:50}},data:function(){return{show:!0,offset:0,duration:0,wrapWidth:0,contentWidth:0}},watch:{scrollable:"start",text:{handler:"start",immediate:!0}},activated:function(){this.start()},methods:{onClickIcon:function(t){"closeable"===this.mode&&(this.show=!1,this.$emit("close",t))},onTransitionEnd:function(){var t=this;this.offset=this.wrapWidth,this.duration=0,this.$nextTick((function(){Object(a["b"])((function(){t.offset=-t.contentWidth,t.duration=(t.contentWidth+t.wrapWidth)/t.speed,t.$emit("replay")}))}))},reset:function(){this.offset=0,this.duration=0,this.wrapWidth=0,this.contentWidth=0},start:function(){var t=this,e=Object(s["c"])(this.delay)?1e3*this.delay:0;this.reset(),clearTimeout(this.startTimer),this.startTimer=setTimeout((function(){var e=t.$refs,i=e.wrap,n=e.content;if(i&&n&&!1!==t.scrollable){var s=i.getBoundingClientRect().width,r=n.getBoundingClientRect().width;(t.scrollable||r>s)&&Object(a["b"])((function(){t.offset=-r,t.duration=r/t.speed,t.wrapWidth=s,t.contentWidth=r}))}}),e)}},render:function(){var t=this,e=arguments[0],i=this.slots,n=this.mode,s=this.leftIcon,a=this.onClickIcon,r={color:this.color,background:this.background},u={transform:this.offset?"translateX("+this.offset+"px)":"",transitionDuration:this.duration+"s"};function l(){var t=i("left-icon");return t||(s?e(o["a"],{class:c("left-icon"),attrs:{name:s}}):void 0)}function h(){var t,s=i("right-icon");return s||("closeable"===n?t="cross":"link"===n&&(t="arrow"),t?e(o["a"],{class:c("right-icon"),attrs:{name:t},on:{click:a}}):void 0)}return e("div",{attrs:{role:"alert"},directives:[{name:"show",value:this.show}],class:c({wrapable:this.wrapable}),style:r,on:{click:function(e){t.$emit("click",e)}}},[l(),e("div",{ref:"wrap",class:c("wrap"),attrs:{role:"marquee"}},[e("div",{ref:"content",class:[c("content"),{"van-ellipsis":!1===this.scrollable&&!this.wrapable}],style:u,on:{transitionend:this.onTransitionEnd}},[this.slots()||this.text])]),h()])}})},a5b9:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("van-row",{attrs:{align:"center",id:"gold-footer",justify:"space-between",type:"flex"}},[n("van-col",[n("img",{staticClass:"ic-gold",attrs:{src:i("4573")}})]),n("van-col",{staticClass:"gold-nub"},[t._v(" Total coins: "),n("span",{staticClass:"font-orange"},[t._v(t._s(t.totalPrice))])]),n("van-col",[n("Button",{staticClass:"goods-button",style:t.buttonStyle,attrs:{loading:t.loading,"loading-text":t.loadingText,"loading-type":"spinner",type:"primary"},on:{click:t.clickButton}},[t._v(t._s(t.buttonText))])],1)],1)},s=[],a=(i("a9e3"),i("66b9"),i("b650")),r={name:"gold-footer",props:{totalPrice:{type:Number,default:0},buttonText:{type:String,default:""},buttonStyle:{type:Object,default:function(){return{}}},loading:{type:Boolean,default:!1},loadingText:{type:String,default:""}},components:{Button:a["a"]},data:function(){return{}},methods:{clickButton:function(){this.$emit("clickButton")}}},o=r,u=(i("5b77"),i("2877")),l=Object(u["a"])(o,n,s,!1,null,"692a4960",null);e["a"]=l.exports},a938:function(t,e,i){},c3a6:function(t,e,i){"use strict";i("68ef"),i("9d70"),i("3743")},c68e:function(t,e,i){"use strict";var n=i("a938"),s=i.n(n);s.a},fb6c:function(t,e,i){}}]);