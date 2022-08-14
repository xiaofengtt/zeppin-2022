/**
*查询框工具1.0 add by caiyuan 2007-01-16
*/
var queryDisplayFlag1 = false;
var queryCondition1,queryButton1;
var moveFlag1=false,movedFlag1=false;	//层移动表示和层已经被移动标志
var ox1,oy1;

function initQueryCondition1()
{	
	queryCondition1 = document.getElementById("queryCondition1");
	if(queryCondition1==null)
		sl_alert("该页面不存在查询条件控件！");
	else
	{
		queryButton1 = document.getElementById("queryButton1");
		if(queryButton1==null)
		{
			sl_alert("该页面不存在增强型查询按钮！");
		}
		else
		{			
			queryCondition1.style.top = queryButton1.offsetTop+50;	
			var left = queryButton1.offsetLeft+queryButton1.offsetParent.offsetLeft-parseInt(queryCondition1.style.width)-10;		
			queryCondition1.style.left = (left<0)?0:left;

			queryButton1.onmouseover=showQueryCondition1;
			queryButton1.onmouseout=hideQueryCondition1;
			queryButton1.onclick=enableQuery1;
						
			document.onmouseup=function(){moveFlag1=false;}		
		}	
	}	

}

function enableQuery1()
{	
	if(queryDisplayFlag1)
	{
		queryDisplayFlag1 = false;	
		queryCondition1.style.display = "none";
		closeCalendar();
	}
	else
	{
		queryDisplayFlag1 = true;	
		queryCondition1.style.display = "";			
	}	
}

function cancelQuery1()
{	
	queryCondition1.style.display = "none";	
	closeCalendar();
	queryDisplayFlag1 = false;
}

function showQueryCondition1()
{	
	if(queryCondition1.style.display != "")
	{
		queryCondition1.style.display = "";
				
	}		
}


function hideQueryCondition1()
{
	if(!queryDisplayFlag1)
	{
		queryCondition1.style.display = "none";
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
	if(obj.id=="qcTitle1")
	{		
		if(!moveFlag1)
		{
			ox1 = queryCondition1.offsetLeft - event.x;
			oy1 =queryCondition1.offsetTop - event.y;	
			moveFlag1 = true;		
		}
	}	
}
	
document.onmousemove=function()
{
	if(moveFlag1)
	{
		queryCondition1.style.left = event.x + ox1;
		queryCondition1.style.top = event.y + oy1;
	}
}

//如果界面上有日期控件，还要将这个控件关掉
function closeCalendar()
{
	var calendar = document.getElementById("divCalendarpad");	
	if(calendar!=null)
		calendar.style.display="none";
}