<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.util.Cookie.*"%>
<%@page import="com.whaty.platform.sso.web.action.InteractionAction"%>
<%@ include file="../../pub/priv.jsp"%>
<%@ include file="../../../student/pub/priv.jsp"%>
<%
	//StudentPriv session_studentPriv = (StudentPriv)(session.getAttribute("eduplatform_priv"));
	//PlatformFactory platformFactory = PlatformFactory.getInstance();
	//StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(session_studentPriv);
//	String seconds = studentOperationManage.getElectiveTimes(teachclass_id,user.getId());
	String seconds = studentOperationManage.getElectiveTimes(courseId,user.getId());

	String first = (String)session.getAttribute("First");
	session.setAttribute("First", "0");
	
	String opencourseId=(String)session.getAttribute("opencourseId");  //学生下进入学习使用，请不要与openCourse混淆
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>help</title>
		<% String path = request.getContextPath();%>
		<link href="<%=path%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	background-color: #6797BB;
}
.style3 {color: #FFFFFF; text-decoration: none; font-family: "黑体", "Arial Black"; font-size: 12px;}
-->
</style>

<script language="JavaScript" src="../js/cookie.js"></script>
<script>
var first = parseInt(<%=first%>);
if(first==1)
	setCookie("TotalTime","0");
//var totalTime = parseInt(getCookie("TotalTime"));
var totalTime = 0;
var totalTimes = <%=seconds %>;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function timer()
{
	totalTime +=1;
	var n = totalTime;
	document.getElementById("second").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minute").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hour").innerHTML = n2(n);
}
function timers()
{
	document.getElementById("timers").style.display = "block";
	var n = totalTimes;
	document.getElementById("seconds").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("minutes").innerHTML = n2(n % 60);
	n = (n - n % 60) / 60;
	document.getElementById("hours").innerHTML = n2(n);
}

function window.onbeforeunload(){
//	setCookie("TotalTime",totalTime);
	top.window.location.reload();
	top.setTime(totalTime);
}
function openware(){
	var xx= window.document.getElementById("combo-box-link").value;
	if(xx.length<=0){
		alert("请选择课件！");
	}else{
		window.open(xx);
	}
}
</script>
<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	List itemList=interactionManage.getTheachItem(courseId);
	String item="";
		String dayi="";
		String gonggao="";
		String taolun="";
		String kaoshi="";
		String zuoye="";
		String ziyuan="";
		String zxdayi="";
	  String smzuoye="";
	  String zice="";
	  String pingjia="";
	  String daohang="";
	  String daoxue="";
	  String shiyan="";
	  String zfx="";
	  String boke="";
	  
	int width = 0;
	if(itemList!=null)
	{
	   for(int i=0;i<itemList.size();i++){
	          CourseItem techItem=(CourseItem) itemList.get(i);
	          gonggao=fixnull(techItem.getGonggao());
	          dayi=fixnull(techItem.getDayi());  
	          taolun=fixnull(techItem.getTaolun());
	          kaoshi=fixnull(techItem.getKaoshi());
	          zuoye=fixnull(techItem.getZuoye());
	          ziyuan=fixnull(techItem.getZiyuan());
	          zxdayi=fixnull(techItem.getZxdayi());
	          smzuoye=fixnull(techItem.getSmzuoye());
	          zice=fixnull(techItem.getZice());
	          pingjia=fixnull(techItem.getPingjia());
	          daoxue=fixnull(techItem.getDaoxue());
	          shiyan=fixnull(techItem.getShiyan());
	          zfx=fixnull(techItem.getZfx());
	          boke=fixnull(techItem.getBoke());
	          taolun=fixnull(techItem.getTaolun());
	  	}
	}				
 %>

</head>
<body leftmargin="0" topmargin="0" onload="timers()">
<table width="212" height="100%" border="0" cellpadding="0" cellspacing="0" onselectstart="return false" >
  <tr class="help_bg"> 
    <td align="center" valign="top" class="help_bg"><table width="100%" height="220" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="150" align="center" valign="top" class="help">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="2" height="16"><img src="../images/k.gif" width="1" height="16"></td>
          </tr>
          <tr>
            <td height="2" align="center" background="../images/help_bt04.gif"><img src="../images/help_bt04.gif" width="2" height="2"></td>
          </tr>
          <%
          	if(ziyuan.equals("1"))
          	{
           %>
<%--          <tr>
            <td height="59" align="center"><a href="../../resource/course_resource.jsp" target="contentBox"><img src="../images/help_bt05.gif" alt="资料下载" width="177" height="48" border="0"></a></td>
          </tr>
--%>          
          <%
          	}
           %>
          <tr>
            <td height="2" align="center" background="../images/help_bt04.gif"><img src="../images/help_bt04.gif" width="2" height="2"></td>
          </tr>
        </table>
          
          <table width="98%" border="0" cellspacing="1" cellpadding="1">
          	
             <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
            <tr>
             <td align=center>
            <a href="<%=path%>/training/student/course/jumpCourseware.jsp?courseId=<%=courseId %>&opencourseId=<%=opencourseId %>" target="_blank"><img src="../images/5.jpg" alt="进入学习" width="178" height="25" border="0"></a></td>
            </tr>
            
             <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
             <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
            <br>
            
            <tr>
              <td height="20" align="center" class="time">您本次学习时长为</td>
            </tr>
            <tr>
              <td height="20" align="center" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="20" align="center" bgcolor="#2A7DB1" class="time">
                  	<div id="timer">
                    	<span id = hour class="time">00</span>
                    	:<span id = minute class="time">00</span>
                    	:<span id = second class="time">00</span>
                    </div>
                  </td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="20" align="center" class="time">您学习总时长为</td>
            </tr>
            <tr>
              <td height="20" align="center" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="20" align="center" bgcolor="#2A7DB1" class="time">
                  	<div id="timers" >
                    	<span id = hours class="time">00</span>
                    	:<span id = minutes class="time">00</span>
                    	:<span id = seconds class="time">00</span>
                    </div>
                  </td>
                </tr>
              </table></td>
            </tr>
            
            <tr>
              <td height="5"><img src="../images/k.gif" width="1" height="5"></td>
            </tr>
            
          </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
