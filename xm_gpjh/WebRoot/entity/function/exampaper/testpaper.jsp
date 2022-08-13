<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*"%>
<%@ page import="com.whaty.util.Cookie.*,com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%
	String id = request.getParameter("id");
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		ExamPaper paper = testManage.getExamPaper(id);
		WhatyCookieManage cookieManage = new WhatyCookieManage();
		String time = paper.getTime();
		String secondstr = cookieManage.getCookieValue(request,"TestTime",null); //(String)session.getAttribute("TestTime");
		long second = 0;
		if(secondstr == null || secondstr.equals("null")|| secondstr.equals(""))
			second = Integer.parseInt(time)*60;
		else
			second = Integer.parseInt(secondstr);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>试题</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
var totalTime = 600;
setInterval("timer()",1000);
function n2(n)
{
 if(n < 10)return "0" + n.toString();
 return n.toString();
}

function initTime(second)
{
	totalTime = second;
	document.getElementById("timer").style.display = "";
}
function timer()
{
	if(totalTime>0)	
	{
		totalTime -=1;
		var n = totalTime;
		document.getElementById("second").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("minute").innerHTML = n2(n % 60);
 		n = (n - n % 60) / 60;
 		document.getElementById("hour").innerHTML = n2(n);
	}
	else 
	{
		commitPaper();
	}
}
function saveTime()
{
	document.cookie="TestTime="+totalTime;
}
function commitPaper()
{
	document.cookie="TestTime=0";
	location.href= "question_infoexe.jsp";
}
</script></head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif" style='overflow:scroll;overflow-x:hidden' onload="initTime('<%=second%>')" onunload = "saveTime()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzc.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="98%" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="20%" background="images/st_01.gif" class="text3" style="padding-left:50px">试&nbsp;&nbsp;题</td>
                            <td width="75%" height="53" background="images/wt_03.gif" align="right" valign="middle">
                            <div id="timer" style="display:none" border="1px #000000 solid">
                            <span id = hour style="font-size:24">00</span>:<span id = minute style="font-size:24">00</span>:<span id = second style="font-size:24">00</span><%--
                            &nbsp;&nbsp;&nbsp;&nbsp;<SPAN valign="bottom"><a href="testpaper_result.jsp?id=<%=id%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="images/tjsj.gif" width="97" height="31" border=0></a></SPAN>
                            --%></div>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td valign="top">
						<table width="97%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td width="36%" valign="top"><iframe align="middle" width="273" height="100%" src="testpaper_left.jsp?id=<%=id%>" frameborder="0" scrolling="auto" marginWidth=0 marginHeight=0 ></iframe></td>
                            <td width="1%">&nbsp;</td>
                            <td width="63%" valign="top"><iframe align="left" name="contentBox" width="100%" height="500" src="testpaper_main.jsp" frameborder="0" scrolling="auto" marginWidth=0 marginHeight=0 ></iframe> </td>
                          </tr>
                          <tr>
                          	<td></td>
                          	<td></td>
                          	<td><SPAN valign="bottom"><a href="testpaper_result.jsp?id=<%=id%>" onclick="javascript:location.replace(this.href);event.returnValue=false;" border=0><img src="images/tjsj.gif" width="97" height="31" border=0></a></SPAN></td>
                          </tr>
                        </table><br>
                      </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</body>
</html>
