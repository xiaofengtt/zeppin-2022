<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>

<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./pub/commonfuction.jsp"%>



<%
	OpenCourse openCourse = (OpenCourse)session.getAttribute("openCourse");
	String courseName = openCourse.getBzzCourse().getName();
	String courseId = openCourse.getBzzCourse().getId();
	//String type = request.getParameter("type");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String id = request.getParameter("id");
%>

<%!
   String fixnull1(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "不详";
   			return str;
   		}
   		return str;
   }
%>


<%
String sql = "";
dbpool db = new dbpool();
MyResultSet rs =null;

String  type = "JSJJ";


%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>中央企业班组长-网络课程</title>
<link rel="stylesheet" type="text/css" href="/entity/function/student/css/style.css">
<link href="/entity/function/student/css/admincss.css" rel="stylesheet" type="text/css">
<link href="/entity/function/student/css/admincss_new.css" rel="stylesheet" type="text/css">
</head>

<body style="padding:10px 0px;">
<table width="95%" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td height="63" valign="top" ><table width="100%" height="63" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="117" align="left" valign="top" background="/entity/function/student/images/righttopbg1.jpg"><span class="hei" style="background-repeat:no-repeat"><img src="/entity/function/student/images/tubiao1.jpg" width="17" height="15"> 教师简介</span></td>
        <td background="/entity/function/student/images/righttopbg2.jpg">&nbsp;</td>
        <td width="296" background="/entity/function/student/images/righttopbg3.jpg">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
  <%
                            		sql = "select t.content  as content,t.title as title from interaction_teachclass_info t where t.teachclass_id = '"+courseId+"' and t.type = '"+type+"'" ;
                            		//out.println(sql);
                            		rs = db.executeQuery(sql);
	                                while(rs!=null && rs.next())
	                                {
		                                content = fixnull(rs.getString("content"));

		                                title = fixnull(rs.getString("title"));
		                             }
		                             db.close(rs);
                            	 %>
                            	
    <td valign="top" class="biankuang" style="padding:10px 30px 10px 30px;"><p class="titlezi"><%=title%></p>
      <p>
    　　<%=content%></p></td>
  </tr> 

</table>
</body>
</html>