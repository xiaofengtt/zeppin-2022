// JavaScript Document
//ϵ�в˵���������ǡ�����ɸ�����������
var menuStr = "menu";
//Ĭ�ϱ��Ϊon�Ĳ˵������
var preMenu = "0";
////////////////////////////////
////////////////////////////////
function registMenu(obj)
{
	if(preMenu!="")	
	{
		offMenu((typeof(preMenu)=="string")?$(menuStr+""+preMenu):preMenu);
	}
	if(obj.status=="off")
	{
		obj.status="on";
		preMenu = obj;
	}
	try{
	document.getElementById("learn").height="1px";
	}
	catch(e){
	}
}
function overMenu(obj)
{
	setClass(obj,"on");
}
function outMenu(obj)
{
	if(obj.status=="off")
	{
		setClass(obj,"off");
	}
	else
	{
		setClass(obj,"on");
	}
}

function onMenu(obj)
{
	obj.status = "on";
	setClass(obj,"on");
}
function offMenu(obj)
{
	obj.status="off";
	setClass(obj,"off");
}
function setClass(obj,status)
{
	if(obj.className.length>=2)
	{
		if(obj.className.lastIndexOf("-")>=0)
		{
			obj.className = obj.className.substring(0,obj.className.lastIndexOf("-")+1)+status;
		}
	}
}
function $(id)
{
	return document.getElementById(id);
}
window.onload =function()
{
	if($(menuStr+""+preMenu))
	{
		onMenu($(menuStr+""+preMenu));
		preMenu = $(menuStr+""+preMenu);
	}
}
