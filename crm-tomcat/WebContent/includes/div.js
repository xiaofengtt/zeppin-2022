
var divDisplayFlag = false;
var divCondition,divButton;
var moveFlag=false,movedFlag=false;	//���ƶ���ʾ�Ͳ��Ѿ����ƶ���־
var ox,oy;
var activeObj;
var divs = new Array();

function initDiv(divConditionId,divButtonId)
{	
	divCondition = document.getElementById(divConditionId);
	if(divCondition==null)
	    sl_alert("��ҳ�治���ڲ�ѯ�����ؼ���");
	else
	{
		divButton = document.getElementById(divButtonId);
		//alert(document.getElementById("divButton").value);
		if(divButton==null)
		{
			sl_alert("��ҳ�治������ǿ�Ͳ�ѯ��ť��");
		}
		else
		{	
			var temp = "divs['"+divButtonId+"']=document.getElementById('"+divConditionId+"')";
			eval(temp);
			divCondition.style.top = divButton.offsetTop+70;	
			var left = divButton.offsetLeft+divButton.offsetParent.offsetLeft-parseInt(divCondition.style.width)-10;		
			divCondition.style.left = (left<0)?0:left;

			divButton.onmouseover=showdivCondition;
			divButton.onmouseout=hidedivCondition;
			divButton.onclick=enablediv;
						
			document.onmouseup=function(){moveFlag=false;}		
		}	
	}	
}

function enablediv()
{	var obj = event.srcElement;
	activeObj = divs[obj.id];
	if(activeObj!=null){
		if(divDisplayFlag)
		{
			divDisplayFlag = false;	
			activeObj.style.display = "none";
			closeCalendar();
		}
		else
		{
			divDisplayFlag = true;	
			activeObj.style.display = "";			
		}	
	}
}

function cancelDiv()
{	
	if(activeObj!=null){
		activeObj.style.display = "none";	
		closeCalendar();
		divDisplayFlag = false;
	}
}

function showdivCondition()
{	
	if(activeObj!=null){
			divDisplayFlag = false;
			activeObj.style.display = "none";		
	}
	var obj = event.srcElement;
	activeObj = divs[obj.id];
	if(activeObj!=null){
		if(activeObj.style.display != "")
		{
			activeObj.style.display = "";				
		}	
	}	
}


function hidedivCondition()
{	
	var obj = event.srcElement;
	activeObj = divs[obj.id];
	if(activeObj!=null){
		if(!divDisplayFlag)
		{
			activeObj.style.display = "none";
			closeCalendar();			
		}		
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
			if(activeObj!=null){
				ox = activeObj.offsetLeft - event.x;
				oy =activeObj.offsetTop - event.y;	
				moveFlag = true;	
			}	 
		}
	}	
}

document.onmousemove=function()
{
	if(activeObj!=null){
		if(moveFlag)
		{
			activeObj.style.left = event.x + ox;
			activeObj.style.top = event.y + oy;
		}
	}
}

//��������������ڿؼ�����Ҫ������ؼ��ص�
function closeCalendar()
{
	var calendar = document.getElementById("divCalendarpad");	
	if(calendar!=null)
		calendar.style.display="none";
}