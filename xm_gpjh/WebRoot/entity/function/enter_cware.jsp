<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "com.whaty.platform.courseware.config.*,
				  com.whaty.platform.entity.*"%>
<%@ include file="pub/priv.jsp" %>				  
<%
String courseware_id=request.getParameter("courseware_id");
String enter_type=request.getParameter("enter_type");
 
 	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);

 if(enter_type!=null && enter_type.equals("view"))
 {
	session.setAttribute("courseware_priv",interactionManage.getCoursewareManagerPriv());
	response.sendRedirect("../../courseware/courseware_view.jsp?courseware_id="+courseware_id);
 }
 else if(enter_type!=null && enter_type.equals("teacher"))
 {
	session.setAttribute("courseware_priv",interactionManage.getCoursewareManagerPriv());
	response.sendRedirect("../../courseware/coursewaretype_select.jsp?courseware_id="+courseware_id);
 }

%>
