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
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>
<%@ include file="./pub/commonfuction.jsp"%>


<%
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String name = fixnull(request.getParameter("name"));
	String stureg_no = fixnull(request.getParameter("stureg_no"));
	String batch_id = fixnull(request.getParameter("batch_id"));

	String sql = "";
	String sql_name = "";
	String sql_reg = "";
	String sql_batch = "";

	if (!name.equals(""))
		sql_name = " and bs.true_name like '%" + name.trim() + "%'";
	if (!stureg_no.equals(""))
		sql_reg = " and bs.reg_no like '%" + stureg_no.trim() + "%'";
	if (!batch_id.equals(""))
		sql_batch = " and bb.id='" + batch_id.trim() + "'";
	sql = "select se.id         as id," + " se.score_exam as score,"
			+ " bs.true_name       as name,"
			+ " bs.reg_no     as reg_no," + " bs.email      as email,"
			+ " bs.gender     as gender," + " bs.folk       as folk,"
			+ " bs.position   as position,"
			+ " bs.department as department,"
			+ " bs.title      as title,"
			+ " bb.name       as batch_name,"
			+ " pe.name       as enterprise_name"
			+ " from pe_bzz_student          bs,"
			+ " pe_bzz_batch            bb,"
			+ " pe_enterprise           pe,"
			+ " pr_bzz_tch_stu_elective se,"
			+ " pe_bzz_tch_course       tc,"
			+ " pr_bzz_tch_opencourse   bo"
			+ " where bs.fk_batch_id = bb.id"
			+ "  and bs.fk_enterprise_id = pe.id"
			+ "  and se.fk_stu_id = bs.id" + "  and tc.name = '"
			+ openCourse.getBzzCourse().getName() + "'"
			+ " and bo.fk_course_id = tc.id"
			+ " and bo.fk_batch_id = bb.id"
			+ "  and se.fk_tch_opencourse_id = bo.id" + sql_name
			+ sql_reg + sql_batch;
			//System.out.println(sql);
%>
<html>
	<title>学生作业成绩查询</title>
	<link href="../css/css2.css" rel="stylesheet" type="text/css">
	<script language=javascript src="../manager/js/check.js"></script>
	<script>

function pageGuard()
{
			if(isNum(document.score_form.score.value))
			{
			}
			else
			{
				alert("输入不是数字!");
				document.score_form.score.focus();
				document.score_form.score.select();
				return false;
			}
			if(isNull(document.score_form.score.value))
			{
			}
			else
			{
				alert("输入为空!");
				document.score_form.score.focus();
				document.score_form.score.select();
				return false;
			}
	
	return true;
}
function doClean(){
		document.search.batch_id.value = "";
		document.search.name.value = "";
		document.search.stureg_no.value = "";
	}
</script>
	<%!/*   String fixnull(String str){
	 if(str==null || str.equals("null") || str.equals("")){
	 return "";
	 }
	 return str;
	 }
	 */%>

	<%
		try {
			String stuName = fixnull(request.getParameter("name"));
			String stuReg_No = fixnull(request.getParameter("stureg_no"));
			BasicActivityManage basicActivityManage = teacherOperationManage
					.createBasicActivityManage();
			BasicScoreManage basicScoreManage = teacherOperationManage
					.createBasicScoreManage();

			int totalItems = db.countselect(sql);
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
			String link = "&name=" + name + "&stureg_no=" + stureg_no
					+ "&batch_id=" + batch_id;
	%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<title>生殖健康咨询师培训网</title>
		<link href="./css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body leftmargin="0" topmargin="0" background="./images/bg2.gif">
		<br>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<!-- tr>
                <td height="86" valign="top" background="./images/top_01.gif"><img src="./images/tlq.gif" width="217" height="86"></td>
              </tr-->
						<tr>
							<td align="center" valign="top">
								<table width="750" border="1" cellspacing="0" cellpadding="0"
									bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
									<tr>
										<td bgcolor="#FFFFFF">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0"
															cellpadding="0">
															<tr>
																<td width="50" align="center" valign="bottom">
																	<img src="./images/tlq_04.gif" width="40" height="44">
																</td>
																<td width="220" valign="top" class="text3"
																	style="padding-top: 25px">
																	<%=openCourse.getBzzCourse().getName()%>
																	学生列表
																</td>
																<td background="./images/wt_03.gif">
																	&nbsp;
																</td>
															</tr>
														</table>
													</td>
												</tr>
												<form name=search action="student_score.jsp" method=post>
												<tr>
													<td valign="top" class="text3">
														<div style="padding-top: 25px">
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学期&nbsp;
															<select name="batch_id">
																<option value="">
																	所有学期
																</option>
																<%
																	String sql_t = "select id,name from pe_bzz_batch order by id";
																		rs = db.executeQuery(sql_t);
																		while (rs != null && rs.next()) {
																			String id = fixnull(rs.getString("id"));
																			String batch_name = fixnull(rs.getString("name"));
																			String selected = "";
																			if (id.equals(batch_id))
																				selected = "selected";
																%>
																<option value=<%=id%> <%=selected%>><%=batch_name%></option>
																<%
																	}
																		db.close(rs);
																%>
															</select>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;姓名&nbsp;
															<input type=text name=name value="<%=stuName%>" size=10
																maxlength="10">
															&nbsp;&nbsp;学号&nbsp;
															<input type=text name=stureg_no value="<%=stuReg_No%>"
																size="25" maxlength="25">
															&nbsp;
															<span class="link"><img src='./images/search.png'
																	alt='Search' width="20" height="20" title='Search'>&nbsp;
																<a href='#' onclick='javascript:submit();'>查找</a> </span>
															<span class="link">&nbsp; <a href='#'
																onclick='javascript:doClean();'>清除</a> </span>
															<img src='images/excel.png' alt='Print' width="20"
																height="20" title='Print'>
															<a
																href='student_score_excel.jsp?coursename=<%=openCourse.getBzzCourse().getName()%>&name=<%=URLEncoder.encode(stuName, "utf-8")%>&stureg_no=<%=stuReg_No%>&batch_id=<%=batch_id%>'
																target="_blank">导出Excel</a>
													</td>
												</tr>
												<tr>
													<br>
													<br>
													<br>
													</form>
													<form name="score_add" action="student_score_addexe.jsp"
														method="post">
												<tr>
													<td valign="top">
														<table width="95%" border="0" align="center"
															cellpadding="0" cellspacing="0">
															<tr align="center" bgcolor="#4582B1">
																<td height=25px width="20%" align="center" class="title">
																	学号
																</td>
																<td height=25px width="10%" align="center" class="title">
																	姓名
																</td>
																<td height=25px width="10%" align="center" class="title">
																	性别
																</td>
																<td height=25px width="10%" align="center" class="title">
																	民族
																</td>
																<td height=25px width="10%" align="center" class="title">
																	所在学期
																</td>
																<td height=25px width="10%" align="center" class="title">
																	所在企业
																</td>
																<td height=25px width="15%" align="center" class="title">
																	考试成绩
																</td>
															</tr>
															<%
																rs = db.execute_oracle_page(sql, pageInt, pagesize);
																	while (rs != null && rs.next()) {
																		String id = fixnull(rs.getString("id"));
																		//System.out.println("id="+id);
																		String stu_name = fixnull(rs.getString("name"));
																		String reg_no = fixnull(rs.getString("reg_no"));
																		String email = fixnull(rs.getString("email"));
																		String gender = fixnull(rs.getString("gender"));
																		String folk = fixnull(rs.getString("folk"));
																		String position = fixnull(rs.getString("position"));
																		String department = fixnull(rs.getString("department"));
																		String title = fixnull(rs.getString("title"));
																		String enterprise_name = fixnull(rs
																				.getString("enterprise_name"));
																		String batch_name = fixnull(rs.getString("batch_name"));
																		String score = fixnull(rs.getString("score"));
																		if (score.equals("")) {
																			score = "0";
																		}
															%>
															<input type="hidden" name="id" value="<%=id%>">
															<td align="center" class="td2">
																&nbsp;<%=reg_no%></td>
															<td align="center" class="td2">
																&nbsp;<%=stu_name%></td>
															<td align="center" class="td2">
																&nbsp;<%=gender%></td>
															<td align="center" class="td2">
																&nbsp;<%=folk%></td>
															<td align="center" class="td2">
																&nbsp;<%=batch_name%></td>
															<td align="center" class="td2">
																&nbsp;<%=enterprise_name%></td>
															<td align="center" class="td2">
																<input type="text" onblur="bl('<%=id%>')"
																	name="score" id="score_<%=id%>" size="4"
																	value="<%=score%>" maxlength="4">
															</td>
															</tr>
															<%
																}
																	db.close(rs);
															%>



															<tr align="center">
																<td align="center" height=25px colspan="7" class="td">
																	<br>
																	<input type="hidden" name="pageInt"
																		value="<%=pageInt%>">
																	<input type="hidden" name="name" value="<%=name%>">
																	<input type="hidden" name="reg_no" value="<%=name%>">
																	<input type="hidden" name="batch_id"
																		value="<%=batch_id%>">
																	<input type="submit" name="submit" value="提交">
																</td>
															</tr>

															</form>

															<tr>
																<td colspan=11>
																	<%@ include file="./pub/dividepage.jsp"%>
																</td>
															</tr>
														</table>
													</td>

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
function bl(reg_no)
{
	var tr = document.getElementById("tr_"+reg_no);
	var score=document.getElementById("score_"+reg_no);
	
	if(!isScore(score.value))
		{
		alert("成绩必须是数字且为(0-99)的整数！");
		score.value="0";	
		}
	if(!isNull(score.value))
		{
		alert("成绩不能为空！");
		score.value="0";	
		}
	if(score.value.length==2){
		if(score.value.substring(0,1)==0)
			{
			alert("学号为("+reg_no+")的成绩无效！");
			score.value="0";
			}
	}			
}
</script>
