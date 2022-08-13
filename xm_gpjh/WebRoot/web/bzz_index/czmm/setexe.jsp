<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String reg_no = request.getParameter("reg_no");
String name = request.getParameter("name");
String mobilephone = request.getParameter("mobilephone");
String phone = request.getParameter("phone");
String enterprise_id = request.getParameter("enterprise_id");
String password = request.getParameter("password");
String confirmpassword = request.getParameter("confirmpassword");
dbpool db = new dbpool();
String sql = "";
String sql1 = "select id from sso_user where login_id='"+reg_no+"' and fk_role_id='0'";
String sql2 = "select id from sso_user where login_id='"+reg_no+"' and fk_role_id not in('0','3')";
if(db.countselect(sql1)>0)
{
	sql = "select id from pe_bzz_student where reg_no='"+reg_no+"' and true_name='"+name+"' and (mobile_phone='"+mobilephone+"' or phone='"+phone+"') and (fk_enterprise_id ='"+enterprise_id+"' or fk_enterprise_id in(select id from pe_enterprise where fk_parent_id='"+enterprise_id+"'))";
}
if(db.countselect(sql2)>0)
{
	sql = "select id from pe_enterprise_manager where login_id='"+reg_no+"' and name='"+name+"' and (mobile_phone='"+mobilephone+"' or phone='"+phone+"') and (fk_enterprise_id ='"+enterprise_id+"' or fk_enterprise_id in(select id from pe_enterprise where fk_parent_id='"+enterprise_id+"'))";
}
if(db.countselect(sql)<1)
{
%>
<script type="text/javascript">
	alert("您输入的信息不存在！");
	window.navigate('/web/findpwd/find.jsp');
</script>
<%
	return;
}
if(password!=null&&password.equals(confirmpassword))
{
	sql = "update sso_user set password = '"+password+"' where login_id='"+reg_no+"'";
	if(db.executeUpdate(sql)>0)
	{
%>
<script type="text/javascript">
	alert("修改成功！");
	window.navigate('<%=basePath%>');
</script>
<%
	}
	else
	{
%>
<script type="text/javascript">
	alert("修改失败！");
	window.history.back();
</script>
<%
	}
}
else{
%>
<script type="text/javascript">
	alert("两次输入密码不一致！");
	window.history.back();
</script>
<%
}

%>