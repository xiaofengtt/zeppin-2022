<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.whaty.platform.interaction.InteractionUserPriv" %>
<%@ page import = "java.util.*,com.whaty.platform.interaction.*"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@page import = "java.util.*,com.whaty.platform.sso.*"%>
<%@page import = "com.whaty.platform.database.oracle.standard.entity.user.OracleManager"%>
<%
Object o1 = session.getAttribute("teacher_eduplatform_user");
Object o2 = session.getAttribute("teacher_eduplatform_priv");
Teacher session_teacher;

if(o1 != null)
	session_teacher = (Teacher)o1;
else
	session_teacher = (Teacher)(session.getAttribute("eduplatform_user"));
	
TeacherPriv session_teacherPriv;
if(o2 != null)
	session_teacherPriv = (TeacherPriv)o2;
else
	session_teacherPriv = (TeacherPriv)(session.getAttribute("eduplatform_priv"));
	

String teacher_id = session_teacher.getId();

String teacher_name = session_teacher.getName();

String teacher_login_id = session_teacher.getLoginId();

PlatformFactory platformFactory = PlatformFactory.getInstance();
TeacherOperationManage teacherOperationManage = platformFactory.creatTeacherOperationManage(session_teacherPriv);
/**/
//教师个人信息
//SsoUserPriv ssoUserPriv = (SsoUserPriv)session.getAttribute("ssoUserPriv");
//String sso_id = ssoUserPriv.getId();
//String sso_name = ssoUserPriv.getName();
//String sso_login_id = ssoUserPriv.getLoginId();

//权限
//PlatformFactory platformFactory = PlatformFactory.getInstance();
//if(sso_id == null || sso_id.equals(""))
//	sso_id = "65";//此信息用于没有登陆时的测试用
//TeacherPriv teacherPriv = platformFactory.getTeacherPriv(sso_id);
/***/
%>
