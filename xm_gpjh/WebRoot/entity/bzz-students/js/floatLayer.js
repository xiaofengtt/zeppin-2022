document.write("<div id='img' style='position:absolute;'>");
document.write("<table width='180' height='100' border='0' cellspacing='0' cellpadding='0' onmouseOver=stopL() onmouseOut=startL()>");
document.write("<tr>");
document.write("<td id=ly height='80' align='center' background=''>");
document.write("<table width='180' height='72' border='0' cellpadding='0' cellspacing='0'>");
document.write("<tr>");
document.write("<td height='20' align='right' class='9pp'><span width='56' height='20' style='cursor:hand' onclick='hideLayer()'>关闭&nbsp;&nbsp;</span></td>");
document.write("</tr>");
document.write("<tr>");
document.write("<td width='180' style='cursor:hand' onclick=\'getUrl()\' id='contentBox' align='center' class='floatText'>ϵͳ</td>");
document.write("</tr>");
document.write("</table>");
document.write("<table>");
document.write("<tr>");
document.write("<td width='180' height='16' style='cursor:hand' onclick=\'getUrl()\' align='center'>&nbsp;</td>");
document.write("</tr>");
document.write("</table>");
document.write("</td>");
document.write("</tr>");
document.write("</table>");
document.write("</div>");
document.getElementById("img").style.visibility = "hidden";
///////////////
var contentText;
var pointUrl;
var bgUrl = "/entity/bzz-students/images/floatLayerBg.gif";
///////////////
var xPos = 20;
var yPos = 0;
var step = 1;
var delay = 10;
var height = 0;
var Hoffset = 0;
var Woffset = 0;
var yon = 0;
var xon = 0;
var pause = true;
var interval;

function changePos() {
	width = document.body.clientWidth;
	height = document.body.clientHeight;
	Hoffset = img.offsetHeight;
	Woffset = img.offsetWidth;
	img.style.left = xPos + document.body.scrollLeft;
	img.style.top = yPos + document.body.scrollTop;
	if (yon) {
		yPos = yPos + step;
	}
	else {
		yPos = yPos - step;
	}
	if (yPos < 0) {
		yon = 1;
		yPos = 0;
	}	
	if (yPos >= (height - Hoffset)) {
		yon = 0;
		yPos = (height - Hoffset);
	}
	if (xon) {
		xPos = xPos + step;
	}
	else {
		xPos = xPos - step;
	}
	if (xPos < 0) {
		xon = 1;
		xPos = 0;
	}
	if (xPos >= (width - Woffset)) {
		xon = 0;
		xPos = (width - Woffset);
	}
}
function www_helpor_net(content,url) {
	yPos = document.body.clientHeight;
	img.style.top = yPos;
	document.getElementById("contentBox").innerHTML = content;
	pointUrl = url;
	document.getElementById("ly").background = bgUrl;
	document.getElementById("img").style.visibility = "visible";
	interval = setInterval('changePos()', delay);
}
function hideLayer(){
	clearInterval(interval);
	document.getElementById("img").style.visibility = "hidden";
}
function getUrl(){
	window.open(pointUrl,"","");
}
function stopL(){
	clearInterval(interval);
}
function startL(){
	interval = setInterval('changePos()', delay);	
}