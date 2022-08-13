<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="../pub/commonfuction.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>


<%
	OpenCourse openCourse = (OpenCourse)session.getAttribute("openCourse");
	
	if(openCourse==null)
	{
	%>
	<script>
		window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
		window.top.location = "/";
	</script>
	<%
	return;
	}
	
	String courseName = openCourse.getBzzCourse().getName();
	String courseId = openCourse.getBzzCourse().getId();
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	if(us==null)
	{
	%>
	<script>
		window.top.alert("登录超时");
		window.top.location = "/";
	</script>
	<%
	return;
	}
	
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>教师简介</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=77%>
                            	<%
                            		sql = "select t.id as id ,t.content  as content,t.title as title from interaction_teachclass_info t where t.teachclass_id = '"+courseId+"' and t.type = '"+type+"'" ;
                            		//out.println(sql);
                            		int count = 0;
                            		rs = db.executeQuery(sql);
                            		count = db.countselect(sql);
	                                while(rs!=null && rs.next())
	                                {
		                                content = fixnull(rs.getString("content"));
		                                id = fixnull(rs.getString("id"));
		                                title = fixnull(rs.getString("title"));
		                             
                            	 %>
                            	  <tr>
                                     
							          <td >
							      <p align="center"><font size=5><strong><%=title%></strong></font></p>
							        </td>
						            </tr>
							<td >
							<br />
							 <%=content%></p>
							</td>
						  </tr>
                            	
                            	 <%
                            	 }
                            	 db.close(rs);
                            	 %>
                            	</table>
							</td>
							</tr>
				     </table>
				     </td>
				    </tr>
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
                    <input type=hidden name=teachclass_id value=<%=courseId %>>
					<input type=hidden name=type value=<%=type %>>
					<input type=hidden name=title value=<%=title %>>
					<input type=hidden name=id value=<%=id %>>
                  <tr>

			<td align=center>
				<%  if(us.getRoleId().equals("3")){
					if(count == 0){
					
				%>
				    <a href="teacher_note_add.jsp?type=JSJJ" class="tj">[添加教师简介]</a>&nbsp;</td>
				    <%
				    	}
				    	else{
				    %>
				     <a href="teacher_note_edit.jsp?type=JSJJ&title=<%=java.net.URLEncoder.encode(title,"UTF-8")%>&id=<%=id%>" class="tj">[编辑教师简介]</a>&nbsp;
				     <a href="teacher_note_delete.jsp?type=JSJJ&title=<%=java.net.URLEncoder.encode(title,"UTF-8")%>&id=<%=id%>" class="tj">[删除教师简介]</a>&nbsp;</td>
			<!-- <input type=button value="编辑" onclick="window.location='course_note_edit.jsp?&type=KCJJ&title=<%=title%>&id=<%=id%>'"></td> -->
			<%} 
			}%>
		    </tr>
                  </table> </td>
              </tr>
              	
            </table></td>
        </tr>
      </table>
</body>
</html>