// JavaScript Document
function exit()
{
	var E = confirm("\n您是否要退出\"课程学习\"模块，\n\n并返回首页？")
	if(E)
	{
		window.opener = null;
		window.close();
	}
}
function XmlDOM() {}
XmlDOM.create = function(URL)
{
	var xhReq = new ActiveXObject("Microsoft.XMLHTTP");
	xhReq.open("get", URL, false);
	xhReq.send(null);
	return xhReq.responseText;
}

//basic start

function returnPar(par,target)
{
	var parTitle;
	if(par) parTitle = par;
	else parTitle = "";
	//
	var _target = (target)?target:document;
	var pageURL = _target.location.search;
	var tempPar = new String();
	var tempParAry = new Array();
	if(pageURL.indexOf("?")!=-1)
	{
		tempPar = pageURL.substring((pageURL.indexOf("?")+1),pageURL.length);
		if(tempPar.indexOf("&")!=-1)
		{
			tempParAry = tempPar.split("&");
		}
		else
		{
			tempParAry[0] = tempPar;
		}
	}
	//
	for(var i=0;i<tempParAry.length;i++)
	{
		if(tempParAry[i].indexOf(parTitle+"=")!=-1)
		{
			return tempParAry[i].substring((tempParAry[i].indexOf(parTitle+"=")+parTitle.length+1),tempParAry[i].length);
		}
	}
	return "";
}

function disVisible(objID)
{
	if($(objID) && $(objID).style)
	{
		$(objID).style.display="none";
	}
}

function visible(objID)
{
	if($(objID) && $(objID).style)
	{
		$(objID).style.display="block";
	}
}

function getHeightOf(objID)
{
	if($(objID) && $(objID).style)
	{
		return parseInt($(objID).style.height);
	}
	else
	{
		return 0;
	}
}

function getWidthOf(objID)
{
	if($(objID) && $(objID).style)
	{
		return parseInt($(objID).style.Width);
	}
	else
	{
		return 0;
	}
}

function $(ID)
{
	if(document.getElementById(ID))
	{
		return document.getElementById(ID);
	}
	else if(parent && parent.document.getElementById(ID))
	{
		return parent.document.getElementById(ID);
	}
	else
	{
		return null;
	}
}
function createMask()
{
	
	var rootEl=document.documentElement||document.body;
	var docHeight=((rootEl.clientHeight>rootEl.scrollHeight)?rootEl.clientHeight:rootEl.scrollHeight)+"px";
	var docWidth=((rootEl.clientWidth>rootEl.scrollWidth)?rootEl.clientWidth:rootEl.scrollWidth)+"px";
	var shieldStyle="position:absolute;top:0px;left:0px;width:"+docWidth+";height:"+docHeight+";background:#000;z-index:1;filter:alpha(opacity=50);";
	var maskDiv= document.createElement("<div id='shield' style=\""+shieldStyle+"\"></div>");
	document.body.appendChild(maskDiv);
}

function removeMask()
{
	if($("shield"))
		document.body.removeChild($("shield"));
}
//////////////////////
function MM_jumpMenu(targ,selObj,restore){ //v3.0
  eval(targ+".location='"+selObj.options[selObj.selectedIndex].value+"'");
  $(targ).focus();
  if (restore) selObj.selectedIndex=0;
}
//////////////////////
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_showHideLayers() { //v6.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }
    obj.visibility=v; }
}