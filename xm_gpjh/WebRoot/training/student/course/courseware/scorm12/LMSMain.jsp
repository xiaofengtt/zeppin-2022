<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant,com.whaty.platform.sso.web.servlet.UserSession"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%
String path1 = request.getContextPath();
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
UserSession userSession = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
if(userSession==null)
{
%>
<script type="text/javascript">
	alert("请您登录后再学习课件！");
	window.location="<%=basePath1%>";
</script>
<%
	return;
}

String user_id=request.getParameter("user_id");
String course_id=request.getParameter("course_id");
String start=request.getParameter("start");
session.setAttribute("USERID",user_id);

//加入课件导航；
String navigate = request.getParameter("navigate");

boolean iscourseware_nav = false;
if(ScormConstant.SCORM_NAVIGATE_PLATFORM.equals(navigate)){
	iscourseware_nav = false;
}else if(ScormConstant.SCORM_NAVIGATE_COURSEWARE.equals(navigate)){
	iscourseware_nav = true;
}

%>
<html>
<head>
<meta http-equiv="expires" content="Tue, 20 Aug 1999 01:00:00 GMT">
<meta http-equiv="Pragma" content="no-cache">
<title>生殖健康咨询师网络培训课堂</title>
<script language ="JAVASCRIPT" >
var API = null;
function initAPI()
{
   //API = window.LMSFrame.document.APIAdapter;
 
}
     var req;
		var url="request11.jsp?url=1";
		
		function donothing()
		{
			
		}
		
		function sendreq()
		{
		 	
			 var date = new Date();
		     var str ="&date="+date.toString();
			if(window.XMLHttpRequest) 
			{
				req=new XMLHttpRequest();
			}
			else if(window.ActiveXObject)
			{
				req=new ActiveXObject("Microsoft.XMLHttp");
			} 
			if(req) 
			{
				req.open("GET",url+str,false); 
				req.onreadystatechange =donothing; 
				req.send(null); 
			}		
			
			
		}
		setInterval(sendreq,5000*12*18);//每隔18分钟就请求一次防止SESSION过期
		if(!window.opener)
			window.location="<%=request.getContextPath()%>";
</script>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String updataScomDataUrl = basePath + "servlet/OffLineLMSCMIServlet?command=updatescorm&message=";
 %>
<script type="text/javascript">
//用于课件导航时课件向平台设置当前的session
//扩展scorm 功能的对象； 在精品课程中使用；由课件主动查找对象，并调用方法； modify by lwx 20090520
var request;
function createRequest() {
  try {
    request = new XMLHttpRequest();
  } catch (trymicrosoft) {
    try {
      request = new ActiveXObject("Msxml2.XMLHTTP");
    } catch (othermicrosoft) {
      try {
        request = new ActiveXObject("Microsoft.XMLHTTP");
      } catch (failed) {
        request = false;
      }
    }
  }
  if (!request)
    alert("Error initializing XMLHttpRequest!");
}

function sendData(dataUrl) {
     var url = dataUrl;
     request.open("GET", url, false);
     request.onreadystatechange = getScormData;
     request.send(null);
   }
   function getScormData() {
      if (request.readyState == 4)
       if (request.status == 200){
       }
       else if (request.status == 404)
         alert("Request URL does not exist");
       else
         alert("Error: status code is " + request.status);
   }
function saveSCOID(SCOID){
	 var d =  Math.random();
	var _command = "?d="+d+"&command=setSCOID&SCOID="+SCOID;//+"&param="+dmeID+"&param="+dmeValue;
     createRequest();
     var dataUrl = '/servlet/lmscmijs' + _command;
     sendData(dataUrl);
}

var whatyExtAPI = new Object;
	//当前的scoID,
	whatyExtAPI.scoID="";
	whatyExtAPI.setSCOID =function(scoID)
	{
		this.scoID = scoID;
		saveSCOID(scoID);
		return true;
	}
	// 获得课件同步数据的地址
	whatyExtAPI.getDataUrl = function(){
		return '<%=updataScomDataUrl%>';
	}




var startDate;
function startTimer()
{
   startDate = new Date().getTime();
}
/*******************************************************************************
** this function will convert seconds into hours, minutes, and seconds in
** CMITimespan type format - HHHH:MM:SS.SS (Hours has a max of 4 digits &
** Min of 2 digits
*******************************************************************************/
function convertTotalSeconds(ts)
{
   var sec = (ts % 60);

   ts -= sec;
   var tmp = (ts % 3600);  //# of seconds in the total # of minutes
   ts -= tmp;              //# of seconds in the total # of hours

   // convert seconds to conform to CMITimespan type (e.g. SS.00)
   sec = Math.round(sec*100)/100;
   
   var strSec = new String(sec);
   var strWholeSec = strSec;
   var strFractionSec = "";

   if (strSec.indexOf(".") != -1)
   {
      strWholeSec =  strSec.substring(0, strSec.indexOf("."));
      strFractionSec = strSec.substring(strSec.indexOf(".")+1, strSec.length);
   }
   
   if (strWholeSec.length < 2)
   {
      strWholeSec = "0" + strWholeSec;
   }
   strSec = strWholeSec;
   
   if (strFractionSec.length)
   {
      strSec = strSec+ "." + strFractionSec;
   }


   if ((ts % 3600) != 0 )
      var hour = 0;
   else var hour = (ts / 3600);
   if ( (tmp % 60) != 0 )
      var min = 0;
   else var min = (tmp / 60);

   if ((new String(hour)).length < 2)
      hour = "0"+hour;
   if ((new String(min)).length < 2)
      min = "0"+min;

   var rtnVal = hour+":"+min+":"+strSec;

   return rtnVal;
}


function computeTime()
{
   if ( startDate != 0 )
   {
      var currentDate = new Date().getTime();
      var elapsedSeconds = ( (currentDate - startDate) / 1000 );
      var formattedTime = convertTotalSeconds( elapsedSeconds );
   }
   else
   {
      formattedTime = "00:00:00.0";
   }

  return formattedTime;
}
function unloadCourse()
{

}
function init()
{
	startTimer();
}
function sendMsg()
{ 
	//if(window.LMSFrame.sendTime)
	//{
	 alert('LMSMain.jsp-->sendMsg();');
		var oXml = org.cote.js.xml.getXml("submitTime.jsp?course_id=<%=course_id%>&student_id=<%=user_id%>&learn_time="+computeTime());
	 
	//}
	 //window.LMSFun.sendTime('<%=course_id%>',"<%=user_id%>",computeTime());
}
  function   document.onkeydown()   
  {   
	  if   ((event.ctrlKey)&&(event.keyCode==78))       //屏蔽   Ctrl+n   
			event.returnValue=false;   
  }
  
  //限制Flash型课件和精品课件不能关闭开始学习页面
 if(<%=iscourseware_nav%>)
	{
  window.onbeforeunload = function()
  {
  		return "提示：刷新或关闭本页面将导致开始学习后学习状态无法正常记录！";
  }
  }
  
  	window.moveTo(0,0);//窗口最大化
	window.resizeTo(screen.availWidth,screen.availHeight);//窗口最大化
</script>

<script type="text/javascript">
		function loadseqPage(){
			if(window.frames["LMSFrame"].document.readyState=="complete"){
				//var obj = window.frames['LMSMain'];
				document.getElementById('Content').src = 'sequencingEngine.jsp?courseID=<%=course_id%>&start=<%=start%>';
			}
		}
	</script>
</head>
		<%if(iscourseware_nav){//课件导航
			%>
			<frameset rows="0,*" ONLOAD="initAPI()"  frameborder="0" noresize>
        <frame id="LMSFrame" name="LMSFrame" src="top.jsp" frameborder="0" noresize onload="loadseqPage();">
        <frameset id="LMSMain" cols="0,*"  frameborder="0">
            <frameset rows="0,*" frameborder="0" >
               <frame id="code" src="code.jsp?course_id=<%=course_id %>" name="code" frameborder="0">
               <frame src="menu_empty.htm" name="menu" frameborder="0">
            </frameset>
            <frame id="Content" name="Content" src="" frameborder="0">
        </frameset>
        <frame id="setSession" name="setSession" src="set_session.jsp" frameborder="0" noresize width="0"> <!-- 用于课件导航时课件向平台设置当前scoId -->
</frameset>
			<%
		}else{//平台导航
			%>
 <frameset rows="65,*" ONLOAD="initAPI();"   frameborder="0" noresize scroll=auto>
        <frame id="LMSFrame" name="LMSFrame" src="top.jsp" frameborder="0" noresize onload="loadseqPage();">
        <frameset id="LMSMain" cols="160,*" frameborder="0" noresize  scroll=auto>
            <frameset rows="0,*" frameborder="0" >
               <frame id="code" src="code.jsp?course_id=<%=course_id %>"  name="code" frameborder="0"  >
               <frame src="menu_empty.htm" name="menu" frameborder="0" noresize scroll=auto>
            </frameset>
            <frame id="Content" name="Content" src="" frameborder="0">
        </frameset>
        <frame id="setSession" name="setSession" src="" frameborder="0" noresize width="0">
</frameset>
			<%
		}
		%>
<body>

</body>
</html>
