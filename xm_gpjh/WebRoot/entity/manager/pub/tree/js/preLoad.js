 //////////////////////
var Ximages = new Array();
//////////////////////
var Urls = new Array();
//TODO zlb
Urls[0]="/entity/manager/pub/images/menu1_62.jpg";
Urls[1]="/entity/manager/pub/images/menu_2_62.jpg";
Urls[2]="/entity/manager/pub/images/menu_58.jpg";
Urls[3]="/entity/manager/pub/images/menu_m_58.jpg";
Urls[4]="/entity/manager/pub/images/tab_menu_bg1.jpg";
Urls[5]="/entity/manager/pub/images/tab_menu_bg2.jpg";
//////////////////////
for(var i=0;i<Urls.length;i++){
	Ximages[i] = new Image();
}
//////////////////////
var thisImages;
var I=-1;
var i=0;
var Int;
//////////////////////
var spaceTime = 100;
//////////////////////
function startLoad()
{
	LoadNext();
	Int = setInterval("loadAndShow()",spaceTime);
}
function LoadNext()
{
	if(i==Ximages.length)
	{
		clearInterval(Int);
		document.getElementById("showInf").innerText = "Finished!";
		location.href = "/entity/manager/pub/tree/user/menu.jsp";
	}else{
		if(I!=i){
			Ximages[i].src = Urls[i];
			thisImages = Ximages[i];
			I=i;
			loadAndShow();
		}
	}
}
function loadAndShow()
{
	if(thisImages && thisImages.readyState == "complete")
	{
		//document.getElementById("showImg").innerHTML +="<img src='"+thisImages.src+"'>";
		i++;
		//document.getElementById("showInf").innerText = i+"/"+Ximages.length;
		document.getElementById("showInf").innerText = parseInt((i/Ximages.length)*100)+"%";
		LoadNext();
	}
}