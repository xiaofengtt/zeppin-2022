/**
*��ѯ�򹤾�1.0 add by caiyuan 2007-01-16
*/
var queryDisplayFlag2 = false;
var queryCondition2,queryButton2;
var moveFlag2=false,movedFlag2=false;	//���ƶ���ʾ�Ͳ��Ѿ����ƶ���־
var ox2,oy2;

function initQueryCondition2()
{	
	queryCondition2 = document.getElementById("queryCondition2");
	if(queryCondition2==null)
		sl_alert("��ҳ�治���ڲ�ѯ�����ؼ���");
	else
	{
		queryButton2 = document.getElementById("queryButton2");
		if(queryButton2==null)
		{
			sl_alert("��ҳ�治������ǿ�Ͳ�ѯ��ť��");
		}
		else
		{			
			queryCondition2.style.top = queryButton2.offsetTop+5;	
			var left = queryButton2.offsetLeft+queryButton2.offsetParent.offsetLeft-parseInt(queryCondition2.style.width)-10;		
			queryCondition2.style.left = (left<0)?0:left;

			queryButton2.onmouseover=showQueryCondition2;
			queryButton2.onmouseout=hideQueryCondition2;
			queryButton2.onclick=enableQuery2;
						
			document.onmouseup=function(){moveFlag2=false;}		
		}	
	}	

}

function enableQuery2()
{	
	if(queryDisplayFlag2)
	{
		queryDisplayFlag2 = false;	
		queryCondition2.style.display = "none";
		closeCalendar();
	}
	else
	{
		queryDisplayFlag2 = true;	
		queryCondition2.style.display = "";			
	}	
}

function cancelQuery2()
{	
	queryCondition2.style.display = "none";	
	closeCalendar();
	queryDisplayFlag2 = false;
}

function showQueryCondition2()
{	
	if(queryCondition2.style.display != "")
	{
		queryCondition2.style.display = "";
				
	}		
}


function hideQueryCondition2()
{
	if(!queryDisplayFlag2)
	{
		queryCondition2.style.display = "none";
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
	if(obj.id=="qcTitle2")
	{		
		if(!moveFlag2)
		{
			ox2 = queryCondition2.offsetLeft - event.x;
			oy2 =queryCondition2.offsetTop - event.y;	
			moveFlag2 = true;		
		}
	}	
}
	
document.onmousemove=function()
{
	if(moveFlag2)
	{
		queryCondition2.style.left = event.x + ox2;
		queryCondition2.style.top = event.y + oy2;
	}
}

//��������������ڿؼ�����Ҫ������ؼ��ص�
function closeCalendar()
{
	var calendar = document.getElementById("divCalendarpad");	
	if(calendar!=null)
		calendar.style.display="none";
}