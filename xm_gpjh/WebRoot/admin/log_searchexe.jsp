<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.whaty.platform.util.*,com.whaty.platform.logmanage.*"%>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
	String userid = request.getParameter("userid");
	String behavior = request.getParameter("behavior");
	String operateStartTime = "";
	String from_hour = "";
	String from_minute = "";
	String from_second = "";
	String from_date = request.getParameter("from_date");
	if (from_date != null && from_date.length() > 0) {
		from_hour = request.getParameter("from_hour");
		from_minute = request.getParameter("from_minute");
		from_second = request.getParameter("from_second");
		operateStartTime = from_date + " " + from_hour + ":"
		+ from_minute + ":" + from_second;
	}

	String operateEndtime = "";
	String to_hour = "";
	String to_minute = "";
	String to_second = "";
	String to_date = request.getParameter("to_date");
	if (to_date != null && to_date.length() > 0) {
		to_hour = request.getParameter("to_hour");
		to_minute = request.getParameter("to_minute");
		to_second = request.getParameter("to_second");
		operateEndtime = to_date + " " + to_hour + ":" + to_minute
		+ ":" + to_second;
	}

	String status = request.getParameter("status");
	String logtype = request.getParameter("logtype");
	String priority = request.getParameter("priority");
	String history_str = request.getParameter("history");
	boolean history = history_str.equals("1") ? true : false;

	LogFactory factory = LogFactory.getInstance();
	LogManage logManage = factory.creatLogManage();
%>
<html>
	<head>
		<link href="css.css" rel="stylesheet" type="text/css">
		<title></title>
		<script>
	function doSubmit() {
		if(confirm("此操作将删除原表中的记录并备份到历史记录表，确定导入吗？")) {
			document.forms["import"].submit();
		}
	}
</script>
	</head>
	<body>
		<%
		if (!history) {
		%>
		<form name="import" action="log_backup.jsp" method=post>
			<INPUT type="hidden" name="userid" value="<%=userid%>">
			<INPUT type="hidden" name="behavior" value="<%=behavior%>">
			<INPUT type="hidden" name="operateStartTime"
				value="<%=operateStartTime%>">
			<INPUT type="hidden" name="operateEndtime"
				value="<%=operateEndtime%>">
			<INPUT type="hidden" name="status" value="">
			<INPUT type="hidden" name="logtype" value="<%=logtype%>">
			<INPUT type="hidden" name="priority" value="<%=priority%>">
			<%--	<INPUT type="button" value="导入历史表" onclick="doSubmit()">--%>
		</form>
		<%
		}
		%>
		<table cellPadding=2 cellSpacing=1 border="0" bgcolor=#3F6C61
			align=center>
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen" width="10%" align="center">
					用户名
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="5%" align="center">
					行为
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="20%" align="center">
					操作时间
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="10%" align="center">
					状态
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="40%" align="center">
					说明
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="10%" align="center">
					身份
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen" width="5%" align="center">
					日志级别
				</td>
			</tr>
			<%
						int totalItems = logManage.getLogEntitiesNum(null, userid,
						operateStartTime, operateEndtime, behavior, status, null,
						logtype, priority, history);
				//----------分页开始---------------
				String spagesize = (String) session.getValue("pagesize");
				String spageInt = request.getParameter("pageInt");
				Page pageover = new Page();
				pageover.setTotalnum(totalItems);
				pageover.setPageSize(spagesize);
				pageover.setPageInt(spageInt);
				int pageNext = pageover.getPageNext();
				int pageLast = pageover.getPagePre();
				int maxPage = pageover.getMaxPage();
				int pageInt = pageover.getPageInt();
				int pagesize = pageover.getPageSize();
				String link = "&userid=" + userid + "&behavior=" + behavior
						+ "&from_date=" + from_date + "&from_hour=" + from_hour
						+ "&from_minute=" + from_minute + "&from_second="
						+ from_second + "&to_date=" + to_date + "&to_hour="
						+ to_hour + "&to_minute=" + to_minute + "&to_second="
						+ to_second + "&status=" + status + "&logtype=" + logtype
						+ "&priority=" + priority + "&history=" + history_str;
				//----------分页结束---------------
				List logEntityList = logManage.getLogEntities(pageover, null,
						userid, operateStartTime, operateEndtime, behavior, status,
						null, logtype, priority, history);

				for (int i = 0; i < logEntityList.size(); i++) {
					LogEntity logEntity = (LogEntity) logEntityList.get(i);
			%>
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getUserid()) %>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getBehavior())%>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getOperate_time()) %>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getStatus()) %>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getNotes()) %>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull(logEntity.getLogtype()) %>
				</td>
				<td bgcolor="#D4E4EB" class="zhengwen">
					<%=fixnull( logEntity.getPriority())%>
				</td>
			</tr>
			<%
			}
			%>

		</table>
		<BR>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<img src="images/bottomtop.gif" width="100%" height="7">
				</td>
			</tr>
			<tr>
				<td background="images/bottom02.gif" width="50%">
					<%@ include file="pub/dividepage.jsp"%>
				</td>
			</tr>
			<tr>
				<td>
					<img src="images/bottom03.gif" width="100%" height="7">
				</td>
			</tr>
		</table>
	</body>
</html>
