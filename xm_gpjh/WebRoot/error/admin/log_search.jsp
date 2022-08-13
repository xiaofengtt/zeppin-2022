<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="../js/calendar/calendar-win2000.css">
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
<title></title>
</head>
<body>
<form action="story/log_searchexe.html" method=post>
	<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center>
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">用户名：</td>
			<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=userid size=50></td>
		</tr>
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">行为：</td>
			<td bgcolor="#D4E4EB" class="zhengwen"><input type=text name=behavior size=50></td>
		</tr>
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">起始时间：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
			 <input type="text"
							class="input" name="from_date" id="from_date" value="" size="8" readonly> <img
							src="images/img.gif" width="20" height="14"
							id="f_trigger_b"
							style="border: 1px solid #551896; cursor: pointer;"
							title="Date selector"
							onmouseover="this.style.background='white';"
							onmouseout="this.style.background=''">&nbsp;
			  <select name="from_hour">
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
			  <select name="from_minute">
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
			  <select name="from_second">
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
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">结束时间：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
			<input type="text"
							class="input" name="to_date" id="to_date" value="" size="8" readonly> <img
							src="images/img.gif" width="20" height="14"
							id="f_trigger_c"
							style="border: 1px solid #551896; cursor: pointer;"
							title="Date selector"
							onmouseover="this.style.background='white';"
							onmouseout="this.style.background=''">&nbsp;
			<select name="to_hour">
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
			  <select name="to_minute">
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
			  <select name="to_second">
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
		
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">操作结果：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
				<SELECT name="status">
					<OPTION value="">全部</OPTION>
					<OPTION value="true">成功</OPTION>
					<OPTION value="false">失败</OPTION>
				</SELECT>
			</td>
		</tr>
		
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">类别：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
				<SELECT name="logtype">
					<OPTION value="">全部</OPTION>
					<%
						//List typeList = LogType.getTypeList();
						//for(int i=0; i<typeList.size(); i++) {
						//	String type = (String)typeList.get(i);
						//	String type_str = LogType.typeShow(type);
					%>
				<!-- 	<OPTION value=""></OPTION> -->
					<%
					//	}
					%>
				</SELECT>
			</td>
		</tr>
				
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">是否查询历史记录表：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
				<INPUT type="radio" name="history" value="1">是&nbsp;
				<INPUT type="radio" name="history" value="0" checked>否
			</td>
		</tr>
		
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen">级别：</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
				<SELECT name="priority">
					<OPTION value="">全部</OPTION>
					<%
						//List priorityList = LogPriority.getPriorityList();
						//for(int i=0; i<priorityList.size(); i++) {
						//	String priority = (String)priorityList.get(i);
						//	String priority_str = LogPriority.priorityShow(priority);
					%>
				<!-- 	<OPTION value=""></OPTION> -->
					<%
						//}
					%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan=2 align="center">
			<input type="submit" value="提交">
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "from_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    
    Calendar.setup({
        inputField     :    "to_date",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>
</body>
</html>