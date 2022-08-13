<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.courseware.basic.*"%>

<%@ include file="pub/priv.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"华南师范大学tle>欢迎光临中国石油大学（华东）现代远程教育平台</title></title></title>
<link href="css/css.css" rel="stylesheet" type="text/css">
</head> 
<%
	try{

%>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		              <td><img src="images/middle_12.gif" width="573" height="26"></td>
		  </tr>
              <tr>
                      <td valign="top"><table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="25" style="padding-left:35px" class="14title2"></td>
                          </tr>
                          <tr>
                            <td height="26" valign="bottom" background="images/middle_02.gif">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr align="center"> 
                                 
                                  <td width="40%" class="14title2">课程名称</td>
                                   <td width="1%"><img src="images/jx.gif" width="2" height="24"></td>
                                  <td width="25%" class="14title2">开始授课</td>
                                   <td width="1%"><img src="images/jx.gif" width="2" height="24"></td>
                                  <td width="15%" class="14title2">所属学期</td>
                                  <td width="1%"><img src="images/jx.gif" width="2" height="24"></td>
                                   <td width="17%" class="14title2">课程栏目123</td>
                                </tr>                                
                              </table></td>
                          </tr>
                          <tr>
                            <td background="images/middle_03.gif">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
<% 
	int ii = 0;
	List teachClasses = teacherOperationManage.getTeachClasses();
	if(teachClasses.size()<1){
%>	  
      <tr colspan="7">
                  <td align="center" style="padding-left:28px"> 暂无课程！</td>
      </tr>
<%
      }else{
	for( ii = 0; ii<teachClasses.size(); ii++){
	   TeachClass teachClass = (TeachClass)teachClasses.get(ii);
	   OpenCourse openCourse = teachClass.getOpenCourse();
	   Semester semester = openCourse.getSemester();
         
%>
								<tr> 
                                  
                                  <td width="40%" align="center" class="time"><a href="javascript:void window.open('enter_class.jsp?teachclass_id=<%=teachClass.getId()%>&open_course_id=<%=openCourse.getId()%>','jlyd','resizable,width=800,height=600')" class="lb"><%= openCourse.getCourse().getName() %></a>
                                  </td>
                                  <td width="1%"> </td>
                                  <td width="25%" align="center" class="time"><a href="javascript:void window.open('enter_class.jsp?teachclass_id=<%=teachClass.getId()%>&open_course_id=<%=openCourse.getId()%>','jlyd','resizable,width=800,height=600')" class="lb">开始授课 </a></td>
                                  <td width="1%"> </td>
                                  <td width="15%" align="center" class="time"><%= semester.getName() %></td>
                                  <td width="1%"> </td>
                                  <td width="17%" align="center" class="time"><a href="teacher_courseItem.jsp?teachclass_id=<%=teachClass.getId()%>&teacher_name=<%= teacher_name %>" target=_blank>栏目设置</a></td>
                                  
                                </tr>
<%
		}
	}	
%>							                              
                              </table></td>
                          </tr>
                            <tr> 
    <td height="7" > <img src="images/middle_09.gif" width=100%></td>
  </tr>
                          
                        </table>
                        <br></td>
              </tr>
            </table>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>
</body>
</html>