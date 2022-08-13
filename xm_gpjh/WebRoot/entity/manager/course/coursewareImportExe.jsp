<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@page import = "com.whaty.platform.training.*,java.util.*"%>
<%@page import = "com.whaty.platform.training.*,com.whaty.platform.database.oracle.standard.training.user.OracleTrainingManagerPriv"%>
<%@page import = "com.whaty.platform.training.basic.*,com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>
<%
try{
	String coursewareId=request.getParameter("course_id");
	String type=request.getParameter("type");
	
	UserSession usersession=(UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	TrainingManagerPriv includePriv=new OracleTrainingManagerPriv(usersession.getId());
	//includePriv.setManagerId(usersession.getId());
	
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingManage trainingManage=factory.creatTrainingManage(includePriv);
	trainingManage.addTrainingCourseware("402880f32201c2df012201df09600003",coursewareId,type);
	
	%>
		<script language="javascript" >
			alert("建立课件成功！");
			window.close();
	</script>
	<%
}catch(Exception e){
	%>
	<script language="javascript" >
	</script>
	<%
}
%>
