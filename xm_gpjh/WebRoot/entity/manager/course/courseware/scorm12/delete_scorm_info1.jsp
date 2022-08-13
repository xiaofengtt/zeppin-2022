
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant"%><%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./readerror.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训平台－后台管理</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<script>
function checkValues()
   {
      if ( courseInfo.title.value == "" || 
           courseInfo.coursezipfile.value == "" ) 
      {
         alert( "You must enter a value for all items" );
         return false;
      }
      
      courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
      return true;
   }
</script>
</head>
<%
	String courseware_id=fixnull(request.getParameter("courseware_id"));
	String courseware_name=fixnull(request.getParameter("courseware_name"));
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql="";
	
	/*sql="select e.percent\n" + 
		"  from pe_bzz_tch_courseware   a,\n" + 
		"       pe_bzz_tch_course       b,\n" + 
		"       pr_bzz_tch_opencourse   d,\n" + 
		"       training_course_student e\n" + 
		" where a.code = '"+courseware_id+"'\n" + 
		"   and a.fk_course_id = b.id\n" + 
		"   and d.fk_course_id = b.id\n" + 
		"   and e.course_id = d.id and to_number(e.percent)>0";
	if(db.countselect(sql)>0)
	{
	%>
	<script>
	//alert("目前已经有学生开始学习，不能删除");
//	window.history.back();
	</script>
	<%
	return;
	} */
	
	Hashtable sql_hash=new Hashtable();
	sql="delete from scorm_stu_sco where course_id='"+courseware_id+"'";
	sql_hash.put(1,sql);
	sql="delete from scorm_stu_course where course_id='"+courseware_id+"'";
	sql_hash.put(2,sql);
	sql="delete from scorm_course_item where course_id='"+courseware_id+"'";
	sql_hash.put(3,sql);
	sql="delete from scorm_course_info where id='"+courseware_id+"'";
	sql_hash.put(4,sql);
	sql="update training_course_student  set percent='0',learn_status='INCOMPLETE' "
		+"where course_id in (select d.id from pe_bzz_tch_courseware a,pe_bzz_tch_course b,pr_bzz_tch_opencourse d where a.code = '"+courseware_id+"'\n" 
		+ "   and a.fk_course_id = b.id\n" 
		+ "   and d.fk_course_id = b.id\n )";
	sql_hash.put(5,sql);
	int count = db.executeUpdateBatch(sql_hash);
	
	if(count>0)
	{
	%>
	<script>
	alert("删除成功");
	window.close();
	</script>
	<%
	}
	else
	{
	%>
	<script>
	alert("删除失败");
	window.close();
	</script>
	<%
	}
 %>

</html>

