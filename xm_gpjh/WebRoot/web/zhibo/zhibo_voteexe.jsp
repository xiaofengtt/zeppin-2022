<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

String client_ip=request.getRemoteAddr();

dbpool db = new dbpool();
MyResultSet rs = null;
String result=fixnull(request.getParameter("item"));
String sql="insert into zhibo_vote(id,ip,result,data_date) values(to_char(s_zhibo_vote_id.nextval),'"+client_ip+"','"+result+"',sysdate)";
int count=db.executeUpdate(sql);
if(count>0)
{
%>
<script>
alert("投票成功");
window.close();
</script>
<%
}
else{
%>
<script>
alert("投票失败");
window.close();
</script>
<%
}
%>
