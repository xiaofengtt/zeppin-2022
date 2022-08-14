/**
*��ѯ�򹤾�1.0 add by caiyuan 2007-01-16   
*/ 

if(!document.getElementsByClassName){
document.getElementsByClassName = function(className, element){
var children = (element || document).getElementsByTagName('*');
var elements = new Array();
for (var i=0; i<children.length; i++){
var child = children[i];
var classNames = child.className.split(' ');
for (var j=0; j<classNames.length; j++){
if (classNames[j] == className){ 
elements.push(child);
break;
}
}
} 
return elements;
};
}

var queryDisplayFlag = false;
var queryCondition,queryButton;
var getBg;
var moveFlag=false,movedFlag=false;	//���ƶ���ʾ�Ͳ��Ѿ����ƶ���־   
var ox,oy;

function initQueryCondition()
{	
	queryCondition = document.getElementById("queryCondition");
	
	if(queryCondition==null)
	    sl_alert("��ҳ�治���ڲ�ѯ�����ؼ���");
	else
	{	
		afterBg("#queryCondition");
		document.getElementById("qcTitle").style.width = 100 + "%";
		queryButton = document.getElementById("queryButton");
		
		//alert(document.getElementById("queryButton").value);
		if(queryButton==null)
		{
			sl_alert("��ҳ�治������ǿ�Ͳ�ѯ��ť��");
		}else{			
			queryCondition.style.top = queryButton.offsetTop+65;	
			var left = queryButton.offsetLeft+queryButton.offsetParent.offsetLeft-parseInt(queryCondition.style.width)-10;		
//			queryCondition.style.left = (left<0)?0:left;

			queryButton.onmouseover=showQueryCondition;
			queryButton.onmouseout=hideQueryCondition;
			queryButton.onclick=enableQuery;
						
			document.onmouseup=function(){moveFlag=false;}		
		}	
	}	

}

function enableQuery()
{	
	if(queryDisplayFlag)
	{
		queryDisplayFlag = false;	
		queryCondition.style.display = "none";
		getBg.style.display = "none";
		closeCalendar();
	}
	else
	{
		queryDisplayFlag = true;	
		queryCondition.style.display = "";
		getBg.style.display = "block";
	}	
}

function cancelQuery()
{	
	getBg.style.display = "none";
	queryCondition.style.display = "none";
	closeCalendar();
	queryDisplayFlag = false;
}

function showQueryCondition()
{

	if(queryCondition.style.display != "")
	{
		queryCondition.style.display = "";				
	}		
}


function hideQueryCondition()
{
	if(!queryDisplayFlag)
	{
		queryCondition.style.display = "none";
		closeCalendar();			
	}		
}



document.onmousedown=function() 
{	
	var obj = event.srcElement;
	if(obj.tagName.toUpperCase()!="TD")
		return ;
	obj = obj.parentElement;	
	if(obj.tagName.toUpperCase()!="TR")	
		return ;
	obj = obj.parentElement;		
 	if(obj.tagName.toUpperCase()!="TBODY")	
		return ;
	obj = obj.parentElement;
	if(obj.id=="qcTitle")
	{		
		if(!moveFlag)
		{
			ox = queryCondition.offsetLeft - event.x;
			oy = queryCondition.offsetTop - event.y;	
			moveFlag = true;
		}
	}	
}
	
document.onmousemove=function()
{
	if(moveFlag)
	{
		queryCondition.style.left = event.x + ox;
		queryCondition.style.top = event.y + oy;
	}
}

//��������������ڿؼ�����Ҫ������ؼ��ص�
function closeCalendar()
{
	var calendar = document.getElementById("divCalendarpad");	
	if(calendar!=null)
		calendar.style.display="none";
}


function cancelQuery1(obj)
{	
	obj.style.display = "none";	
	closeCalendar();
	queryDisplayFlag = false;
}

function afterBg(){
	var body = document.getElementsByTagName("body")[0];
	var bg = document.createElement("div");
	bg.className = "after-bg";
	body.appendChild(bg);
	getBg = document.getElementsByClassName("after-bg")[0];
	
	getBg.style.width = body.offsetWidth + "px";
	getBg.style.height = body.offsetHeight + "px";
	
}