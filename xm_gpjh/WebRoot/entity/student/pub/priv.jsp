<%@ page import="com.whaty.platform.interaction.InteractionUserPriv" %>
<%@ page import = "java.util.*,com.whaty.platform.interaction.*"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "java.util.*,com.whaty.platform.sso.*"%>
<%
Object o1 = session.getAttribute("student_eduplatform_user");
Object o2 = session.getAttribute("student_eduplatform_priv");
Student session_student;
if(o1 != null)
	session_student = (Student)o1;
else
	session_student = (Student)(session.getAttribute("eduplatform_user"));
	
StudentPriv studentPriv;
if(o2 != null)
	studentPriv = (StudentPriv)o2;
else
	studentPriv = (StudentPriv)(session.getAttribute("eduplatform_priv"));

//Student session_student = (Student)(session.getAttribute("eduplatform_user"));
//StudentPriv studentPriv = (StudentPriv)(session.getAttribute("eduplatform_priv"));

/*
String student_id = session_student.getId();
String student_reg_no = session_student.getStudentInfo().getReg_no();

String student_name = session_student.getName();
String student_login_id = session_student.getLoginId();
String student_siteid = session_student.getStudentInfo().getSite_id();
String student_sitename = session_student.getStudentInfo().getSite_name(); */

PlatformFactory platformFactory = PlatformFactory.getInstance();
StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);
Course course = (Course)(session.getAttribute("course"));

ManagerPriv includePriv = studentOperationManage.getNormalEntityManagePriv();

/**/
// 瀛︾敓涓汉淇℃伅
//SsoUserPriv ssoUserPriv = (SsoUserPriv)session.getAttribute("ssoUserPriv");
//String sso_id = ssoUserPriv.getId();
//String sso_name = ssoUserPriv.getName();
//String sso_login_id = ssoUserPriv.getLoginId();

// 鏉冮檺
//PlatformFactory platformFactory = PlatformFactory.getInstance();
//if(sso_id == null || sso_id.equals(""))
//	sso_id = "66";//姝や俊鎭敤浜庢病鏈夌櫥闄嗘椂鐨勬祴璇曠敤
//StudentPriv studentPriv = platformFactory.getStudentPriv(sso_id);
//StudentOperationManage studentOperationManage = platformFactory.creatStudentOperationManage(studentPriv);
//Course course = (Course)session.getAttribute("course");
/**/
%>