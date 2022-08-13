//////////////////////
var ua=navigator.userAgent;
var ie=navigator.appName=="Microsoft Internet Explorer"?true:false;
var IEversion=parseFloat(ua.substring(ua.indexOf("MSIE ")+5,ua.indexOf(";",ua.indexOf("MSIE "))));
//////////////////////
var systemTitle = "华南师范大学教学教务平台";
var systemShortTitle = "管理员工作室";
var maxStatus = false;
var tipInfoLeftA = "收缩菜单";
var tipInfoLeftB = "展开菜单";
var tipInfoMaxA = "最大化工作区域";
var tipInfoMaxB = "恢复正常布局";
//var defaultHelpInfo = "欢迎使用中国石油大学现代远程教育教学教务管理平台！鼠标移动到图标上，本区就会出现相应的帮助信息！";
var menuAreaNormalOpacity = 62;
var menuAreaMaxOpacity = 98;
//////////////////////
/*
	show menu
*/
var isMenuThere = false;
function showMenu()
{
	if(isMenuThere!=true)
	{
		isMenuThere = true;
		modelA(tmpModelObjA);
	}
}
/*
	Contral title change with client workplace width
*/
function showTitle()
{
	var tempObj;
	if(topBanner.document.getElementById("title"))
	{
		tempObj = topBanner.document.getElementById("title");
	}
	else
	{
		return;
	}
	if(tempObj.scrollWidth>=160)
	{
		tempObj.innerText = SystemTitle;
	}
	else if(tempObj.scrollWidth<160 && tempObj.scrollWidth>50)
	{
		tempObj.innerText = systemShortTitle;
	}
	else
	{
		tempObj.innerText = " ";
	}
}
/*
	show name of currentPage in the titleBox
	name:String is passed from menu.js
*/
function showPageName(str,url){
	if(top.frames["title"].document.getElementById("titleTextBox")&& url!="#")
	{
		top.frames["title"].document.getElementById("titleTextBox").innerText = str;
	}
	else
	{
		return;
	}
}
/*
	menu area show & hide contral
*/
function showHideMenu(){
	if(top.frames["content"].document.getElementById("MCContral"))
		var tempO = top.frames["content"].document.getElementById("MCContral");
	else
		return;
	if(top.frames["TC"].cols=="0,*")
	{
		top.frames["TC"].cols="213,*";
		tempO.src = "../images/images/split_buttonOut.jpg";
		tempO.title=tipInfoLeftA;
	}
	else{
		top.frames["TC"].cols="0,*";
		tempO.src = "../images/images/split_buttonIn.jpg";
		tempO.title=tipInfoLeftB;
	}
}
/*
	logoBar area show & hide contral
*/
function maxWin(){
	if(top.frames["content"].document.getElementById("MCContral"))
		var tempLeft = top.frames["content"].document.getElementById("MCContral");
	else
			eturn;
	if(top.frames["title"].document.getElementById("maxContral"))
		var tempMax = top.frame	["title"].document.getElementById("maxContral");
	else
		return;
	if(maxStatus == false)
	{
		maxStatus = true;
		tempMax.src = "../images/title_maxButton2.gif";
		tempMax.title=tipInfoMaxB;
		top.frames["TCB"].rows="13,*,48";
		if(top.frames["CC"].cols=="213,*")
		{
			top.frames["CC"].cols="8,*";
			tempLeft.src = "../images/content_menucontralButtonB.gif";
			tempLeft.title=tipInfoLeftB;
		}
	}
	else
	{
		maxStatus = false;
		tempMax.src = "../images/title_maxButton.gif";
		tempMax.title=tipInfoMaxA;
		top.frames["TCB"].rows="108,*,48";
		if(top.frames["CC"].cols=="8,*")
		{
			top.frames["CC"].cols="213,*";
			tempLeft.src = "../images/content_menucontralButtonA.gif";
			tempLeft.title=tipInfoLeftA;
		}
	}
}
/* min help area */
function minHelpBox(){
	if(top.frames["TH"] && top.frames["help"] && top.frames["help"].document.getElementById("sizeC"))
	{
		var tempO = top.frames["help"].document.getElementById("sizeC");
		if(top.frames["TH"].rows == "*,152")
		{
			top.frames["TH"].rows = "*,30";
			tempO.title = "恢复帮助";
			tempO.src = "../images/help_buttonMax.gif";
		}
		else
		{
			top.frames["TH"].rows = "*,152";
			tempO.title = "最小化帮助";
			tempO.src = "../images/help_buttonMin.gif";
		}
	}
	else
	{
		return;
	}
}
/* model contral*/
var tmpModelObjA;
var tmpModelObjB;
var tmpModelObjC;
function registerObj(obj)
{
	tmpModelObjA = obj;
}
function modelA(obj)
{
	//alert(obj);
	tmpModelObjA = obj;
	clearModelImg();
	if(top.frames["content"] && top.frames["content"].document.getElementById("MCContral"))
		var tempO = top.frames["content"].document.getElementById("MCContral");
	else
		return;
	if(top.frames["TC"])
	{
		top.frames["TC"].cols="213,*";
	}
	tempO.src = "../images/images/split_buttonOut.jpg";
	tempO.title=tipInfoLeftA;
	if(top.frames["MC"])
	{
		top.frames["MC"].rows="47,*";
	}

	if (top.frames["TCB"]) //zlb
	{
		top.frames["TCB"].rows="62,34,*,38";
	}
	tmpModelObjA.className = "modelImgSelect";

/*	if (isF11 == true) {	//zlb
		var WshShell = new ActiveXObject('WScript.Shell');
		WshShell.SendKeys('{F11}');
		isF11 = false;
	}
	*/
}
function modelB(obj){
	tmpModelObjB = obj;
	clearModelImg();
	if(top.frames["content"] && top.frames["content"].document.getElementById("MCContral"))
		var tempO = top.frames["content"].document.getElementById("MCContral");
	else
		return;
	if(top.frames["TC"])
	{
		//top.frames["TC"].cols="47,*";   //原始大小 gaoyuan changed 1204
		top.frames["TC"].cols="0,*"; 
	}
	tempO.src = "../images/images/split_buttonOut.jpg";
	tempO.title=tipInfoLeftA;
	if(top.frames["MC"])
	{
		//top.frames["MC"].rows="47,*";   //原始大小 gaoyuan changed 1204
		top.frames["MC"].rows="0,*";
	}

	if (top.frames["TCB"]) //zlb
	{
		top.frames["TCB"].rows="62,34,*,38";
	}
	tmpModelObjB.className = "modelImgSelect";
}
function modelC(obj){
	tmpModelObjC = obj;
	clearModelImg();
	if(top.frames["content"] && top.frames["content"].document.getElementById("MCContral"))
		var tempO = top.frames["content"].document.getElementById("MCContral");
	else
		return;
	if(top.frames["TC"])
	{
		top.frames["TC"].cols="0,*";
	}
	tempO.src = "../images/images/split_buttonOut.jpg";
	tempO.title=tipInfoLeftA;
	/* zlb
	if(top.frames["MC"])
	{
		top.frames["MC"].rows="1,*";
	}
	*/
	
	if (top.frames["TCB"]) //zlb
	{
		top.frames["TCB"].rows="0,34,*,3";
	}
	tmpModelObjC.className = "modelImgSelect";

//	if (isF11 == false) {	//zlb
//		var WshShell = new ActiveXObject('WScript.Shell');
//		WshShell.SendKeys('{F11}');
//		isF11 = true;
//	}
}
function modelD(){
	if(top.frames["MC"])
	{
		top.frames["MC"].rows="0,*";
		isMenuThere  = false;
	}
}
function clearModelImg(){
	if(tmpModelObjA)
	tmpModelObjA.className = "modelImgNormal";
	if(tmpModelObjB)
	tmpModelObjB.className = "modelImgNormal";
	if(tmpModelObjC)
	tmpModelObjC.className = "modelImgNormal";
}
/* open page from bigMenu*/
var tempBigMenuID;
function openPage(url,objID)
{
	if(tempBigMenuID)
	{
		changeBorder(objID);
	}
	else
	{
		if(top.frames["menu"] && top.frames["menu"].document.getElementById(objID))
			top.frames["menu"].document.getElementById(objID).className = "tab_menu1";
	}
	tempBigMenuID = objID;
	top.frames["content"].document.getElementById("contentBox").src = url;
	//window.open(url);
}
function changeBorder(objID)
{
	if(top.frames["menu"] && top.frames["menu"].document.getElementById(objID))
	{
		if(top.frames["menu"].document.getElementById(tempBigMenuID))
			top.frames["menu"].document.getElementById(tempBigMenuID).className  = "tab_menu2";
		top.frames["menu"].document.getElementById(objID).className = "tab_menu1";
	}
	else
	{
		return;
	}
}
/* close current page */
function closeCurrentPage(){
	if(top.frames["content"].document.getElementById("contentBox"))
	{
		top.frames["content"].document.getElementById("contentBox").src = "welcome.htm";
		top.showPageName(systemTitle);
		top.showHelpInfo();
	}
}
/* 
	show model help infomations
	
*/
function showHelpInfo(str){
	if(top.help.document.getElementById("helpInfo"))
		top.help.document.getElementById("helpInfo").innerText = (str==undefined)?defaultHelpInfo:str;
	else
		return;
}
/*
	contral the style of menu area
	setBright used to set the area'opacity to 100 by step
	setGray used to set the area' opacity to 62 by step
*/
var tempO;
var tempItv;
function setBright(obj){
	clearItv();
	tempO = obj;
	if(ie)	tempItv = setInterval(bright,10);
}
function setGray(obj){
	clearItv();
	tempO = obj;
	if(ie)	tempItv = setInterval(gray,10);	
}
function bright(){
	if(tempO.filters[0].opacity >= menuAreaMaxOpacity)
	{
		clearItv();
	}else
	{
		tempO.filters[0].opacity += 2+(100-tempO.filters[0].opacity)/10;
	}
}
function gray(){
	if(tempO.filters[0].opacity <= menuAreaNormalOpacity)
	{
		clearItv();
	}else
	{
		tempO.filters[0].opacity -= (1+tempO.filters[0].opacity/30);
	}
}
function clearItv(){
	clearInterval(tempItv);	
}
/* exit admin system & close window */
function exitSys(){
	if(ie)
	{
		if(IEversion< 5.5){
			var str  = "<object id=noTipClose classid='clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11'>";
			str += "<param name='Command' value='Close'></object>";
			document.body.insertAdjacentHTML("beforeEnd", str);
			document.all.noTipClose.Click();
	    }else{
			window.opener =null;
			window.close();
	    }
	}
	else
	{
	window.close();
	}
}
/////////////
function closeCurWin()
{
	top.frames["fraMain"].closeCurWin();
}

function closeAllWin()
{
	top.frames["fraMain"].closeAllWin();
}
