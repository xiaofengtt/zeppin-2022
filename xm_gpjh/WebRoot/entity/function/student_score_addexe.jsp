<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="java.net.URLEncoder" %>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./pub/priv.jsp"%>
<%@ include file="../teacher/pub/priv.jsp"%>
<html>
<head>
<title>执行录入学生成绩</title>
<head>
<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<%
	dbpool db = new dbpool();
	MyResultSet rs =null;
	int count=0;
	String sql = "";
	String stuName = fixnull(request.getParameter("name"));
	String stuReg_No = fixnull(request.getParameter("reg_no"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String[] scores = request.getParameterValues("score");
	
	String[] ids = request.getParameterValues("id");
	String pageInt = request.getParameter("pageInt");
	//String context = request.getContextPath();
try
{ 	
	for(int i=0;i<ids.length;i++)
	{
		String id=ids[i];
		sql="update pr_bzz_tch_stu_elective set score_exam = '"+scores[i]+"'  where id='"+id+"'";
		if(db.executeUpdate(sql)>0)
		{
			count++;
		}
	}
		if (count < 1)
		{
	%>
	<p align="center">
	  更新学生成绩失败！<br>
	  <a href="/entity/function/student_score.jsp?reg_no=<%=stuReg_No%>&pageInt=<%=pageInt%>&name=<%=stuName%>&batch_id=<%=batch_id%>">返回</a>
	  </p>
	<%
		}
	else
		{
	%>
	<p align="center">
	  成功更新学生成绩！<br>
	   <%
	//   System.out.print("reg_no="+stuReg_No+"pageInt="+pageInt+"name="+stuName);
	   %>
	  <a href="/entity/function/student_score.jsp?reg_no=<%=stuReg_No%>&name=<%=URLEncoder.encode(stuName,"utf-8")%>&pageInt=<%=pageInt%>&batch_id=<%=batch_id%>">返回</a>
	  
	</p>
	<%
		}
	%>

<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>