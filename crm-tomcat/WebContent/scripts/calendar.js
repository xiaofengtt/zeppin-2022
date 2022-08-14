var SearchBtn = null;
var Browser=new Object();
var ua=navigator.userAgent.toLowerCase();
Browser.isMozilla=(typeof document.implementation!='undefined')&&(typeof document.implementation.createDocument!='undefined')&&(typeof HTMLDocument!='undefined');
Browser.isIE=window.ActiveXObject?true:false;
Browser.isFirefox=(ua.indexOf("firefox")!=-1);
Browser.isSafari=(ua.indexOf("Browser.isSafari")!=-1);
Browser.isOpera=(typeof window.opera!='undefined');
var undefined;
String.prototype.trim=function(){return this.replace(/^\s+|\s+$/,'');};
function F(thisArg,funcRef){return function(){return funcRef.apply(thisArg,arguments);};}
function $trim(value){value=""+value;return value.replace(/^\s+|\s+$/,'');}
function $addScript(id,src){var o=$$("script");o.id=id;o.type="text/javascript";o.setAttribute("src",src);document.getElementsByTagName('head')[0].appendChild(o);return o;}
function $prefetchJS(id,src){var o=$$("link");o.id=id;o.setAttribute("type","text/css");o.setAttribute("rel","stylesheet");o.setAttribute("href",src);document.getElementsByTagName('head')[0].appendChild(o);return o;}
function htmlEncode(text){return text.replace(/&/g,'&amp;').replace(/"/g,'&quot;').replace(/</g,'&lt;').replace(/>/g,'&gt;');}
function $showMsg(text,timeout){$('errorMsg').innerHTML=text;$display('messageBar');if(typeof timeout!="undefined")window.setTimeout($hideMsg,timeout);}
function $hideMsg(){$nodisplay('messageBar');}
function $$(tagName,id){var e=document.createElement(tagName);if(id)e.id=id;return e;}
function $(id){return document.getElementById(id);}
function T(e,text){var item;if(typeof e=="object")item=e;else item=$(e);if(Browser.isFirefox)item.textContent=text;else item.innerText=text;}
function $isVisible(e){var item=e;if(typeof item!="object")item=$(e);return(item.style.visibility!="hidden"&&item.style.display!="none");}
function $nodisplay(e){if(typeof e=="object")e.style.display="none";else if($(e)!=null)$(e).style.display="none";}
function $display(e){if(typeof e=="object")e.style.display="block";else if($(e)!=null)$(e).style.display="block";}
function $clearDisplay(e){if(typeof e=="object")e.style.display="";else if($(e)!=null)$(e).style.display="";}
function $hide(e){if(typeof e=="object")e.style.visibility="hidden";else if($(e)!=null)$(e).style.visibility="hidden";}
function $visible(e){if(typeof e=="object")e.style.visibility="visible";else if($(e)!=null)$(e).style.visibility="visible";}
function $toggleDisplay(e){var id=(typeof e=="object")?e.id:e;Utility.toggleVisibility(id);};
function $focus(e){if(typeof e=="object")e.focus();else $(e).focus();}
function $fix(e){return PU.fix(e);}
function $stopBubble(event){event=$fix(event);event.stopPropagation();event.preventDefault();return false;}
function $remove(e){var item=(typeof e=="object"?e:$(e));item.parentNode.removeChild(item);}
function $urlParam(name){var regexS="[\\?&]"+name+"=([^&#]*)";var regex=new RegExp(regexS);var tmpURL=document.location.href;var results=regex.exec(tmpURL);if(results==null)return"";else return results[1];}
function $fixTable(table){var tr=document.createElement("tr");table.firstChild.appendChild(tr);table.firstChild.removeChild(tr);}
function $scrollTop(){window.scrollTo(0,0);}
function $disabled(e)
{
var item=e;
if(typeof e!="object")item=$(e);
if(item.disabled==true)return;
item.disabled=true;
item._cursor=""+item.style.cursor;
item.style.cursor="default";
item._color=""+item.style.color;
item.style.color="lightgrey";
item._onclick=item.onclick;
item.onclick=function(){return false;}}
function $enabled(e)
{
var item=e;
if(typeof e!="object")item=$(e);
if(item.disabled==false)return;
item.disabled=false;
item.style.cursor=""+item._cursor;
item.style.color=""+item._color;
item.onclick=item._onclick;
item._cursor=null;
item._color=null;
item._onclick=null;}
//$toggle
$toggle=function(btnId,divId)
{
var btn=$(btnId);
if($isVisible(divId)){$nodisplay(divId);btn.className=TOGGLE_ICON_EXPAND;}
else{$display(divId);btn.className=TOGGLE_ICON_COLLAPSE;}}

// Utility
var Utility={
//Utility.fix(event)
fix:function(event)
{
if(!event)event=window.event;
if(!event)return null;
if(!event.stopPropagation){event.stopPropagation=new Function('this.cancelBubble = true')}
if(!event.preventDefault){event.preventDefault=new Function('this.returnValue = true')}
if(typeof event.layerX=='undefined'&&typeof event.offsetX=='number'){
event.layerX=event.offsetX;event.layerY=event.offsetY;}
if(event.target){
if(event.target.nodeType==3)event.target=event.target.parentNode;}
if(!event.target&&event.srcElement){
event.target=event.srcElement;
if(event.type=='onmouseout'){
event.relatedTarget=event.toElement;}
else if(event.type=='onmouseover'){
event.relatedTarget=event.fromElement;}}
return event;},
//Utility.getViewportWidth()
getViewportWidth:function(){
var width=0;
if(document.documentElement&&document.documentElement.clientWidth){
width=document.documentElement.clientWidth;}
else if(document.body&&document.body.clientWidth){
width=document.body.clientWidth;}
else if(window.innerWidth){
width=window.innerWidth-18;}
return width;},
//Utility.getContentHeight()
getContentHeight:function()
{
if(document.body&&document.body.clientHeight){
return document.body.clientHeight;}},
//Utility.getViewportHeight()
getViewportHeight:function(){
var height=0;
if(window.innerHeight){
height=window.innerHeight-18;}
else if(document.documentElement&&document.documentElement.clientHeight){
height=document.documentElement.clientHeight;}
else if(document.body&&document.body.clientHeight){
height=document.body.clientHeight;}
return height;},
//Utility.getViewportScrollX()
getViewportScrollX:function(){
var scrollX=0;
if(document.documentElement&&document.documentElement.scrollLeft){
scrollX=document.documentElement.scrollLeft;}
else if(document.body&&document.body.scrollLeft){
scrollX=document.body.scrollLeft;}
else if(window.pageXOffset){
scrollX=window.pageXOffset;}
else if(window.scrollX){
scrollX=window.scrollX;}
return scrollX;},
//Utility.getViewportScrollY()
getViewportScrollY:function(){
var scrollY=0;
if(document.documentElement&&document.documentElement.scrollTop){
scrollY=document.documentElement.scrollTop;}
else if(document.body&&document.body.scrollTop){
scrollY=document.body.scrollTop;}
else if(window.pageYOffset){
scrollY=window.pageYOffset;}
else if(window.scrollY){
scrollY=window.scrollY;}
return scrollY;},
//Utility.centerDiv(div)
centerDiv:function(div)
{
var top=((PU.getViewportHeight()-div.offsetHeight)/2);
if(top<0)top=10;
div.style.left=((PU.getViewportWidth()-div.offsetWidth)/2)+"px";
div.style.top=top+"px";},
//Utility.changeParent(item,parent)
changeParent:function(item,parent)
{
var e=(item.tagName?item:$(item));
e.parentNode.removeChild(e);
if(parent!=null)
{
var newParent=(parent.tagName?parent:$(parent));
newParent.appendChild(e);}},
setBusy:function()
{
document.documentElement.style.cursor='wait';},
setIdle:function()
{
document.documentElement.style.cursor='';},
setInnerText:function(e,text)
{
if(Browser.isFirefox)e.textContent=text;
else e.innerText=text;},
//Utility.toggleVisibility(name)
toggleVisibility:function(name)
{
var e=$(name);
if($isVisible(e))
$nodisplay(e);
else
$display(e);},
//Utility.LastZIndx
LastZIndex:1000,
//Utility.makeOnTop(el)
makeOnTop:function(el)
{
el.style.zIndex=Utility.LastZIndex++;},
//Utility.showProgress(msg)
showProgress:function(msg)
{
$display("Progress");},
//Utility.hideProgress()
hideProgress:function()
{
$nodisplay("Progress");},
//Utility.getPosition(obj)
getPosition:function(obj){
if(obj){
var w=obj.offsetWidth;
var h=obj.offsetHeight;
if(obj.offsetParent){
for(var posX=0,posY=0;obj.offsetParent;obj=obj.offsetParent){
posX+=obj.offsetLeft;
posY+=obj.offsetTop;}
return[posX,posY,w,h];}else{
return[obj.x,obj.y,w,h];}}else{
return[0,0,0,0];}},
//Utility.disableAllFrames()
disableAllFrames:function()
{
for(var i=0;i<window.frames.length;i++)
window.frames[i].disabled=true;},
//Utility.opacity(id,opacStart,opacEnd,millisec,callback)
opacity:function(id,opacStart,opacEnd,millisec,callback){
var steps=(opacEnd-opacStart)/4;
var timer=millisec/4;
Utility.changeOpac(opacStart,id);
function updateOpac(opac)
{
Utility.changeOpac(opac,id);
if(opac==opacEnd)
{
if(opacEnd==100)Utility.clearOpac(id);
if(callback)callback();return;}
opac+=steps;
window.setTimeout(function(){updateOpac(opac);},timer);}
window.setTimeout(function(){updateOpac(opacStart);},timer);},
//Utility.changeOpac(opacity,id)
changeOpac:function(opacity,id){
var object=$(id).style;
object.opacity=(opacity/100);
object.MozOpacity=(opacity/100);
object.KhtmlOpacity=(opacity/100);
object.filter="alpha(opacity="+opacity+")";},
//Utility.clearOpac(id)
clearOpac:function(id){
var object=$(id).style;
object.opacity=null;
object.MozOpacity=null;
object.KhtmlOpacity=null;
object.filter=null;},
//Utility.shiftOpacity(id,millisec)
shiftOpacity:function(id,millisec){
if($(id).style.opacity==0){
Utility.opacity(id,0,100,millisec);}else{
Utility.opacity(id,100,0,millisec);}},
//Utility.currentOpac(id,opacEnd,millisec)
currentOpac:function(id,opacEnd,millisec){
var currentOpac=100;
if($(id).style.opacity<100){
currentOpac=$(id).style.opacity*100;}
Utility.opacity(id,currentOpac,opacEnd,millisec)},
//Utility.dumpException(result)
dumpException:function(result)
{
if(null==result)
{
return;}
if(typeof result.get_message=="function")
{
var msg=result.get_message();
msg+='\r'+result.get_stackTrace();
App.addError(msg);}},
//Utility.getErrorMessage(exception)
getErrorMessage:function(exception)
{
if(null==exception)
{
return "UNKNOWN_ERROR";}
if(typeof exception.get_message=="function")
{
var msg=exception.get_message();
return msg;}
else
return "UNKNOWN_ERROR";},
//Utility.getXmlDoc(xmlString)
getXmlDoc:function(xmlString)
{
var myDocument;
if(document.implementation.createDocument)
{
var parser=new DOMParser();
myDocument=parser.parseFromString(xmlString,"text/xml");}
else if(window.ActiveXObject)
{
myDocument=new ActiveXObject("Microsoft.XMLDOM");
myDocument.async="false";
myDocument.loadXML(xmlString);}
return myDocument;},
//Utility.formatText(text)
formatText:function(text)
{
var lines=text.split("\r");
var formatted=lines.join("<"+"br />");
return formatted;},
//Utility.blockUI()
blockUI:function()
{
$display('blockUI');
$('blockUI').style.height=Math.max(PU.getContentHeight(),"1000")+"px";},
//Utility.unblockUI()
unblockUI:function()
{
$nodisplay('blockUI');},
//Utility.exec(text)
exec:function(text)
{
text=text.replace(/\\/g,'\\\\').replace(/"/g,'\\"').replace(/\n/g,"\\n").replace(/\r/g,"\\n");
setTimeout('try { eval("'+text+'"); } catch(ex) { alert(ex.message); }',0);}};
//define the var PU of Utility;
var PU=Utility;

Array.prototype.add=Array.prototype.queue=function(item){this.push(item);}
Array.prototype.addRange=function(items){var length=items.length;if(length!=0){for(var index=0;index<length;index++){this.push(items[index]);}}}
Array.prototype.clear=function(){if(this.length>0){this.splice(0,this.length);}}
Array.prototype.clone=function(){var clonedArray=[];var length=this.length;for(var index=0;index<length;index++){clonedArray[index]=this[index];}
return clonedArray;}
Array.prototype.contains=Array.prototype.exists=function(item){var index=this.indexOf(item);return(index>=0);}
Array.prototype.dequeue=function(){return this.shift();}
if(!Array.prototype.indexOf){Array.prototype.indexOf=function(item,startIndex){var length=this.length;if(length!=0){startIndex=startIndex||0;if(startIndex<0){startIndex=Math.max(0,length+startIndex);}
for(var i=startIndex;i<length;i++){if(this[i]==item){return i;}}}
return-1;}}
if(!Array.prototype.forEach){Array.prototype.forEach=function(fnCb,context){var length=this.length;for(var i=0;i<length;i++){fnCb.call(context,this[i],i,this);}}}
Array.prototype.insert=function(index,item){this.splice(index,0,item);}
Array.prototype.remove=function(item){var index=this.indexOf(item);if(index>=0){this.splice(index,1);}
return(index>=0);}
Array.prototype.removeAt=function(index){this.splice(index,1);}
Array._typeName='Array';
Array.parse=function(value){return eval('('+value+')');}

//StringBuilder
function StringBuilder(initialText) {
	var _parts = new Array();
	if ((typeof (initialText) == "string") && (initialText.length != 0)) {
		_parts.push(initialText);
	}
	this.append = function (text) {
		if ((text == null) || (typeof (text) == "undefined")) {
			return;
		}
		if ((typeof (text) == "string") && (text.length == 0)) {
			return;
		}
		_parts.push(text);
	};
	this.appendLine = function (text) {
		this.append(text);
		_parts.push("\r\n");
	};
	this.clear = function () {
		_parts.clear();
	};
	this.isEmpty = function () {
		return (_parts.length == 0);
	};
	this.toString = function (delimiter) {
		return _parts.join(delimiter || "");
	};
};



//读取cookie 方法
function get_cookie(Name)
 {
	  var search = Name + "="
	  var returnvalue = "";
	  if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search)
		if (offset != -1) { // if cookie exists
		  offset += search.length
		  end = document.cookie.indexOf(";", offset);
		  if (end == -1)
			 end = document.cookie.length;
		  returnvalue=unescape(document.cookie.substring(offset, end))
		  }
	   }
	  return returnvalue;
}

var language;	
try
{
	language= get_cookie("Language");
}
catch(e)
{
	language = "";
}

document.write("<style type='text/css'>");
document.write("#__calendar{margin:0;padding:0;}");
document.write("#calendarTable{width:179px;margin:0;padding:0;border:1px solid #395366;background:#fff;}");
document.write("th,td{margin:0;padding:0px}");
document.write("#calendarTable th ,#calendarTable td{font:12px/18px 宋体,Arial,sans-serif;text-align:center; color:#395366; background:#F9F9FA;}");
document.write("#calendarTable thead th.week{background:#E2E7EF;}");
document.write("#calendarTable thead tr.function th{color:#61801D!important; font-weight:bold;background:#B7C4D4};");
document.write("#calendarTable thead tr.function th a{color:#091150!important; font-weight:bold;}");
document.write("#calendarTable thead tr.function th a:hover{color:#FFF!important; font-weight:bold;}");
document.write("#calendarTable thead tr.top{letter-spacing:1px;}");
document.write("#calendarTable thead a{color:#051350;width:19px;height:18px;text-decoration:none;display:block; }");
document.write("#calendarTable thead a.today{width:100% !important; }");
document.write("#calendarTable thead a.today:hover{width:100% !important;}");
document.write("#calendarTable td{width:21px;height:16px;color:#fff;}");
document.write("#calendarTable th{height:21px;}");
document.write("#calendarTable tr.com{background:#ffffff;}");
document.write("#calendarTable tr.cur{background:#7B9E2D;}");
document.write("#calendarTable tbody a{margin-left:2px;margin-right:2px;color:#395366;width:22px;height:16px;text-decoration:none;display:block;  }");
document.write("#calendarTable tbody a:hover{color:#091150;width:22px;height:16px;text-decoration:none;background:#B7C4D4;display:block;}");
document.write("#calendarTable tbody a.today{color:#fff;background:#395366;}");
document.write("#calendarTable tbody a.today:hover{color:#091150;background:#B7C4D4;}");
document.write("#calendarTable tbody a.week{color:#c00; }");
document.write("</style>");
document.write("<div id='__calendar' style='position:absolute;display:none;z-index:50;'></div>");
if (language == "en"){
   document.write("<table cellspacing=\"0\" cellpadding=\"0\" id=\"calendarTable\"><thead><tr class=\"top\"><th> </th><th colspan=\"5\" id=\"sohwdate\"></th><th><a href=\"javascript:void(0);\" title=\"close\" onclick=\"shut()\">×</a></th></tr><tr class=\"function\"><th><a href=\"javascript:void(0);\" title=\"To the previous year\" onclick=\"preYear()\"><<</a></th><th><a href=\"javascript:void(0);\" title=\"To the previous month\" onclick=\"preMonth()\"><</a></th><th colspan=\"3\"><a href=\"javascript:void(0);\" class=\"today\" title=\"today\" onclick=\"getDate('0')\">today</a></th><th><a href=\"javascript:void(0);\" title=\"To the next month\" onclick=\"nextMonth()\">></a></th><th><a href=\"javascript:void(0);\" title=\"To the next year\" onclick=\"nextYear()\">>></a></th></tr><tr><th class=\"week\">Sun</th><th class=\"week\">Mon</th><th class=\"week\">Tue</th><th class=\"week\">Wed</th><th class=\"week\">Thu</th><th class=\"week\">Fri</th><th class=\"week\">Sat</th></thead><tbody id=\"calendarTbody\"></tbody></table>");
}
else if (language == "zhbig5"){
   document.write("<table cellspacing=\"0\" cellpadding=\"0\" id=\"calendarTable\"><thead><tr class=\"top\"><th> </th><th colspan=\"5\" id=\"sohwdate\"></th><th><a href=\"javascript:void(0);\" title=\"關閉\" onclick=\"shut()\">×</a></th></tr><tr class=\"function\"><th><a href=\"javascript:void(0);\" title=\"向前翻1年\" onclick=\"preYear()\"><<</a></th><th><a href=\"javascript:void(0);\" title=\"向前翻1月\" onclick=\"preMonth()\"><</a></th><th colspan=\"3\"><a href=\"javascript:void(0);\" class=\"today\" title=\"今天\" onclick=\"getDate('0')\">今天</a></th><th><a href=\"javascript:void(0);\" title=\"向後翻1月\" onclick=\"nextMonth()\">></a></th><th><a href=\"javascript:void(0);\" title=\"向後翻1年\" onclick=\"nextYear()\">>></a></th></tr><tr><th class=\"week\">日</th><th class=\"week\">一</th><th class=\"week\">二</th><th class=\"week\">三</th><th class=\"week\">四</th><th class=\"week\">五</th><th class=\"week\">六</th></thead><tbody id=\"calendarTbody\"></tbody></table>");
}
else{
   document.write("<table cellspacing=\"0\" cellpadding=\"0\" id=\"calendarTable\"><thead><tr class=\"top\"><th> </th><th colspan=\"5\" id=\"sohwdate\"></th><th><a href=\"javascript:void(0);\" title=\"关闭\" onclick=\"shut()\">×</a></th></tr><tr class=\"function\"><th><a href=\"javascript:void(0);\" title=\"向前翻1年\" onclick=\"preYear()\"><<</a></th><th><a href=\"javascript:void(0);\" title=\"向前翻1月\" onclick=\"preMonth()\"><</a></th><th colspan=\"3\"><a href=\"javascript:void(0);\" class=\"today\" title=\"今天\" onclick=\"getDate('0')\">今天</a></th><th><a href=\"javascript:void(0);\" title=\"向后翻1月\" onclick=\"nextMonth()\">></a></th><th><a href=\"javascript:void(0);\" title=\"向后翻1年\" onclick=\"nextYear()\">>></a></th></tr><tr><th class=\"week\">日</th><th class=\"week\">一</th><th class=\"week\">二</th><th class=\"week\">三</th><th class=\"week\">四</th><th class=\"week\">五</th><th class=\"week\">六</th></thead><tbody id=\"calendarTbody\"></tbody></table>");
}





var objouter;
var objInput;
var isShow = true;
//var ableClose=false
objouter=document.getElementById("__calendar"); 
var calendarTable = document.getElementById("calendarTable");
objouter.appendChild(calendarTable);

function setday(obj)
{
	objInput = obj;
	writeDate();
	sohwDate();	
//	if(document.all)
//	{
//		event.returnValue=false;
//		event.cacelBubble=true;
//	}
//  objouter.style.top =getAbsoluteHeight(objInput)+getAbsoluteTop(objInput);
//	objouter.style.left =getAbsoluteLeft(objInput);
    var pos = PU.getPosition(objInput);
    objouter.style.top = pos[1]+pos[3] + "px";
    objouter.style.left = pos[0]+"px";
	objouter.style.display = "block";
	//setTimeout("ableClose=true;",800);
}

function setdaylm(obj,btSearch)
{
	objInput = obj;
	writeDate();
	sohwDate();	
//	if(document.all)
//	{
//		event.returnValue=false;
//		event.cacelBubble=true;
//	}
//  objouter.style.top =getAbsoluteHeight(objInput)+getAbsoluteTop(objInput);
//	objouter.style.left =getAbsoluteLeft(objInput);
    var pos = PU.getPosition(objInput);
    objouter.style.top = pos[1]+pos[3] + "px";
    objouter.style.left = pos[0]+"px";
	objouter.style.display = "block";
	//setTimeout("ableClose=true;",800);
	SearchBtn = btSearch;
	//btSearch.click();
}

function getAbsoluteHeight(ob){return ob.offsetHeight;}
function getAbsoluteWidth(ob){return ob.offsetWidth;}
function getAbsoluteLeft(ob){var s_el=0;el=ob;while(el){s_el=s_el+el.offsetLeft;el=el.offsetParent;}; return s_el}
function getAbsoluteTop(ob){var s_el=0;el=ob;while(el){s_el=s_el+el.offsetTop ;el=el.offsetParent;}; return s_el}



var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31); 
var toDay = new Date();
var tempYear = toDay.getFullYear();
var tempMonth = toDay.getMonth();
var tbody = document.getElementById("calendarTbody"); 
var sohwId = document.getElementById("sohwdate");
function getDays(month, year)
{    
   if (1 == month) return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28; 
   else return daysInMonth[month]; 
} 

function writeDate() {    
   var curCal = new Date(tempYear,tempMonth ,1);
   var startDay = curCal.getDay();
   var daily = 0;
   var    today = toDay.getDate();
   if(tempYear != toDay.getFullYear() || tempMonth != toDay.getMonth()) today = -1;
   var todayStyle = "";
   var weekEndStyle = "";
   clear();
   var intDaysInMonth =getDays(curCal.getMonth(), curCal.getFullYear());
   var weeks = (intDaysInMonth + startDay) % 7 == 0 ? (intDaysInMonth + startDay) / 7 : parseInt((intDaysInMonth + startDay ) / 7) + 1;    

   for (var intWeek = 1; intWeek <= weeks; intWeek++){ 
       var tr = document.createElement("tr");
       tr.className='com';
       tr.setAttribute("onmouseover","javascript:this.className='cur'");        
       tr.setAttribute("onmouseout","javascript:this.className='com'");
       tr.onmouseover = function (){this.className = "cur";}
       tr.onmouseout = function (){this.className = "com";}
       for (var intDay = 0;intDay < 7;intDay++){            
           var td = document.createElement("td");
           if ((intDay == startDay) && (0 == daily)) 
               daily = 1; 
               
           if(today == daily)
               todayStyle="today";
           
           if (intDay == 6 || intDay == 0) weekEndStyle = "week" ;
           
           if ((daily > 0) && (daily <= intDaysInMonth)) 
           { 
               if (language == "en"){
                   td.innerHTML = "<a href=\"javascript:void(0);\" class=\""+ weekEndStyle + todayStyle +"\" onclick=\"getDate('"+daily+"')\" title=\""+eval(tempMonth+1)+"-"+daily+"\">" + daily + "</a>";
               }
               else if (language == "zhbig5"){
                   td.innerHTML = "<a href=\"javascript:void(0);\" class=\""+ weekEndStyle + todayStyle +"\" onclick=\"getDate('"+daily+"')\" title=\""+eval(tempMonth+1)+"月"+daily+"日\">" + daily + "</a>";
               }
               else{
                   td.innerHTML = "<a href=\"javascript:void(0);\" class=\""+ weekEndStyle + todayStyle +"\" onclick=\"getDate('"+daily+"')\" title=\""+eval(tempMonth+1)+"月"+daily+"日\">" + daily + "</a>";
               }                  
               todayStyle = "";
               weekEndStyle = "";
               daily++; 
           }else{ 
               td.innerHTML = " "; 
               todayStyle = "";
               weekEndStyle = "";
           }            
           tr.appendChild(td);            
       }
       tbody.appendChild(tr);
   } 
}
function getDate(day){
   var year , month ,date;
   if(day == "0"){
       year = toDay.getFullYear();
       month = eval(toDay.getMonth()+1) < 10 ? "0"+eval(toDay.getMonth()+1) : eval(toDay.getMonth()+1);
       date = toDay.getDate() < 10 ? "0"+toDay.getDate() : toDay.getDate();
   }else{
       year = tempYear;
       month = eval(tempMonth+1) < 10 ? "0"+eval(tempMonth+1) : eval(tempMonth+1);
       date = day < 10 ? "0"+day : day;        
   }
   objInput.value = year + "-" + month +"-"+ date;
   if(SearchBtn != null)
   {
        SearchBtn.click();
   }
   //close();
}
function sohwDate(){
   if (language == "en"){
           sohwId.innerHTML = tempYear + "-" + eval(tempMonth+1);
       }
       else if (language == "zhbig5"){
           sohwId.innerHTML = tempYear + "年" + eval(tempMonth+1) +"月";
       }
       else{
           sohwId.innerHTML = tempYear + "年" + eval(tempMonth+1) +"月";
       }        
}
function preYear(){
   isShow = false;
   if(tempYear > 999 && tempYear < 10000){
       tempYear--;
   }else{
           if (language == "en"){
                alert("The year is out of extent(1000-9999)!");
           }
           else if (language == "zhbig5"){
                alert("年份超出範圍（1000-9999）！");
           }
           else{
                alert("年份超出范围（1000-9999）！");
           }     
   }
   sohwDate();
   writeDate();
}
function nextYear(){
   isShow = false;
   if(tempYear > 999 && tempYear < 10000){
       tempYear++;
   }else{
       if (language == "en"){
            alert("The year is out of extent(1000-9999)!");
       }
       else if (language == "zhbig5"){
            alert("年份超出範圍（1000-9999）！");
       }
       else{
            alert("年份超出范围（1000-9999）！");
       }     
   }
   sohwDate();
   writeDate();
}

function preMonth(){
   isShow = false;
   if(tempMonth >= 1){tempMonth--}else{tempYear--;tempMonth = 11;}
   sohwDate();
   writeDate();
}
function nextMonth(){
   isShow = false;
   if(tempMonth == 11){tempYear++;tempMonth = 0}else{tempMonth++}
   sohwDate();
   writeDate();
}
function clear(){
   var nodes = tbody.childNodes;
   var nodesNum = nodes.length; 
   for(var i=nodesNum-1;i>=0;i--) { 
       tbody.removeChild(nodes[i]); 
   }
}
function shut(){
   close();
}
function close(){
   tempYear = toDay.getFullYear();
   tempMonth = toDay.getMonth();
   objouter.style.display = "none"
   objouter.style.top = 0;
   objouter.style.left = 0;
}
function vent(event){
   if(document.all){
       if(isShow){
           if (window.event.srcElement != objouter && window.event.srcElement.tagName != 'IMG') close();
           isShow = true;
		   //ableClose=false
           return;
       }
       isShow = true;        
   }else{
       if(isShow){
       	//alert(event.target.tagName);
           if(event.target != objouter && event.target.tagName != 'IMG'&& event.target.tagName != 'A') close();
           isShow = true;
		   //ableClose=false;
		   return;
       }
       isShow = true;
   }
}
document.onclick = vent;