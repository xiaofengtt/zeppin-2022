<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改在线考试</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="/js/calendar/calendar-win2000.css">
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
<script language="javascript" src="/entity/function/js/check.js">
</script>
<script>
	function userAddGuarding()
	{	
		
		var startArr = document.courseForm.start_date.value.split("-");
		var endArr = document.courseForm.end_date.value.split("-");
		var startHour = document.courseForm.start_hour[document.courseForm.start_hour.selectedIndex].value;
		var startMinute = document.courseForm.start_minute[document.courseForm.start_minute.selectedIndex].value;
		var startSecond = document.courseForm.start_second[document.courseForm.start_second.selectedIndex].value;
		var endHour = document.courseForm.end_hour[document.courseForm.end_hour.selectedIndex].value;
		var endMinute = document.courseForm.end_minute[document.courseForm.end_minute.selectedIndex].value;
		var endSecond = document.courseForm.end_second[document.courseForm.end_second.selectedIndex].value;
		var startDate = new Date(startArr[0],startArr[1],startArr[2],startHour,startMinute,startSecond);
		var endDate = new Date(endArr[0],endArr[1],endArr[2],endHour,endMinute,endSecond);
		if(startDate > endDate) {
			alert("开始时间不能比结束时间晚！");
			return false;
		}
		
		return true;
	}
	
	function checkLength(obj,len){
		if(obj.value.length > (len)){
			alert('此字段最多只能输入'+len+'个字符');
			obj.value = obj.value.substr(0,len);
			return false;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<form name="courseForm" action="/entity/studyZone/courseResources_doEditTest.action" method="post" onsubmit="return userAddGuarding();">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<input type="hidden" name="course_id" value="<s:property value='course_id'/>">
<input type="hidden" name="test_id" value="<s:property value='test_id'/>">
<input type="hidden" name="onlineType" value="<s:property value='onlineType'/>"/>
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/images/wt_02.gif" width="78" height="52"></td>
                             <s:if test="roleCode == 3">	
                            	<td width="100" valign="top" class="text3" style="padding-top:25px">编辑考试</td>
                            </s:if>
                            <s:else>
                                <td width="100" valign="top" class="text3" style="padding-top:25px">查看考试</td>
                            </s:else>
                            <td background="/entity/function/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="/entity/function/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="/entity/function/images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="/entity/function/images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="/entity/function/images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="/entity/function/images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3" valign="top">标题:</td>
		                                  <td valign="top"><input name="test_title" type="text" size="30" maxlength="50" value="<s:property value="onlineTest.title"/>"></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">开始时间:</td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="start_date" id="start_date" value="<s:property value='start_date'/>" size="8" readonly> <img
											src="/entity/function/images/img.gif" width="20" height="14"
											id="f_trigger_b"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="start_hour">
											  	<option value="<s:property value='start_hour'/>" selected ><s:property value='start_hour'/></option>
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="start_minute">
											  <option value="<s:property value='start_minute'/>" selected ><s:property value='start_minute'/></option>
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="start_second">
											  	<option value="<s:property value='start_second'/>" selected ><s:property value='start_second'/></option>
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
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
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3">结束时间:</td>
		                                  <td class="text1">
		                                  <input type="text"
											class="input" name="end_date" id="end_date" value="<s:property value='end_date'/>" size="8" readonly> <img
											src="/entity/function/images/img.gif" width="20" height="14"
											id="f_trigger_c"
											style="border: 1px solid #551896; cursor: pointer;"
											title="Date selector"
											onmouseover="this.style.background='white';"
											onmouseout="this.style.background=''">&nbsp;
											  <select name="end_hour">
											  	<option value="<s:property value='end_hour'/>" selected ><s:property value='end_hour'/></option>
											  	<%
											  		for(int i=0; i<24; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 时
											  <select name="end_minute">
											  	<option value="<s:property value='end_minute'/>" selected ><s:property value='end_minute'/></option>
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
												<%
													}
												%>
											  </select> 分
											  <select name="end_second">
											  	<option value="<s:property value='end_second'/>" selected ><s:property value='end_second'/></option>
											  	<%
											  		for(int i=0; i<60; i++) {
											  			String str = String.valueOf(i);
											  			if(i < 10)
											  				str = "0" + str;
											  	%>
												<option value="<%=str%>"><%=str%></option>
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
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3" valign="top">说明:</td>
		                                  <td valign="top"><textarea name="note" rows="8" cols="40" onkeyup="checkLength(this,1000);"><s:property value="onlineTest.note"/></textarea></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="bottom"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="bottom">客观题是否自动判卷:</td>
		                                  <td valign="middle" class="text1">
		                                  	<INPUT type="radio" name="isAutoCheck" value="1" <s:if test="onlineTest.isautocheck == 1">checked</s:if> />是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="isAutoCheck" value="0" <s:if test="onlineTest.isautocheck == 0">checked</s:if> />否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="bottom"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="bottom">答题完毕后是否显示答案:</td>
		                                  <td valign="middle" class="text1">
		                                  	<INPUT type="radio" name="isHiddenAnswer" value="1" <s:if test="onlineTest.ishiddenanswer == 1">checked</s:if> />是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="isHiddenAnswer" value="0" <s:if test="onlineTest.ishiddenanswer == 0">checked</s:if> />否
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="bottom"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="100" class="text3" valign="bottom">是否激活:</td>
		                                  <td valign="middle" class="text1">
		                                  	<INPUT type="radio" name="status" value="1" <s:if test="onlineTest.status == 1">checked</s:if> />是&nbsp;&nbsp;&nbsp;
		                                  	<INPUT type="radio" name="status" value="0" <s:if test="onlineTest.status == 0">checked</s:if> />否
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
				                          <td width="100" class="text3"></td>
		                                  <td align="center"><input type="submit" <s:if test="roleCode != 3">disabled=true</s:if> value="提交">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返回" onclick="window.location='/entity/studyZone/courseResources_onlineTest.action?onlineType=<s:property value='onlineType'/>&course_id=<s:property value='course_id'/>'"/></td>	
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="/entity/function/images/wt_06.gif" width="604" height="11"></td>
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
