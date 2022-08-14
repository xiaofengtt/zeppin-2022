   
document.write('<div id="divFloatPrompt" style="z-index:10000;position:absolute;visibility:hidden;background-color: #f5f5f5; border: 1px solid Silver;padding:3px;filter:alpha(opacity=80);">'+
                
                '</div>');
var offsetfromcursorX = 12;
var offsetfromcursorY = 10; 

var offsetdivfrompointerX = 10;
var offsetdivfrompointerY = 14;

var ie = document.all;
var ns6 = document.getElementById && !document.all;

//if (ie || ns6)
//    var objdiv = document.all ? document.all["divFloatPrompt"] : document.getElementById ? document.getElementById("divFloatPrompt") : ""

//var pointerobj = document.all ? document.all["dhtmlpointer"] : document.getElementById ? document.getElementById("dhtmlpointer") : ""

//获得鼠标位置 
function mouseCoords(ev)
{
    if(ev.pageX || ev.pageY){
        return {x:ev.pageX, y:ev.pageY};
    }
    return {
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,
        y:ev.clientY + document.body.scrollTop - document.body.clientTop
    };
}
//显示图片
function showFloatImagePrompt(ev,div)
{
	ev = ev || window.event;
	var mousePos = mouseCoords(ev);
	var objdiv = document.getElementById("divFloatPrompt");
	var objContent = document.getElementById("divPromptContent");
	objdiv.style.color = "#000000";
	//objdiv.className = "divFloatPrompt";
	objdiv.innerHTML = div.innerHTML==""?"无附件":div.innerHTML;
	objdiv.style.width = 192 + "px";
	objdiv.style.left = mousePos.x + 1 + "px";
	objdiv.style.top = mousePos.y + 1 + "px";
	objdiv.style.visibility = "visible";
	//设置DIV的位置
	setPosition();
}
function ietruebody() {
    return (document.compatMode && document.compatMode != "BackCompat") ? document.documentElement : document.body
}
//设置DIV的位置
function setPosition()
{
	var nondefaultpos = false

	var objdiv = document.getElementById("divFloatPrompt");
	var curX = (ns6) ? e.pageX : event.clientX + ietruebody().scrollLeft;
	var curY = (ns6) ? e.pageY : event.clientY + ietruebody().scrollTop;
	var winwidth = ie && !window.opera ? ietruebody().clientWidth : window.innerWidth - 20
	var winheight = ie && !window.opera ? ietruebody().clientHeight : window.innerHeight - 20

	var rightedge = ie && !window.opera ? winwidth - event.clientX - offsetfromcursorX : winwidth - e.clientX - offsetfromcursorX
	var bottomedge = ie && !window.opera ? winheight - event.clientY - offsetfromcursorY : winheight - e.clientY - offsetfromcursorY

	var leftedge = (offsetfromcursorX < 0) ? offsetfromcursorX * (-1) : -1000
    if (rightedge < objdiv.offsetWidth) {
		objdiv.style.left = curX - objdiv.offsetWidth + "px"
		nondefaultpos = true
	}
	else if (curX < leftedge)
		objdiv.style.left = "5px"
	else {
		objdiv.style.left = curX + offsetfromcursorX - offsetdivfrompointerX + "px"
	}
	if (bottomedge < objdiv.offsetHeight) {
		objdiv.style.top = curY - objdiv.offsetHeight/* - offsetfromcursorY*/ + "px"
		nondefaultpos = true
	}
	else {
		objdiv.style.top = curY + offsetfromcursorY /*+ offsetdivfrompointerY*/ + "px"
	}
	objdiv.style.visibility = "visible";
}
//隐藏提示信息
function hiddenFloatPrompt()
{	
	var objdiv = document.getElementById("divFloatPrompt");
	objdiv.className = "divFloatPromptHidden";
	objdiv.style.visibility = "hidden";
}
