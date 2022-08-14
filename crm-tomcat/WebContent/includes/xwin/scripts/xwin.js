/*
说明:xWin改进版  
  目前x系列支持的浏览器类型为:IE5.5, FF1.5
  xWin的标签为
  	<div class="xWin" title="窗口标题" style="display:none;width:450px;height:120px;left:20px;">  		
  		content
  	</div>
  
  支持属性:
    int left, int top, int width, int height
  支持方法:
    Close(), Destroy(), Max([bolean Variable]), Min([bolean Variable]), 
    MoveTo(int x, int y), ResizeTo(int x, int y), SetContent(string Variable | htmlObject Variable)
	SetTitle(string Variable), ShowHide()

  改进的地方:布局不是从<span class="xWinSetting">获得，而是直接从div的style里取，标题从div的title中取，样式直接设置
  *增加_stat属性表明是否顶端始终显示标志 
*/

//**检测浏览器种类*****
var BROWSERNAME="";
switch(navigator.appName.toLowerCase()){
	case "netscape":
		BROWSERNAME="ns";
	break;
	case "microsoft internet explorer":
	default:
		BROWSERNAME="ie";
	break;
}

//**设置全局定时器******
if(typeof(__xSeriaTimer__)=="undefined"){
	var __xSeriaTimer__={
		events:new Array(),
		objs:new Array(),
		handle:null,
		exec:function(){
			for(var i=0;i<__xSeriaTimer__.events.length;i++){
				try{
					with(__xSeriaTimer__.objs[i]){
						eval(__xSeriaTimer__.events[i]);
					}
				}catch(e){}
			}
		},
		pop:function(i){
			__xSeriaTimer__.events[i]=null;
			__xSeriaTimer__.objs[i]=null;
		},
		push:function(strV,obj){
			for(var i=0;i<__xSeriaTimer__.events.length;i++){
				if(__xSeriaTimer__.events[i]==null){
					__xSeriaTimer__.events[i]=strV;
					__xSeriaTimer__.objs[i]=obj;
					return(i);
				}
			}
			__xSeriaTimer__.events[i]=strV;
			__xSeriaTimer__.objs[i]=obj;
			return(i);
		},
		start:function(){
			__xSeriaTimer__.stop();
			__xSeriaTimer__.handle=setInterval(__xSeriaTimer__.exec,30);
		},
		stop:function(){
			clearInterval(__xSeriaTimer__.handle);
		}
	};
	__xSeriaTimer__.start();
}
//**初始化函数******
function xWinInit(){
	var allTheWindows=document.getElementsByTagName("div");
	for(var i=0;i<allTheWindows.length;i++){
		if(allTheWindows[i].className=="xWin")_xWin_event_doInit(allTheWindows[i]);
	}
}
//**事件响应函数区******
function _xWin_event_doInit(element){
	//初始化变量
	element.ownerDocument.index=isNaN(element.ownerDocument.index)?10000:parseInt(element.ownerDocument.index)+1;
	element.x0=0;element.y0=0;
	element.x1=0;element.y1=0;
	element.w0=0;element.h0=0;
	element.offx=6;element.offy=6;	//阴影的偏移量
	element.padx=0;element.pady=0;
	element.minW=90;element.minH=(BROWSERNAME=="ns"?20:20);
	element.moveable=false;
	element.resizable=false;
	element.hover='#000000';element.normal='#ffffff';
	element.minButton=BROWSERNAME=="ie"?"0":"_";
	element.maxButton=BROWSERNAME=="ie"?"1":"=";
	element.normalButton=BROWSERNAME=="ie"?"2":"+";
	element.closeButton=BROWSERNAME=="ie"?"r":"X";
	element._stat="";
	element._title="Untitled Window";
	element._body="";
	element._winRect={l:0,t:0,w:0,h:0};
	element._restoredWinRect={l:0,t:0,w:0,h:0};
	element._windowState="normal";
	element.xwin=element;
	
	//设置方法
	element.Close=_xWin_method_Close;
	element.Destroy=_xWin_method_Destroy;
	element.Max=_xWin_method_Max;
	element.Min=_xWin_method_Min;
	element.MoveTo=_xWin_method_MoveTo;
	element.ResizeTo=_xWin_method_ResizeTo;
	element.SetContent=_xWin_method_SetContent;
	element.SetTitle=_xWin_method_SetTitle;
	element.ShowHide=_xWin_method_ShowHide;
	element.SetStat=_xWin_method_SetStat;
	
	//设置事件
	element.onmousedown=_xWin_event_doMDown;
	element.onmouseup=element.onlosecapture=_xWin_event_doMUp;
	element.onmousemove=_xWin_event_doMMove;
	element.onclick=_xWin_event_doClick;
	element.onselectstart=element.onselect=_xWin_event_doSelect;
	
	//记录显示风格
	var tempDisplay=element.style.display;
	//改变显示风格
	element.style.display="block";
	//设置窗口变量
	var w=parseInt(element.style.width);
	w=isNaN(w)?(element.offsetWidth+10):parseInt(w);
	w=w<element.minW?element.minW:w;
	var h=parseInt(element.style.height);
	h=isNaN(h)?(element.offsetHeight+30):parseInt(h);
	h=h<element.minH?element.minH:h;
	var l=parseInt(element.style.left);
	l=isNaN(l)?element.offsetLeft:parseInt(l);
	l=l<1?1:l;
	var t=parseInt(element.style.top);
	t=isNaN(t)?element.offsetTop:parseInt(t);
	t=t<1?1:t;
	var z=element.ownerDocument.index;
	var title=new String(element.title);
	
	//设置窗口标题
	element.oTitle=element.ownerDocument.createElement("div");
	element.oTitle.xwin=element;
	element.oTitle.className="xTitle";
	element.appendChild(element.oTitle);
	
	//设置窗口标题内容
	element.oTitleContent=element.ownerDocument.createElement("span");
	element.oTitleContent.xwin=element;
	element.oTitleContent.className="xWinTitleContent";
	element.oTitle.appendChild(element.oTitleContent);
	element.oTitleContent.ondblclick=function(){this.xwin.Max();};
	element.SetTitle(title);
	
	//设置窗口标题关闭按钮
	element.oTitleCButton=element.ownerDocument.createElement("input");
	element.oTitleCButton.xwin=element;
	element.oTitleCButton.type="button";
	element.oTitleCButton.className="xWinTitleCloseButton";
	element.oTitle.appendChild(element.oTitleCButton);
	element.oTitleCButton.onclick=function(){this.xwin.ShowHide("none","");};
	element.oTitleCButton.value=element.closeButton;
	
	//设置窗口标题最大化按钮
	element.oTitleMaButton=element.ownerDocument.createElement("input");
	element.oTitleMaButton.xwin=element;
	element.oTitleMaButton.type="button";
	element.oTitleMaButton.className="xWinTitleMaxButton";
	element.oTitle.appendChild(element.oTitleMaButton);
	element.oTitleMaButton.onclick=function(){this.xwin.Max();};
	element.oTitleMaButton.value=element.maxButton;
	
	//设置窗口标题最小化按钮
	element.oTitleMButton=element.ownerDocument.createElement("input");
	element.oTitleMButton.xwin=element;
	element.oTitleMButton.type="button";
	element.oTitleMButton.className="xWinTitleMinButton";
	element.oTitle.appendChild(element.oTitleMButton);
	element.oTitleMButton.onclick=function(){this.xwin.Min();};
	element.oTitleMButton.value=element.minButton;
	
	//设置窗口内容
	element.oContent=element.ownerDocument.createElement("div");
	element.oContent.xwin=element;
	element.oContent.className="xWinBody";
	element.appendChild(element.oContent);
	oC=element.firstChild;
	while(oC){
		tC=oC.nextSibling;
		if(oC!=element.oTitle&&oC!=element.oContent){			
			element.oContent.appendChild(oC);
		}
		oC=tC;
	}
	
	//设置窗口阴影	
	element.oShadow=element.ownerDocument.createElement("div");
	element.oShadow.xwin=element;
	element.oShadow.className="xShadow";
	element.parentNode.insertBefore(element.oShadow,element.nextSibling);
	element.oShadow.style.zIndex=z-1;
	
	//设置透明的DIV层覆盖SELECT控件
	element.bIframe = element.ownerDocument.createElement("iframe");
	element.bIframe.xwin=element;
	element.bIframe.className="xBIframe";
	element.appendChild(element.bIframe);

	element.MoveTo(l,t);
	element.ResizeTo(w,h);

	//恢复显示风格
	element.style.display=tempDisplay;
	//设置窗口样式
	with(element.style){
		zIndex=z;
		backgroundColor=element.normal;
		color=element.normal;
	}
	padx=element.offsetWidth-element.clientWidth;
	pady=element.offsetHeight-element.clientHeight;
	
}
function _xWin_event_doMDown(evt){
	var e=evt?evt:window.event;
	var eSrc=e.srcElement?e.srcElement:e.target;
	var leftButton=e.srcElement?e.button==1:e.button==0;
//	if(this.style.zIndex!=this.ownerDocument.index){//将窗口放到最前
//		this.ownerDocument.index+=2;
//		var idx = this.ownerDocument.index;
//		this.style.zIndex=idx;
//		this.nextSibling.style.zIndex=idx-1;
//	}
	if(eSrc==this.oTitleContent&&leftButton&&this._windowState=="normal"){//如果开始拖动
		//锁定标题栏;
		document.captureEvents?document.captureEvents("mousemove",this.oTitle):this.oTitle.setCapture();
		//定义对象;
		var win = this;
		var sha = win.nextSibling;
		//记录鼠标和层位置;
		this.x0 = e.clientX;
		this.y0 = e.clientY;
		this.x1 = parseInt(win.style.left);
		this.y1 = parseInt(win.style.top);
		//改变风格;
		this.oTitle.style.backgroundColor = this.hover;
		win.style.borderColor = this.hover;
		this.oTitle.nextSibling.style.color = this.hover;
		sha.style.left = this.x1 + this.offx;
		sha.style.top  = this.y1 + this.offy;
		this.moveable = true;
		return(true);
	}
	if(this.style.cursor!="default"&&this._windowState=="normal"){//开始改变大小
		//锁定标题栏;
		document.captureEvents?document.captureEvents("mousemove",this.oTitle):this.oTitle.setCapture();
		//定义对象;
		var win = this;
		var sha = win.nextSibling;
		//记录鼠标位置和层位置和大小;
		this.x0=e.clientX;
		this.y0=e.clientY;
		this.x1=parseInt(win.offsetLeft);
		this.y1=parseInt(win.offsetTop);
		this.w0=parseInt(win.offsetWidth);
		this.h0=parseInt(win.offsetHeight);
		//改变风格;
		this.oTitle.style.backgroundColor = this.hover;
		win.style.borderColor = this.hover;
		this.oTitle.nextSibling.style.color = this.hover;
		sha.style.left = this.x1 + this.offx;
		sha.style.top  = this.y1 + this.offy;
		this.resizable = true;
		return(true);
	}
}
function _xWin_event_doMUp(evt){
	var e=evt?evt:window.event;
	document.releaseEvents?document.releaseEvents("mousemove",this.oTitle):this.oTitle.releaseCapture();
	if(this.moveable){
		var win = this;
		var sha = win.nextSibling;
		var msg = this.oTitle.nextSibling;
		win.style.borderColor     = "";
		this.oTitle.style.backgroundColor = "";
		msg.style.color           = "";
		sha.style.left = this.oTitle.parentNode.style.left;
		sha.style.top  = this.oTitle.parentNode.style.top;
		this.moveable = false;
		return(false);
	}
	if(this.resizable){
		var win = this;
		var sha = win.nextSibling;
		var msg = this.oTitle.nextSibling;
		win.style.borderColor     = "";
		this.oTitle.style.backgroundColor = "";
		msg.style.color           = "";
		sha.style.left = this.oTitle.parentNode.style.left;
		sha.style.top  = this.oTitle.parentNode.style.top;
		sha.style.width = this.oTitle.parentNode.style.width;
		sha.style.height = this.oTitle.parentNode.style.height;
		this.style.cursor="default";
		this.resizable = false;
		return(false);
	}
}
function _xWin_event_doMMove(evt){
	var e=evt?evt:window.event;
	if(this.moveable){//拖动窗口
		this.MoveTo(this.x1 + e.clientX - this.x0, this.y1 + e.clientY - this.y0);
		return(true);
	}
	if(this.resizable){//改变窗口大小
		var xxx=this.style.cursor.substring(0,2).match(/[we]/i);
		var yyy=this.style.cursor.substring(0,2).match(/[ns]/i);
		l=this.offsetLeft;
		t=this.offsetTop;
		w=parseInt(this.style.width);
		h=parseInt(this.style.height);
		if(xxx=="w"){
			l=this.x1+e.clientX - this.x0;
			w=this.w0+this.x0-e.clientX;
			if(l<0){w+=l;l=0;}
			if(w<this.minW){l=l+w-this.minW;w=this.minW;}
		}
		if(xxx=="e"){
			w=this.w0+e.clientX-this.x0;
			w=w<this.minW?this.minW:w;
		}
		if(yyy=="n"){
			t=this.y1+e.clientY - this.y0;
			h=this.h0+this.y0-e.clientY;
			if(t<0){h+=t;t=0;}
			if(h<this.minH){t=t+h-this.minH;h=this.minH;}
		}
		if(yyy=="s"){
			h=this.h0+e.clientY-this.y0;
			h=h<this.minH?this.minH:h;
		}
		this.MoveTo(l,t);
		this.ResizeTo(w,h);
		
		return(true);
	}
	if(this._windowState=="normal"){
		var cc="";
		x=window.getRealLeft(this);
		y=window.getRealTop(this);
		w=parseInt(this.offsetWidth);
		h=parseInt(this.offsetHeight);
		if(e.clientY-y<5)cc+="n";
		if(y+h-e.clientY<5)cc+="s";
		if(e.clientX-x<5)cc+="w";
		if(x+w-e.clientX<5)cc+="e";
		if(cc!=""){
			this.style.cursor=cc+"-resize";
			return(true);
		}
		if(this.style.cursor!="default"){
			this.style.cursor="default";
		}
	}
}
function _xWin_event_doClick(evt){
	var e=evt?evt:window.event;
	var eSrc=e.srcElement?e.srcElement:e.target;
	switch(eSrc){
		
	}
}
function _xWin_event_doSelect(evt){
	var e=evt?evt:window.event;
	var eSrc=e.srcElement?e.srcElement:e.target;
	if(eSrc==this.oTitle||this.oTitle.contains(eSrc)){
		e.cancelBubble=true;
		e.returnValue=false;
		return(false);
	}
}
function _xWinParentNode_event_doScroll(evt){
	if(!this.minimizedWindows)return(true);
	for(var i=0;i<this.minimizedWindows.length;i++){
		this.minimizedWindows[i].Min(true);
	}
}
//**方法函数区******
function _xWin_method_Close(){
	this.Destroy();
}
function _xWin_method_Destroy(){
	if(this.minIndex){
		this.parentNode.minimizedWindows[this.minIndex]=null;
		this.minIndex=null;
	}
	this.outerHTML="";
}


function _xWin_method_Max(reV){

	if(this._windowState=="maximize"&&(!reV)){
		//还原父节点overflow属性
		this.parentNode.style.overflow=this.parentNode.restoredStyle_overflow;
		
		this.MoveTo(this._restoredWinRect.l,this._restoredWinRect.t);
		this.ResizeTo(this._restoredWinRect.w,this._restoredWinRect.h);
		this.oTitleMButton.value = this.minButton;
		this.oTitleMaButton.value = this.maxButton;
		this._windowState="normal";
	}else{
		if(this._windowState=="normal"){
			this._restoredWinRect.l=this._winRect.l;this._restoredWinRect.t=this._winRect.t;
			this._restoredWinRect.w=this._winRect.w;this._restoredWinRect.h=this._winRect.h;
		}
		if(this.minIndex!=null){
			this.parentNode.minimizedWindows[this.minIndex]=null;
			this.minIndex=null;
		}
		if(this.minTimeHandle!=null){
			__xSeriaTimer__.pop(this.minTimeHandle);
			this.minTimeHandle=null;
		}
		//纪录父节点overflow属性
		this.parentNode.restoredStyle_overflow=this.parentNode.style.overflow;
		//改变父节点overflow属性
		this.parentNode.style.overflow="hidden";
		
		this.MoveTo(0,0);
		if(this.parentNode!=document.body&&this.parentNode.nodeName!="FORM"){
			w=this.parentNode.clientWidth-5;
			h=this.parentNode.clientHeight-4;
		}else{
			w=document.body.clientWidth-5;
			h=document.body.clientHeight-4;
		}

		this.ResizeTo(w,h);
		this.oTitleMButton.value = this.minButton;
		this.oTitleMaButton.value = this.normalButton;
		this._windowState="maximize";
		this.scrollIntoView();
	}
}
function _xWin_method_Min(reV){
	if(this._windowState=="minimize"&&(!reV)){
		this.MoveTo(this._restoredWinRect.l,this._restoredWinRect.t);
		this.ResizeTo(this._restoredWinRect.w,this._restoredWinRect.h);
		this.oTitleMButton.value = this.minButton;
		this.oTitleMaButton.value = this.maxButton;
		this._windowState="normal";
		this.parentNode.minimizedWindows[this.minIndex]=null;
		this.minIndex=null;
		if(this.minTimeHandle!=null){
			__xSeriaTimer__.pop(this.minTimeHandle);
			this.minTimeHandle=null;
		}
	}else{
		if(this._windowState=="normal"){
			this._restoredWinRect.l=this._winRect.l;this._restoredWinRect.t=this._winRect.t;
			this._restoredWinRect.w=this._winRect.w;this._restoredWinRect.h=this._winRect.h;
		}else{
			//还原父节点overflow属性
			try{
				this.parentNode.style.overflow=this.parentNode.restoredStyle_overflow;
			}catch(e){}
		}
		if(!this.parentNode.minimizedWindows)this.parentNode.minimizedWindows=new Array();
		if(this._windowState!="minimize"){
			for(var i=0;i<this.parentNode.minimizedWindows.length;i++){
				if(this.parentNode.minimizedWindows[i]==null)break;
			}
			this.parentNode.minimizedWindows[i]=this;
		}else{
			i=this.minIndex;
		}
		this.ResizeTo(0,0);
		var w=this.offsetWidth;
		var h=this.offsetHeight;
		var mw=getParentRect(this).mw;
		var mh=getParentRect(this).mh;
		var n=parseInt(mw/w);
		var t=parseInt(i/n)+1;
		this.MoveTo(w*(i%n),mh-t*h-1);
		if(mh>getParentRect(this).mh){
			this.MoveTo(w*(i%n),mh-t*h-200);
			mh=getParentRect(this).mh;
			this.MoveTo(w*(i%n),mh-t*h-1);
		}
		this.minIndex=i;
		this.oTitleMButton.value = this.normalButton;
		this.oTitleMaButton.value = this.maxButton;
		this._windowState="minimize";
		if(this.minTimeHandle==null){
			this.minTimeHandle=__xSeriaTimer__.push("Min(true)",this);
			this.scrollIntoView();
		}
	}
}
function _xWin_method_MoveTo(x,y){

	var win = this.oTitle.parentNode;
	var sha = win.nextSibling;
	
	x=isNaN(x)?0:parseInt(x);
	y=isNaN(y)?0:parseInt(y);
	x=x<0?0:x;
	y=y<0?0:y;
	this.style.left=x+"px";
	this.style.top=y+"px";
	
	sha.style.left = parseInt(win.style.left) + ((this.moveable||this.resizable)?this.offx:0)+"px";
	sha.style.top  = parseInt(win.style.top) + ((this.moveable||this.resizable)?this.offy:0)+"px";
	this._winRect.l=x;
	this._winRect.t=y;
}
function _xWin_method_ResizeTo(w,h){
	var win = this;
	var sha = win.nextSibling;
		
	var w=isNaN(w)?this.minW:parseInt(w);
	var h=isNaN(h)?this.minH:parseInt(h);
	var w=w<this.minW?this.minW:w;
	var h=h<this.minH?this.minH:h;
	
	this.style.width=w+"px";
	this.style.height=h+"px";
	this.oTitle.style.width=parseInt(this.clientWidth)+"px";
	var wTC=this.clientWidth;
	for(var i=0;i<this.oTitle.childNodes.length;i++){
		if(this.oTitle.childNodes[i]!=this.oTitleContent){wTC-=this.oTitle.childNodes[i].offsetWidth;}
	}
	wTC-=8;
	this.oTitleContent.style.width=(wTC<1?1:wTC)+"px";
	
	var wC=this.clientHeight-this.oTitle.offsetHeight;
	this.oContent.style.height=(wC<1?1:wC)+"px";
	this.oContent.style.width=parseInt(this.clientWidth)+"px";
	sha.style.left = parseInt(win.style.left) + ((this.moveable||this.resizable)?this.offx:0)+"px";
	sha.style.top  = parseInt(win.style.top) + ((this.moveable||this.resizable)?this.offy:0)+"px";
	sha.style.width = parseInt(win.style.width)+"px";
	sha.style.height = parseInt(win.style.height)+"px";
	
	this._winRect.w=w;
	this._winRect.h=h;
}
function _xWin_method_SetContent(v){
	if(this.oContent){
		if(v==null||v==undefined||v==""){this._body=this.oContent.innerHTML="";return(this.oContent);}
		if(typeof(v)=="string"){
			this._body=this.oContent.innerHTML=v;
			return(this.oContent);
		}else{
			try{
				this.oContent.innerHTML="";
				this._body=this.oContent.appendChild(v);
				return(this.oContent);
			}catch(e){
				throw(e);
			}
		}
	}else{
		this._body=v;
		return(null);
	}
}
function _xWin_method_SetTitle(strT){
	this._title=strT==null?this._title:strT;
	if(this.oTitleContent){
		setInnerText(this.oTitleContent,this._title);
	}
}
function _xWin_method_ShowHide(dis,stat){
	if(stat!=null)
		this._stat = stat;
	if(this._stat=="topshow")
		return;
	var bdisplay = (dis==null)?((this.style.display=="none")?"":"none"):dis;
	this.style.display = bdisplay;
	this.nextSibling.style.display = bdisplay;
	if(bdisplay=="none"){
		if(this._windowState=="minimize"){
			this.parentNode.minimizedWindows[this.minIndex]=null;
			this.minIndex=null;
		}
	}else{
		if(this._windowState=="minimize"){
			this.Min();
		}
	}
}

/**
 * 设置顶端显示
 */
function _xWin_method_SetStat(){
	
	if(this._stat=="")
	{
		this.style.display = "";
		this.nextSibling.style.display = "";
		
		this._stat="topshow";
	}	
	else
	{
		this.style.display = "none";
		this.nextSibling.style.display = "none";
		this._stat="";
	}
}


//**通用函数区***
function getRealLeft(o){
	var l=o.offsetLeft-o.scrollLeft;
	while(o=o.offsetParent){
		l+=o.offsetLeft-o.scrollLeft;
	}
	return(l);
}
function getRealTop(o){
	var t=o.offsetTop-o.scrollTop;
	while(o=o.offsetParent){
		t+=o.offsetTop-o.scrollTop;
	}
	return(t);
}
function setInnerText(obj,text){
	switch(BROWSERNAME){
		case "ns":
			obj.textContent=text;
		break;
		case "ie":
		default:
			obj.innerText=text;
	}
}
function getElementByClassName(obj,className){
	for(var i=0;i<obj.childNodes.length;i++){
		if(obj.childNodes[i].className==className)return(obj.childNodes[i]);
	}
	return(null);
}
function getParentRect(obj){
	var re=new Object();
	if(obj.parentNode!=document.body){
		re.mw=Math.max(obj.parentNode.scrollWidth,obj.parentNode.clientWidth);
		re.mh=Math.max(obj.parentNode.scrollHeight,obj.parentNode.clientHeight);
	}else{
		re.mw=Math.max(document.documentElement.scrollWidth,document.documentElement.clientWidth);
		re.mh=Math.max(document.documentElement.scrollHeight,document.documentElement.clientHeight);
	}
	return(re);
}