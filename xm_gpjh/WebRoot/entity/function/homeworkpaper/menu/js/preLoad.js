//////////////////////
var Ximages = new Array();
//////////////////////
var Urls = new Array();
Urls[0]="../js/tree/close.gif";
Urls[1]="../js/tree/open.gif";
Urls[2]="../js/tree/file.gif";
Urls[3]="../js/tree/list.gif";
Urls[4]="../js/tree/folder1.gif";
Urls[5]="../js/tree/folder2.gif";
Urls[6]="../js/tree/folder3.gif";
Urls[7]="../js/tree/folder4.gif";
Urls[8]="../js/tree/usericon.gif";
Urls[9]="../js/tree/userpower.gif";
Urls[10]="../js/tree/icon-expandall.gif";
Urls[11]="../js/tree/icon-closeall.gif";
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
		location.href = "../user/menu.htm";
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