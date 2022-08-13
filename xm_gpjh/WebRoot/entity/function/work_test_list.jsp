<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="java.util.*,java.net.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page
	import="com.whaty.platform.database.oracle.MyResultSet" />
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>

<html>
	<title>学生作业成绩查询</title>
	<link href="../css/css2.css" rel="stylesheet" type="text/css">
	<script language=javascript src="../manager/js/check.js"></script>

	<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			return "";
		}
		return str;
	}%>

	<%
//		String user_id = new String(request.getParameter("studentId").getBytes(), "GBK");
		String user_id = URLDecoder.decode(request.getParameter("studentId"),"utf-8");
		String id = request.getParameter("id");
		List workmsgList = new ArrayList();
		List uploadList = new ArrayList();
		try {
			dbpool db = new dbpool();
			String sql = "select a.id as id , b.title as title ,user_id,testpaper_id, to_char(test_date,'yyyy-mm-dd') as test_date,test_result,score from test_homeworkpaper_history a,test_homeworkpaper_info b where a.testpaper_id=b.id and a.user_id like'%"
			+ id + "%'";

			MyResultSet rs = null;

			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("title"));
				list.add(rs.getString("score"));
				workmsgList.add(list);
			}
			db.close(rs);
			
			db = new dbpool();
			String sql2 = "select t2.title,t1.score from homework_history t1, homework_info t2 where t1.homework_id = t2.id and t1.user_id = '"+id+"'";
			
			rs = null;
			rs = db.executeQuery(sql2);
			while (rs!=null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("title"));
				list.add(rs.getString("score"));
				uploadList.add(list);
			}
			db.close(rs);
	%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>生殖健康咨询师培训网</title>
		<link href="./css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body leftmargin="0" topmargin="0" background="./images/bg2.gif">
		<br>


		<table width='50%' align="center" cellpadding='1' cellspacing='0'
			class='list'>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="150" valign="top" class="text3"
								style="padding-top:25px">
								<%=user_id%>
								作业成绩表
							</td>
							<td background="./images/wt_03.gif">
								&nbsp;
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr valign="top">
			<td>
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr align="center" bgcolor="#4582B1">
						<td height=25px width="10%" align="center" class="title">
							作业题目（在线作业）
						</td>
						<td height=25px width="10%" align="center" class="title">
							作业成绩
						</td>

					</tr>
					<%
					if (workmsgList.size() < 1) {
					%>
					<td align="center" class="td2" colspan="2">
						该学生到目前为止还没做过在线作业！
					</td>
					<%
					}
					%>
					<%
								for (int i = 0; i < workmsgList.size(); i++) {
								List hat = (List) workmsgList.get(i);
								String className = "oddrowbg1";
								if (i % 2 == 0)
							className = "oddrowbg1";
								else
							className = "oddrowbg2";
					%>
					<tr class='<%=className%>'>



						<td align="center" class="td2">
							<%=hat.get(0)==null?"":hat.get(0)%>&nbsp;
						</td>
						<td align="center" class="td2">
							<%=hat.get(1)==null?"":hat.get(1)%>&nbsp;
						</td>

					</tr>
					<%
					}
					%>

					<tr align="center">
						<td height=25px colspan="6" class="td">


						</td>
					</tr>

					<tr>
						<td colspan=11>

						</td>
					</tr>
				</table>
			</td>
			</tr>
			<tr>
			<td valign="top">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr align="center" bgcolor="#4582B1">
						<td height=25px width="10%" align="center" class="title">
							作业题目（上传作业）
						</td>
						<td height=25px width="10%" align="center" class="title">
							作业成绩
						</td>

					</tr>
					<%
					if (uploadList.size() < 1) {
					%>
					<td align="center" class="td2" colspan="2">
						该学生到目前为止还没做过在线作业！
					</td>
					<%
					}
					%>
					<%
								for (int i = 0; i < uploadList.size(); i++) {
								List hat = (List) uploadList.get(i);
								String className = "oddrowbg1";
								if (i % 2 == 0)
							className = "oddrowbg1";
								else
							className = "oddrowbg2";
					%>
					<tr class='<%=className%>'>



						<td align="center" class="td2">
							<%=hat.get(0)==null?"":hat.get(0)%>&nbsp;
						</td>
						<td align="center" class="td2">
							<%=hat.get(1)==null?"":hat.get(1)%>&nbsp;
						</td>

					</tr>
					<%
					}
					%>

					<tr align="center">
						<td height=25px colspan="6" class="td">


						</td>
					</tr>

					<tr>
						<td colspan=11>

						</td>
					</tr>
				</table>
			</td>
			</tr>
		</table>
	</body>
	<%
			} catch (Exception e) {
			out.print(e.getMessage());
			return;
		}
	%>
</html>
<script>

</script>
