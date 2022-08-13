<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.sso.web.action.SsoConstant"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<link href="images/css/admincss.css" rel="stylesheet" type="text/css" />
</head>

<body style="margin:0">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
		<tr>
			<td height="34" width="100%" style="background-image:url(images/images/g_04.jpg); background-repeat:repeat-x;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="padding-top:3px;">
	     			<tr>
	       				<td width="250" align="center" class="topTimes" id="topTimeBox">
	       					<script type="text/javascript">
								<!--
								var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
								var today=new Date();
								var date;
								function initArray(){
									this.length=initArray.arguments.length;
									for(var i=0;i<this.length;i++){
										this[i+1]=initArray.arguments[i];
									}
								}
								date = today.getYear()+"年"+(today.getMonth()+1)+"月"+today.getDate()+"日 "+d[today.getDay()+1];
								function showtime(){
									if (!document.layers&&!document.all)
										return;
									var mydate=new Date();
									var hours=mydate.getHours();
									var minutes=mydate.getMinutes();
									var dn=" AM";
									if (hours>12){
										dn=" PM";
										hours=hours-12;
									}
									if (hours==0)
										hours=12;
									if (minutes<=9)
										minutes="0"+minutes;
									//change font size here to your desire           
									myclock="  "+hours+":"+minutes+dn;
									if (document.layers){
										document.layers.liveclock.document.write(myclock);
										document.layers.liveclock.document.close();
									}else if (document.all){
										document.getElementById("topTimeBox").innerText = date+myclock;
									}
								}
								showtime();
								setInterval("showtime()",5000);
								//-->
	         				</script>
	          			</td>
	          			<td width="150" align="left" class="topTimes">
	          				平台访问总次数: <%=session.getAttribute("whole_sumLogin") %>
	          			</td>
	        			<td width="470" align="left" class="topTimes">
	        				<span style="font-size:15px;font-weight:bold">欢迎</span>
	        				<SPAN style="font-size:14px;font-weight:bold;color:#bb3333"><%=((UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY)).getUserName()%></SPAN>
	        				<span style="font-size:15px;font-weight:bold">登录国培计划项目管理系统</span>
	        			</td>
	        			<td align="right">
	        				<table width="200" border="0" cellspacing="0" cellpadding="0">
	          					<tr>
	            					<td width="44%" class="menuTitle" align="right">显示方式:</td>
	            					<td width="56%" align="left">
	            						<table width="60" border="0" cellpadding="0" cellspacing="0">
	              							<tr align="center"> 
								                <td>
								                	<img src="images/images/g_12.jpg" alt="两分工作区" width="19" height="16" class="modelImgNormal" onclick="top.modelB(this)" />
								                </td>
								                <td>
								                	<img src="images/images/g_14.jpg" alt="三分工作区" width="19" height="16" class="modelImgNormal" onclick="top.modelA(this)" />
								                </td>
	              							</tr>
	            						</table>
	            					</td>
	          					</tr>
	        				</table>
						</td>
	      			</tr>
	    		</table>
	    	</td>
		</tr>
	</table>
</body>
</html>
