<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
			String status = (String)session.getAttribute("mock_login");
			if(status != null && status.equals("1")){ 
		%>
			<script>alert('模拟学生登陆只能查看不能编辑基本信息');window.history.back();</script>
		<%
			}
		%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改作业</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="../../../js/calendar/calendar-win2000.css">
<script type="text/javascript" src="../../../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="../../../js/calendar/calendar-setup.js"></script>
</head>
<%
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	TestManage testManage = interactionManage.creatTestManage();
	HomeworkPaper homeworkPaper = testManage.getHomeworkPaper(id);
	String startDate = homeworkPaper.getStartDate();
	String start_date = startDate.substring(0,10);
	String start_hour = startDate.substring(11,13);
	String start_minute = startDate.substring(14,16);
	String start_second = startDate.substring(17,19);
	String endDate = homeworkPaper.getEndDate();
	String end_date = endDate.substring(0,10);
	String end_hour = endDate.substring(11,13);
	String end_minute = endDate.substring(14,16);
	String end_second = endDate.substring(17,19);
	String selected = "";
	
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
%>
<script>
	function del(){
		if(confirm("是否确定删除？"))
			window.navigate("homeworkpaper_delete.jsp?id=<%=homeworkPaper.getId()%>&pageInt=<%=pageInt%>");
	}
</script>
<script>
	function subCheck()
	{
		if(document.paperForm.title.value == "")
		{
			alert("请填写标题！");
			return false;
		}
		if(document.paperForm.start_date.value == "")
		{
			alert("请选择开始时间！");
			return false;
		}
		if(document.paperForm.end_date.value == "")
		{
			alert("请选择结束时间！");
			return false;
		}
		
		var startArr = document.paperForm.start_date.value.split("-");
		var endArr = document.paperForm.end_date.value.split("-");
		var startHour = document.paperForm.start_hour[document.paperForm.start_hour.selectedIndex].value;
		var startMinute = document.paperForm.start_minute[document.paperForm.start_minute.selectedIndex].value;
		var startSecond = document.paperForm.start_second[document.paperForm.start_second.selectedIndex].value;
		var endHour = document.paperForm.end_hour[document.paperForm.end_hour.selectedIndex].value;
		var endMinute = document.paperForm.end_minute[document.paperForm.end_minute.selectedIndex].value;
		var endSecond = document.paperForm.end_second[document.paperForm.end_second.selectedIndex].value;
		var startDate = new Date(startArr[0],startArr[1],startArr[2],startHour,startMinute,startSecond);
		var endDate = new Date(endArr[0],endArr[1],endArr[2],endHour,endMinute,endSecond);
		//alert("开始时间：" + startDate + "<br>结束时间：" + endDate);
		if(startDate > endDate) {
			alert("开始时间不能比结束时间晚！");
			return false;
		}
		return true
		
	}
</script>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<form name="paperForm" action="homeworkpaper_editexe.jsp" method="post" onsubmit="return subCheck()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zxzy.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <%if(us.getRoleId().equals("3")){ %>
                            <td width="120" valign="top" class="text3" style="padding-top:25px">修改作业基本信息</td>
                            <%}
                               else { %>
                             <td width="120" valign="top" class="text3" style="padding-top:25px">查看作业基本信息</td>
                             <%} %>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">*标题:</td>
		                                  <td valign="top"><input name="title" type="text" size="30" maxlength="50" value="<%=homeworkPaper.getTitle()%>"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">*开始时间:</td>
				                          <td class="text1"><input type="text"
											class="input" name="start_date" id="start_date" value="<%=start_date%>" size="8" readonly> <img
											src="images/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="start_hour">
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(start_hour))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="start_minute">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(start_minute))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="start_second">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(start_second))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 秒
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3">*结束时间:</td>
		                                  <td class="text1"><input type="text"
											class="input" name="end_date" id="end_date" value="<%=end_date%>" size="8" readonly> <img
											src="images/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="end_hour">
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_hour))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="end_minute">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_minute))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="end_second">
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  			if(str.equals(end_second))
											  				selected = "selected";
											  			else
											  				selected = "";
											  	%>
												<option value="<%=str%>" <%=selected%>><%=str%></option>
												<%
													}
												%>
											  </select> 秒
										  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
    <%! String fixnull(String str) {
		if (str == null || str.equals("null"))
			str = "";
		return str;
	}
	String selected(String s1, String s2)
	{
		if(s1 != null && s1.equals(s2))
			return "selected";
		else
			return "";
	}
	
	%>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><textarea name="note" rows="10" cols="50"><%= fixnull(homeworkPaper.getNote())%></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">*是否激活:</td>
		                                  <td class="text1" valign="top">
		                                  	<INPUT type="radio" name="status" value="1" <%if(homeworkPaper.getStatus().equals("1"))out.print("checked");%>>是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <%if(homeworkPaper.getStatus().equals("0"))out.print("checked");%>>否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
                          		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                 <td width="20" valign="top"><img src="images/ggzt.gif" width="20" height="32"></td>
				                          <td width="80" class="text3" valign="top">*生成作业:</td>
		                                  <td class="text1" valign="top">
		                              	   <!--  <INPUT type="radio" name="types" value="1" <%if(homeworkPaper.getType().equals("1"))out.print("checked"); %>>随机题目&nbsp;&nbsp;&nbsp; -->
		                                  	<INPUT type="radio" name="types" value="0" <%if(homeworkPaper.getType().equals("0"))out.print("checked");%> >指定题目
           									</td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"></td>
				                          <td width="80" class="text3"></td>
		                                   <%if(us.getRoleId().equals("3")){ %>
		                                  <td>
		                                  <input type="hidden" name="id" value="<%=homeworkPaper.getId()%>">
		                                  <input type="hidden" name="pageInt" value="<%=pageInt%>">
		                                  
		                                  <input type="submit" value="修改">
		                                  <!-- input type="button" value="删除" onclick=del();-->
		                                  </td>	
		                                  <%} %>	                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><a href="homeworkpaper_list.jsp?pageInt=<%=pageInt%>" class="tj">[返回]</a> </td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</form>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "start_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
    Calendar.setup({
        inputField     :    "end_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
</body>
</html>
