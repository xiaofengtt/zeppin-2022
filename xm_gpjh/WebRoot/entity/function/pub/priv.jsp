<%@ page import = "java.util.*,com.whaty.platform.interaction.*,com.whaty.platform.test.*"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>

<%
	
	String contextPath = request.getContextPath();
	
	InteractionUserPriv interactionUserPriv = (InteractionUserPriv)session.getAttribute("interactionUserPriv");
	OpenCourse openCourse = (OpenCourse)session.getAttribute("openCourse");
	
	String teachclass_id = "";
	String courseId = (String)session.getAttribute("courseId");
	String userType = null;
	User user = null;
	User teacher = null;
	User student = null;
	
	UserSession us = (UserSession)session.getAttribute("user_session");
	
	if(interactionUserPriv != null ) {
		openCourse = (OpenCourse)session.getAttribute("openCourse");
//System.out.println(openCourse.getId() + "+++++++++++");
		teachclass_id = interactionUserPriv.getTeachclassId();
		//teachclass_id=(String)session.getAttribute("openId");
		userType = (String)session.getAttribute("userType");
		 
		teacher = null;
		student = null;
		if("teacher".equalsIgnoreCase(userType)){
		   
		   	user = (User)session.getAttribute("teacher_eduplatform_user");
		   	if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
		   		
			teacher = user;
		}else {
		  	user = (User)session.getAttribute("student_eduplatform_user");
		  		if(user!=null){
		   	}else{
		   	  user = (User)session.getAttribute("eduplatform_user");
		   	}
			student = user;
					
		}

	} else {
%>	
	<script language="javascript">
		window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
		window.top.location = "/";
	</script>
<%
	return ;
	}
%>
