<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import = "java.util.*,com.whaty.platform.database.oracle.dbpool,java.text.*,"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if(session!=null)
{

Date startdate = (Date)session.getAttribute("now");

if(startdate !=null )
{
String userID="";
String courseID="";
String openCourseId ="";
	courseID = (String) session.getAttribute("COURSEID");
	userID = (String) session.getAttribute("USERID");
	openCourseId = (String)session.getAttribute("openId");	

	
dbpool db = new dbpool();
Date now = new Date();
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String start = dateFormat.format(startdate);
String end = dateFormat.format(now);
System.out.println(end);
if(userID!=null && !userID.equals(""))
{
	String sql ="insert into STUTTIME (id,START_DATE,FK_SSOUSER_ID,FK_OPEN_COURSE_ID,END_DATE) values(to_char(Seq_stutime_id.Nextval),to_date('"+start+"','yyyy-mm-dd hh24:mi:ss'),'"+userID+"','"+openCourseId+"',to_date('"+end+"','yyyy-mm-dd hh24:mi:ss'))";
//	int k =db.executeUpdate(sql);
}
}
}
%>
<script>
window.close();
</script> 
